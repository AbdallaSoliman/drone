package com.musala.drones.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.musala.drones.model.Model;
import com.musala.drones.model.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
