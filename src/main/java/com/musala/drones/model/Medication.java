package com.musala.drones.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
public class Medication {
  @Id @GeneratedValue private Long id;
  @Pattern(regexp = "[\\w\\-]+",message = "name must match {regexp}")
  private String  name;
  private Float weight;
  @Pattern(regexp = "[A-Z\\d_]+",message = "code must match {regexp}")
  private String code;

}
