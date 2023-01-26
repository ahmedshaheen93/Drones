package com.shaheen.drones.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name ="MEDICATION")
@Getter
@Setter
public class Medication {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
//   (allowed only letters, numbers, ‘-‘, ‘_’)
  private String name;
  private Float weight = 0f;
  //  (allowed only upper case letters, underscore and numbers)
  private String code ;

  //(picture of the medication case)\
  private String image ;

}
