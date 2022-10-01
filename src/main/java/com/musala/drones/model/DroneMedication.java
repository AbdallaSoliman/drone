package com.musala.drones.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import java.io.Serializable;
import java.time.LocalDateTime;

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
  @CreationTimestamp private LocalDateTime createDateTime;
  @UpdateTimestamp private LocalDateTime updateDateTime;

  public DroneMedication(Drone drone, Medication medication, Integer count) {
    this.drone = drone;
    this.medication = medication;
    this.count = count;
    this.id = new DroneMedicationPK(drone.getId(), medication.getId());
  }
}
