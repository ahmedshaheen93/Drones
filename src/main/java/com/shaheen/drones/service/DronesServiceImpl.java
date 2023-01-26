package com.shaheen.drones.service;

import com.shaheen.drones.api.model.DroneAddRequest;
import com.shaheen.drones.api.model.DroneResponse;
import com.shaheen.drones.mapper.DroneMapper;
import com.shaheen.drones.model.Drone;
import com.shaheen.drones.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DronesServiceImpl implements DronesService {
  private final DroneRepository repository;
  private final DroneMapper droneMapper;
  @Override
  public DroneResponse save(DroneAddRequest droneAddRequest) {
    // todo validate the request
    //  - duplication of serial number
    //  - positive battery capacity
    //  - positive max wight
    Drone drone = droneMapper.mapFromDroneAddRequest(droneAddRequest);
    drone = repository.save(drone);
    return droneMapper.mapDroneToDroneResponse(drone);
  }
}
