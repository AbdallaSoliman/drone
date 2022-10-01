package com.musala.drones.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Medication {
  @Id @GeneratedValue private Long id;
  private String name;
  private Float weight;
  private String code;

}
