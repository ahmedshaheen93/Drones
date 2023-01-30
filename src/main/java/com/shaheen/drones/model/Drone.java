package com.shaheen.drones.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "DRONE")
@Getter
@Setter
@Audited
public class Drone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Size(max = 100)
  @Column(unique = true)
  private String serialNumber;
  @Enumerated(EnumType.STRING)
  private DroneModel model;
  @Max(500)
  private Float weightLimit;
  private Integer batteryCapacity;
  @Enumerated(EnumType.STRING)
  private DroneState state = DroneState.IDLE;
  @OneToMany(fetch = FetchType.LAZY,mappedBy = "drone",cascade = CascadeType.ALL)
  private List<Medication> medications = new ArrayList<>();
}

