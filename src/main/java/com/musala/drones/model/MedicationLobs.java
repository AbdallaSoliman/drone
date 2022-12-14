package com.musala.drones.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
public class MedicationLobs implements Serializable {
  @Id
  @Type(type = "uuid-char")
  private UUID id;

  @OneToOne @MapsId private Medication medication;

  @Lob private byte[] image;
}
