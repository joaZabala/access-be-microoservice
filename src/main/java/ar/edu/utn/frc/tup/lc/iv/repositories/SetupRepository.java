package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.SetupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for setup record from the database.
 */
@Repository
public interface SetupRepository extends JpaRepository<SetupEntity, Long> {
}
