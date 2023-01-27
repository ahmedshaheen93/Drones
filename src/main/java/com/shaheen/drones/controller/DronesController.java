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
    return new ResponseEntity<>(dronesService.save(droneAddRequest), HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<List<MedicationResponse>> addMedications(Integer id, List<MedicationRequest> medicationRequest) {
    return new ResponseEntity<>(dronesService.loadDroneWithMedication(id,medicationRequest), HttpStatus.CREATED);
  }
}
