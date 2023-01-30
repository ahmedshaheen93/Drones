package com.shaheen.drones.controller;

import com.shaheen.drones.api.DronesApi;
import com.shaheen.drones.api.model.DroneAddRequest;
import com.shaheen.drones.api.model.DroneResponse;
import com.shaheen.drones.api.model.MedicationRequest;
import com.shaheen.drones.api.model.MedicationResponse;
import com.shaheen.drones.service.DronesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DronesController implements DronesApi {
  private final DronesService dronesService;
  @Override
  public ResponseEntity<DroneResponse> addADrone(DroneAddRequest droneAddRequest) {
    return new ResponseEntity<>(dronesService.registerADrone(droneAddRequest), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<List<MedicationResponse>> addMedications(Integer id, List<MedicationRequest> medicationRequest) {
    return new ResponseEntity<>(dronesService.loadDroneWithMedication(id,medicationRequest), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<List<MedicationResponse>> loadMedicationInfo(Integer id) {
    return ResponseEntity.ok(dronesService.loadMedicationInfo(id));
  }

  @Override
  public ResponseEntity<DroneResponse> getDroneById(Integer id,String fields) {
    return ResponseEntity.ok(dronesService.loadDroneInfo(id));
  }

  @Override
  public ResponseEntity<List<DroneResponse>> listDrones(Boolean eligibleForLoading) {
    List<DroneResponse> droneResponses ;
    if(eligibleForLoading){
      droneResponses = dronesService.findAllAvailableDrones();
    }else {
      droneResponses = dronesService.findAllDrones();
    }
    return ResponseEntity.ok(droneResponses);
  }
}
