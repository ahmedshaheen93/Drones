package com.shaheen.drones.mapper;

import com.shaheen.drones.api.model.MedicationRequest;
import com.shaheen.drones.model.Medication;
import org.springframework.stereotype.Component;

@Component
public class MedicationMapper {
  public Medication mapMedicationFormMedicationLoadRequest(MedicationRequest medicationItem){
    Medication medication = new Medication();
    medication.setCode(medicationItem.getCode());
    medication.setName(medicationItem.getName());
    medication.setWeight(medicationItem.getWeight().floatValue());
    medication.setImage(medicationItem.getImage());
    return medication;
  }
}
