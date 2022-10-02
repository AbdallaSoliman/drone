package com.musala.drones.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.musala.drones.model.enumerate.Model;
import com.musala.drones.model.enumerate.State;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JGlobalMap
public class DroneRequestDto implements Serializable {
  @Schema(example = "101")
  @Size(max = 100,message = "serial size must be between 0 and {max}")
  private String serial;

  private Model model;

  @Max(value = 500, message = "weightLimit should not be greater than or equal to {value}")
  @Min(value = 0, message = "weightLimit should not be less  than or equal to  {value}")
  private Float weightLimit;

  @Max(value = 100, message = "batteryCapacity should not be greater than or equal to {value}")
  @Min(value = 0, message = "batteryCapacity should not be less  than or equal to  {value}")
  private Float batteryCapacity;

  private State state;
}
