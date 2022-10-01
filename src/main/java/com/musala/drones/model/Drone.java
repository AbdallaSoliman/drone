package com.musala.drones.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Drone {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
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

  @ManyToMany private List<Medication> medicationList;
}
