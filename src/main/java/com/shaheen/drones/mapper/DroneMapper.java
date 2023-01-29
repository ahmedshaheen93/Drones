package com.shaheen.drones.mapper;

import com.shaheen.drones.api.model.DroneAddRequest;
import com.shaheen.drones.api.model.DroneResponse;
import com.shaheen.drones.model.Drone;
import com.shaheen.drones.model.DroneModel;
import com.shaheen.drones.model.DroneState;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

@Component
public class DroneMapper {
  public Drone mapFromDroneAddRequest(DroneAddRequest droneAddRequest) {
    Drone drone = new Drone();
    drone.setSerialNumber(droneAddRequest.getSerialNumber());
    drone.setModel(DroneModel.valueOf(droneAddRequest.getModel().name()));
    drone.setWeightLimit(droneAddRequest.getWeightLimit().floatValue());
    drone.setBatteryCapacity(droneAddRequest.getBatteryCapacity());
    drone.setState(ObjectUtils.isEmpty(droneAddRequest.getState()) ? DroneState.IDLE : DroneState.valueOf(droneAddRequest.getState().name()));
    return drone;
  }

  public DroneResponse mapDroneToDroneResponse(Drone drone) {
    DroneResponse droneResponse = new DroneResponse();
    droneResponse.setId(drone.getId());
    droneResponse.setSerialNumber(drone.getSerialNumber());
    droneResponse.setModel(DroneResponse.ModelEnum.valueOf(drone.getModel().name()));
    droneResponse.setState(com.shaheen.drones.api.model.DroneState.valueOf(drone.getState().name()));
    droneResponse.setWeightLimit(BigDecimal.valueOf(drone.getWeightLimit()));
    droneResponse.setBatteryCapacity(drone.getBatteryCapacity());
    return droneResponse;
  }
}
