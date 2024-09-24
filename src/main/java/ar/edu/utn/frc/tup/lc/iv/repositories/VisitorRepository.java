package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of Authorized.
 */
@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Long> {
    /**
     * Finds a visitor entity by document number.
     *
     * @param docNumber the document number of the authorized person.
     * @return the visitor entity with the specified document number.
     */
    VisitorEntity findByDocNumber(Long docNumber);
}
