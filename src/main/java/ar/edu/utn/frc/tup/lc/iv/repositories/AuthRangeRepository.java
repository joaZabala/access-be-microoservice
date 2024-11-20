package ar.edu.utn.frc.tup.lc.iv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;

import java.util.List;

/**
 * repository for authorized ranges.
 */
@Repository
public interface AuthRangeRepository extends JpaRepository<AuthRangeEntity, Long> {

    // find by auth id

    /**
     * Finds the list of authorized ranges for a specific auth entity.
     *
     * @param authEntity The auth entity to search for.
     * @return List of authorized ranges associated with the given auth entity.
     */
    List<AuthRangeEntity> findByAuthId(AuthEntity authEntity);

    /**
     * Finds the list of authorized ranges for a given external ID.
     *
     * @param externalID The external ID associated with an auth entity.
     * @return List of authorized ranges matching the external ID.
     */
    // PMD ignore MethodNamingConventions
    List<AuthRangeEntity> findByAuthIdExternalID(Long externalID);

    /**
     * Finds the list of authorized ranges for a given external ID and plot ID.
     *
     * @param externalID The external ID associated with an auth entity.
     * @param plotId The plot ID associated with an auth entity.
     * @return List of authorized ranges matching the external ID and plot ID.
     */
    // PMD ignore MethodNamingConventions
    List<AuthRangeEntity> findByAuthIdExternalIDAndAuthIdPlotId(Long externalID, Long plotId);


}
