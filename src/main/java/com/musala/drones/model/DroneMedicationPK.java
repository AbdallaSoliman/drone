package com.musala.drones.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DroneMedicationPK implements Serializable {
  @Type(type = "uuid-char")
  private UUID droneId;
  @Type(type = "uuid-char")
  private UUID medicationId;

}
