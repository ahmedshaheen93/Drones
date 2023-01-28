package com.shaheen.drones.service;

import com.shaheen.drones.api.model.DroneAddRequest;
import com.shaheen.drones.api.model.DroneResponse;
import com.shaheen.drones.api.model.MedicationRequest;
import com.shaheen.drones.api.model.MedicationResponse;
import com.shaheen.drones.errorhandling.BadRequestException;
import com.shaheen.drones.errorhandling.NotFoundException;
import com.shaheen.drones.mapper.DroneMapper;
import com.shaheen.drones.mapper.MedicationMapper;
import com.shaheen.drones.model.Drone;
import com.shaheen.drones.model.DroneState;
import com.shaheen.drones.model.Medication;
import com.shaheen.drones.repository.DroneRepository;
import com.shaheen.drones.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DronesServiceImpl implements DronesService {
  private final DroneRepository droneRepository;
  private final MedicationRepository medicationRepository;
  private final DroneMapper droneMapper;
  private final MedicationMapper medicationMapper;
  @Override
  public DroneResponse registerADrone(DroneAddRequest droneAddRequest) {
    String serialNumber = droneAddRequest.getSerialNumber();
    Optional<Drone> optionalDrone = droneRepository.findBySerialNumber(serialNumber);
    if(optionalDrone.isPresent()){
      throw new BadRequestException(String.format("duplication of serial number '%s'",serialNumber));
    }
    Drone drone = droneMapper.mapFromDroneAddRequest(droneAddRequest);
    drone = droneRepository.save(drone);
    return droneMapper.mapDroneToDroneResponse(drone);
  }

  @Override
  public List<MedicationResponse> loadDroneWithMedication(Integer id, List<MedicationRequest> medicationItems) {
    Drone drone = getDrone(id);
    if(!CollectionUtils.isEmpty(medicationItems)){
      Float weightLimit = drone.getWeightLimit() - calcTheCurrentLoadedMedicationWeight(drone.getMedications());

      Float totalWeight = calcMedicationItemsTotalWeight(medicationItems);
      if (totalWeight> weightLimit){
        throw new BadRequestException(String.format("Total Medication Items Weight '%s' Exceed the current Drone weight limit '%s' ",totalWeight,weightLimit));
      }
      if(drone.getBatteryCapacity()>25) {
        List<Medication> medications = medicationRepository.saveAll(mapMedication(medicationItems));
        drone.getMedications().addAll(medications);
        drone.setState(DroneState.LOADED);
        droneRepository.save(drone);
        return medications.stream()
            .filter(Objects::nonNull)
            .map(medicationMapper::mapMedicationResponseFromMedication)
            .collect(Collectors.toList());
      } else {
        throw new BadRequestException("Can't load the Drone As Battery Capacity less than 25% ");
      }
    }else {
      throw new BadRequestException("Could not load empty medication items");
    }
  }


  @Override
  public List<MedicationResponse> loadMedicationInfo(Integer id) {
    Drone drone = getDrone(id);
    List<Medication> medications = drone.getMedications();
    if(!CollectionUtils.isEmpty(medications)){
      return medications.stream()
                .filter(Objects::nonNull)
                .map(medicationMapper::mapMedicationResponseFromMedication)
                .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  @Override
  public DroneResponse loadDroneInfo(Integer id) {
    return droneMapper.mapDroneToDroneResponse(getDrone(id));
  }
  private Drone getDrone(Integer id) {
    Optional<Drone> optionalDrone = droneRepository.findById(id);
    if(optionalDrone.isEmpty()){
      throw new NotFoundException(String.format("Drone with id '%s' was not founded", id));
    }
    return optionalDrone.get();
  }


  private Float calcTheCurrentLoadedMedicationWeight(List<Medication> medications) {
    float totalWeight = 0f;
    if(!CollectionUtils.isEmpty(medications)){
      for (Medication item : medications){
        totalWeight+= item.getWeight();
      }
    }
    return totalWeight;
  }

  private List<Medication> mapMedication(List<MedicationRequest> medicationItems) {
    return medicationItems.stream()
        .filter(Objects::nonNull)
        .map(medicationMapper::mapMedicationFormMedicationLoadRequest)
        .collect(Collectors.toList());
  }

  private Float calcMedicationItemsTotalWeight(List<MedicationRequest> medicationItems) {
    float totalWeight = 0f ;
    for (MedicationRequest item : medicationItems){
     totalWeight+= item.getWeight().floatValue();
    }
    return totalWeight;
  }
}
