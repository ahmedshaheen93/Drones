package com.shaheen.drones.service;

import com.shaheen.drones.api.model.DroneAddRequest;
import com.shaheen.drones.api.model.DroneResponse;
import com.shaheen.drones.api.model.MedicationRequest;
import com.shaheen.drones.api.model.MedicationResponse;
import com.shaheen.drones.errorhandling.BadRequestException;
import com.shaheen.drones.errorhandling.NotFoundException;
import com.shaheen.drones.model.Drone;
import com.shaheen.drones.model.DroneModel;
import com.shaheen.drones.model.DroneState;
import com.shaheen.drones.repository.DroneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class DronesServiceTest {
  Drone drone = new Drone();
  @Autowired
  private DronesService dronesService;
  @Autowired
  private DroneRepository droneRepository;

  @BeforeEach
  void setup() {
    // clear old data
    droneRepository.deleteAll();
    drone.setSerialNumber("123456");
    drone.setWeightLimit(300f);
    drone.setBatteryCapacity(50);
    drone.setModel(DroneModel.HEAVYWEIGHT);
    drone.setState(DroneState.IDLE);
    drone = droneRepository.save(drone);
  }

  @Test
  void registerADrone() {
    DroneResponse droneResponse = dronesService.registerADrone(new DroneAddRequest()
        .serialNumber("2468")
        .weightLimit(BigDecimal.valueOf(500))
        .state(com.shaheen.drones.api.model.DroneState.IDLE)
        .model(DroneAddRequest.ModelEnum.HEAVYWEIGHT)
    );
    assertThat(droneResponse).isNotNull();
    assertThat(droneResponse.getId()).isNotZero().isPositive();
  }

  @Test
  void registerADrone_with_duplicated_serial_number() {
    assertThatThrownBy(() -> dronesService.registerADrone(new DroneAddRequest()
        .serialNumber("123456")
        .weightLimit(BigDecimal.valueOf(500))
        .state(com.shaheen.drones.api.model.DroneState.IDLE)
        .model(DroneAddRequest.ModelEnum.HEAVYWEIGHT)
    )).isInstanceOf(BadRequestException.class)
        .hasMessage("duplication of serial number '123456'");

  }

  @Test
  void loadDroneWithMedication() {
    MedicationRequest medicationItem1 = new MedicationRequest()
        .name("name1")
        .code("C1")
        .weight(new BigDecimal(100))
        .image("dummyBase64Image1");
    MedicationRequest medicationItem2 = new MedicationRequest()
        .name("name2")
        .code("C2")
        .weight(new BigDecimal(150))
        .image("dummyBase64Image2");

    List<MedicationResponse> medicationResponse = dronesService.loadDroneWithMedication(drone.getId(), List.of(medicationItem1, medicationItem2));
    assertThat(medicationResponse).isNotEmpty().hasSize(2);
    assertThat(droneRepository.findById(drone.getId()).get().getState()).isEqualTo(DroneState.LOADING);
  }

  @Test
  void loadDroneWithMedication_notFounded_drone() {
    assertThatThrownBy(() -> dronesService.loadDroneWithMedication(555, new ArrayList<>()))
        .isInstanceOf(NotFoundException.class)
        .hasMessage("Drone with id '555' was not founded");
  }

  @Test
  void loadDroneWithMedication_empty_medication_items() {
    assertThatThrownBy(() -> dronesService.loadDroneWithMedication(drone.getId(), new ArrayList<>()))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Could not load empty medication items");
  }

  @Test
  void loadDroneWithMedication_exceed_wight_limit() {
    MedicationRequest medicationItem1 = new MedicationRequest()
        .name("name1")
        .code("code1")
        .weight(new BigDecimal(250))
        .image("dummyBase64Image1");
    MedicationRequest medicationItem2 = new MedicationRequest()
        .name("name2")
        .code("code3")
        .weight(new BigDecimal(150))
        .image("dummyBase64Image2");

    assertThatThrownBy(() -> dronesService.loadDroneWithMedication(drone.getId(), List.of(medicationItem1, medicationItem2)))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Total Medication Items Weight '400.0' Exceed the current Drone weight limit '300.0' ");
  }

  @Test
  void loadDroneWithMedication_append_then_exceed_wight_limit() {
    MedicationRequest medicationItem1 = new MedicationRequest()
        .name("name1")
        .code("C1")
        .weight(new BigDecimal(250))
        .image("dummyBase64Image1");
    MedicationRequest medicationItem2 = new MedicationRequest()
        .name("name2")
        .code("C2")
        .weight(new BigDecimal(150))
        .image("dummyBase64Image2");

    // success load first item that is not exceed the weight limit
    assertThat(dronesService.loadDroneWithMedication(drone.getId(), List.of(medicationItem1))).isNotEmpty().hasSize(1);
    // weight limit = 300
    // already loaded items total weight = 250
    // then the drone can carry only 50
    // fail to append the exceeded weight
    assertThatThrownBy(() -> dronesService.loadDroneWithMedication(drone.getId(), List.of(medicationItem2)))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Total Medication Items Weight '150.0' Exceed the current Drone weight limit '50.0' ");
  }

  @Test
  void loadDroneWithMedication_low_battery_capacity() {
    //update battery capacity to less than 25
    drone.setBatteryCapacity(20);
    droneRepository.save(drone);
    MedicationRequest medicationItem1 = new MedicationRequest()
        .name("name1")
        .code("C1")
        .weight(new BigDecimal(100))
        .image("dummyBase64Image1");
    MedicationRequest medicationItem2 = new MedicationRequest()
        .name("name2")
        .code("C2")
        .weight(new BigDecimal(150))
        .image("dummyBase64Image2");

    assertThatThrownBy(() -> dronesService.loadDroneWithMedication(drone.getId(), List.of(medicationItem1, medicationItem2)))
        .isInstanceOf(BadRequestException.class)
        .hasMessage("Can't load the Drone As Battery Capacity less than 25% ");
    assertThat(drone.getMedications()).isEmpty();
  }

  @Test
  void loadMedicationInfo() {
    MedicationRequest medicationItem1 = new MedicationRequest()
        .name("name1")
        .code("C2")
        .weight(new BigDecimal(100))
        .image("dummyBase64Image1");
    MedicationRequest medicationItem2 = new MedicationRequest()
        .name("name2")
        .code("C1")
        .weight(new BigDecimal(150))
        .image("dummyBase64Image2");
    // given a loaded drone with medication items
    dronesService.loadDroneWithMedication(drone.getId(), List.of(medicationItem1, medicationItem2));
    assertThat(dronesService.loadMedicationInfo(drone.getId())).hasSize(2);
  }

  @Test
  void loadMedicationInfo_for_newly_add_drone() {
    assertThat(dronesService.loadMedicationInfo(drone.getId())).isEmpty();
  }

}
