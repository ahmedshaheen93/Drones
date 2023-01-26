package com.shaheen.drones.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name ="DRONE")
@Getter
@Setter
public class Drone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Size(max = 100)
  private String serialNumber;
  private DroneModel model;
  @Max(500)
  private Float weightLimit;
  private Integer batteryCapacity;
  private DroneState state = DroneState.IDLE;
  @OneToMany(fetch = FetchType.LAZY)
  private List<Medication> medications;
}
