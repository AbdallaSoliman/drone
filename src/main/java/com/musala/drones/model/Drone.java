package com.musala.drones.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
public class Drone implements Serializable {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @ColumnDefault("random_uuid()")
  @Type(type = "uuid-char")
  private UUID id;

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

  @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
  private Set<DroneMedication> droneMedications = new HashSet<>();

  public void addMedication(Medication medication, Integer count) {
    boolean newMedication = true;
    for (DroneMedication droneMedication : droneMedications) {
      if (droneMedication.getDrone().equals(this)
              && droneMedication.getMedication().getId().equals(medication.getId())) {
        droneMedication.setCount(droneMedication.getCount() + count);
        newMedication = false;
      }
    }
    if (newMedication) {
      DroneMedication droneMedication = new DroneMedication(this, medication, count);
      droneMedications.add(droneMedication);
    }
  }

  public void removeMedication(Medication medication, Integer count) {
    for (Iterator<DroneMedication> iterator = droneMedications.iterator(); iterator.hasNext(); ) {
      DroneMedication droneMedication = iterator.next();

      if (droneMedication.getDrone().equals(this)
          && droneMedication.getMedication().getId().equals(medication.getId())) {
        if (droneMedication.getCount() > count) {
          droneMedication.setCount(droneMedication.getCount() - count);
        } else {
          iterator.remove();
          droneMedication.setDrone(null);
          droneMedication.setMedication(null);
        }
      }
    }
  }
}
