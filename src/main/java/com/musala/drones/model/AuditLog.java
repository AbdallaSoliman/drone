package com.musala.drones.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AuditLog {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @ColumnDefault("random_uuid()")
  @Type(type = "uuid-char")
  private UUID id;

  @CreationTimestamp private LocalDateTime createDateTime;
  @ManyToOne
//  @JoinColumn(name = "drone_id")
  private Drone drone;
  @Max(100)
  @Min(0)
  private Float batteryCapacity;

  public AuditLog(Drone drone, Float batteryCapacity) {
    this.drone = drone;
    this.batteryCapacity = batteryCapacity;
  }
}
