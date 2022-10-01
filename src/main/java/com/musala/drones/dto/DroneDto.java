package com.musala.drones.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.musala.drones.model.Model;
import com.musala.drones.model.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JGlobalMap
public class DroneDto {
  @Size(max = 100,message = "{0} size must be between 0 and {max}")
  private String serial;

  private Model model;

  @Max(value = 500, message = "{0} should not be greater than or equal to {value}")
  @Min(value = 0, message = "{0} should not be less  than or equal to  {value}")
  private Float weightLimit;

  @Max(value = 100, message = "{0} should not be greater than or equal to {value}")
  @Min(value = 0, message = "{0} should not be less  than or equal to  {value}")
  private Float batteryCapacity;

  private State state;
}
