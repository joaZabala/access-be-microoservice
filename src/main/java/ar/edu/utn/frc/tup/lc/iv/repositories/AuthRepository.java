package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing visit access records from the database.
 */
@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, Long> {


    /**
     * Finds authorization records for a specific visitor.
     *
     * @param visitor The visitor entity to search for.
     * @return A list of authorization records associated with the given visitor.
     */
    List<AuthEntity> findByVisitor(VisitorEntity visitor);

    /**
     * Finds authorization records by the type of visitor.
     *
     * @param visitorType The type of visitor (e.g., Resident, Guest).
     * @return A list of authorization records associated with the given visitor type.
     */
    List<AuthEntity> findByVisitorType(VisitorType visitorType);

    /**
     * Finds authorization records for a specific visitor
     * , external ID, and plot ID.
     * @param visitorType The type of visitor (e.g., Resident, Guest).
     * @param externalID  The external ID associated with the authorization.
     * @param plotId      The plot ID where the authorization applies.
     * @return A list of authorization records matching the given criteria.
     */
    List<AuthEntity> findByVisitorTypeAndExternalIDAndPlotId(VisitorType visitorType, Long externalID, Long plotId);
    /**
     * Finds an {@link AuthEntity} by the specified visitor,
     * visitor type, and plot ID.
     * @param visitor     the visitor entity to search for.
     * @param visitorType the type of visitor.
     * @param plotId     the ID of the plot associated with the authorization.
     * @return the matching {@link AuthEntity}, or null if not found.
     */
    AuthEntity findByVisitorAndVisitorTypeAndPlotId(VisitorEntity visitor, VisitorType visitorType, Long plotId);
    /**
     * Finds an {@link AuthEntity}.
     * @param id     unique identifier.
     * @return the matching {@link AuthEntity}, or null if not found.
     */
    List<AuthEntity> findByAuthId(Long id);
    /**
     * Retrieves a paginated list of AuthEntity
     * based on specified filters.
     * @param spec     Specification defining the filters .
     * @param pageable Pagination information.
     * @return records that match the given specification and pagination.
     */
    Page<AuthEntity> findAll(Specification<AuthEntity> spec, Pageable pageable);

}
