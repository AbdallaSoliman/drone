package com.musala.drones.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MedicationLobs {
  @Id private Long id;

  @OneToOne @MapsId private Medication medication;

  @Lob private byte[] image;
}
