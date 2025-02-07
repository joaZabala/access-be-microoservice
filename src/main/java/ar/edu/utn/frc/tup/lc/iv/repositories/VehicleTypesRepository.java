package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.VehicleTypesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing vehicle type records from the database.
 */
@Repository
public interface VehicleTypesRepository extends JpaRepository<VehicleTypesEntity, Long> {
}
