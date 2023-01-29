package com.shaheen.drones.config;

import com.shaheen.drones.model.Drone;
import com.shaheen.drones.model.DroneState;
import com.shaheen.drones.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {
  private final DroneRepository droneRepository;

  @Scheduled(cron = "${com.shaheen.scheduler.battery}")
  public void droneBatteryDecreasingScheduler() {
    List<Drone> drones = droneRepository.findAllByBatteryCapacityGreaterThan(0);
    if (!CollectionUtils.isEmpty(drones)) {
      for (Drone drone : drones) {
        drone.setBatteryCapacity(drone.getBatteryCapacity() - 1);
      }
      droneRepository.saveAll(drones);
    }
  }

  @Scheduled(cron = "${com.shaheen.scheduler.status}")
  public void loadingStatusScheduler() {
    List<Drone> drones = droneRepository.findAllByStateIs(DroneState.LOADING);
    if (!CollectionUtils.isEmpty(drones)) {
      for (Drone drone : drones) {
        drone.setState(DroneState.LOADED);
      }
      droneRepository.saveAll(drones);
    }
  }
}
