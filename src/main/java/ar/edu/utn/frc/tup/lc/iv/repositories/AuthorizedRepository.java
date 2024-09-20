package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthorizedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of Authorized.
 */
@Repository
public interface AuthorizedRepository extends JpaRepository<AuthorizedEntity, Long> {
    /**
     * Finds an authorized entity by document number.
     *
     * @param docNumber the document number of the authorized person.
     * @return the authorized entity with the specified document number.
     */
    AuthorizedEntity findByDocNumber(Integer docNumber);
}
