package com.musala.drones.model;

import javax.persistence.*;

@Entity
public class MedicationLobs {
  @Id private Long id;

  @OneToOne @MapsId private Medication medication;

  @Lob private byte[] image;
}
