package com.shaheen.drones.service;

import com.shaheen.drones.api.model.DroneAddRequest;
import com.shaheen.drones.api.model.DroneResponse;
import com.shaheen.drones.errorhandling.BadRequestException;
import com.shaheen.drones.mapper.DroneMapper;
import com.shaheen.drones.model.Drone;
import com.shaheen.drones.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DronesServiceImpl implements DronesService {
  private final DroneRepository repository;
  private final DroneMapper droneMapper;
  @Override
  public DroneResponse save(DroneAddRequest droneAddRequest) {
    String serialNumber = droneAddRequest.getSerialNumber();
    Optional<Drone> optionalDrone = repository.findBySerialNumber(serialNumber);
    if(optionalDrone.isPresent()){
      throw new BadRequestException(String.format("duplication of serial number '%s'",serialNumber));
    }
    Drone drone = droneMapper.mapFromDroneAddRequest(droneAddRequest);
    drone = repository.save(drone);
    return droneMapper.mapDroneToDroneResponse(drone);
  }
}
