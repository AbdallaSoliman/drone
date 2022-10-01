package com.musala.drones.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.musala.drones.model.Model;
import com.musala.drones.model.State;
import lombok.Data;

import javax.validation.constraints.Max;

@Data
@JGlobalMap
public class DroneDto {
  @Max(value = 100)
  private String serial;

  private Model model;
  @Max(value = 500)
  private Float weightLimit ;
  private Float batteryCapacity ;

  private State state;
}
