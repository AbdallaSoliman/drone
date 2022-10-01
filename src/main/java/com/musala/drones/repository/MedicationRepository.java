package com.musala.drones.repository;

import com.musala.drones.model.Medication;
import org.springframework.data.repository.CrudRepository;

public interface MedicationRepository extends CrudRepository<Medication, Long> {

}
