package com.shaheen.drones.mapper;

import com.shaheen.drones.api.model.MedicationRequest;
import com.shaheen.drones.api.model.MedicationResponse;
import com.shaheen.drones.model.Drone;
import com.shaheen.drones.model.Medication;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MedicationMapper {
  public Medication mapMedicationFormMedicationLoadRequest(MedicationRequest medicationItem, Drone drone) {
    Medication medication = new Medication();
    medication.setCode(medicationItem.getCode());
    medication.setName(medicationItem.getName());
    medication.setWeight(medicationItem.getWeight().floatValue());
    medication.setImage(medicationItem.getImage());
    medication.setDrone(drone);
    return medication;
  }

  public MedicationResponse mapMedicationResponseFromMedication(Medication medication) {
    return new MedicationResponse()
        .id(medication.getId())
        .code(medication.getCode())
        .name(medication.getName())
        .image(medication.getImage())
        .weight(BigDecimal.valueOf(medication.getWeight()));
  }
}
