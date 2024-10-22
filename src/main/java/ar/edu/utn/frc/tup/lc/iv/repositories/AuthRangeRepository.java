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
     * find by auth id.
     * @param authEntity auth entity
     * @return auth range list
     */
    List<AuthRangeEntity> findByAuthId(AuthEntity authEntity);

    /**
     *  find by auth id and external id.
     * @param externalID external id.
     * @return auth range list.
     */
    List<AuthRangeEntity> findByAuthId_ExternalID(Long externalID);

}
