package com.musala.drones.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Entity
@Getter
@Setter
public class Drone {

  @Id @GeneratedValue private Long id;

  @Column(length = 100)
  private String serial;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private Model model;

  @Max(500)
  private Float weightLimit;

  @Max(100)
  @Min(0)
  private Float batteryCapacity;

  @Enumerated(EnumType.STRING)
  @Column(length = 10)
  private State state;

  @ManyToMany private List<Medication> medicationList;
}
