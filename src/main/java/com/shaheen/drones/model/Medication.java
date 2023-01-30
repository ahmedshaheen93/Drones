package com.shaheen.drones.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity(name = "MEDICATION")
@Getter
@Setter
@Audited
public class Medication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Pattern(regexp = "^[A-Za-z0-9_\\-]+")
  private String name;
  private Float weight = 0f;
  @Pattern(regexp = "^[A-Z0-9_]+")
  private String code;

  private String image;
  @ManyToOne
  private Drone drone;
}
