package com.shaheen.drones.repository;

import com.shaheen.drones.model.Drone;
import com.shaheen.drones.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer>, RevisionRepository<Medication, Integer, Integer> {
}
