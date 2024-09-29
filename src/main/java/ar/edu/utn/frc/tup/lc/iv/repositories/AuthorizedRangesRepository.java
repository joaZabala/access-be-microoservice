package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthorizedRangesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
/**
 * Repository of AuthorizedRanges.
 */
@Service
public interface AuthorizedRangesRepository extends JpaRepository<AuthorizedRangesEntity, Long> {
}
