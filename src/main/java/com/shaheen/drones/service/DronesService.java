package com.shaheen.drones.service;

import com.shaheen.drones.api.model.DroneAddRequest;
import com.shaheen.drones.api.model.DroneResponse;
import com.shaheen.drones.api.model.MedicationRequest;
import com.shaheen.drones.api.model.MedicationResponse;

import java.util.List;

public interface DronesService {

  DroneResponse registerADrone(DroneAddRequest droneAddRequest);

  List<MedicationResponse> loadDroneWithMedication(Integer id, List<MedicationRequest> medicationRequest);

  List<MedicationResponse> loadMedicationInfo(Integer id);

  DroneResponse loadDroneInfo(Integer id);
}
