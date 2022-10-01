package com.musala.drones.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class MedicationLobs {
  @Id
  @Type(type = "uuid-char")
  private UUID id;

  @OneToOne @MapsId private Medication medication;

  @Lob private byte[] image;
}
