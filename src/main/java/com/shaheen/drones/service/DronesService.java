package com.shaheen.drones.service;

import com.shaheen.drones.api.model.DroneAddRequest;
import com.shaheen.drones.api.model.DroneResponse;

public interface DronesService {

  DroneResponse save(DroneAddRequest droneAddRequest);
}
