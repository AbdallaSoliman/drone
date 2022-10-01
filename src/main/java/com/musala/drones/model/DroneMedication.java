package com.musala.drones.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DroneMedication implements Serializable {
  @EmbeddedId private DroneMedicationPK id;

  @ManyToOne
  @MapsId("droneId")
  private Drone drone;

  @ManyToOne
  @MapsId("medicationId")
  private Medication medication;

  private Integer count;
  private Date loadedOn = new Date();

  public DroneMedication(Drone drone, Medication medication, Integer count) {
    this.drone = drone;
    this.medication = medication;
    this.count = count;
    this.id = new DroneMedicationPK(drone.getId(), medication.getId());
  }
}
