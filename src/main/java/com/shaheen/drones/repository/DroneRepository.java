package com.shaheen.drones.repository;

import com.shaheen.drones.model.Drone;
import com.shaheen.drones.model.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Integer> {
  Optional<Drone> findBySerialNumber(String serialNumber);
  List<Drone> findAllByBatteryCapacityGreaterThan(Integer batteryCapacity);
  List<Drone> findAllByStateIs(DroneState droneState);
  List<Drone> findAllByBatteryCapacityGreaterThanAndStateIn(Integer batteryCapacity, List<DroneState> states);
}
