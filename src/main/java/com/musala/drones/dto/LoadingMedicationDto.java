package com.musala.drones.dto;

import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoadingMedicationDto implements Serializable {
    @JMap
    private Integer count;
    @JMap(value = "${medication.id}")
    private UUID id;
}
