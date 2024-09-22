package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.VisitorsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of Authorized.
 */
@Repository
public interface VisitorsRepository extends JpaRepository<VisitorsEntity, Long> {
    /**
     * Finds an visitor entity by document number.
     *
     * @param docNumber the document number of the authorized person.
     * @return the visitor entity with the specified document number.
     */
    VisitorsEntity findByDocNumber(Integer docNumber);
}
