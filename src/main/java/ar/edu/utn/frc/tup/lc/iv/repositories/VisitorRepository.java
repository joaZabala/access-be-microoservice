package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository of Authorized.
 */
@Repository
public interface VisitorRepository extends JpaRepository<VisitorEntity, Long> {
    /**
     * Finds a visitor entity by document number.
     * @param docNumber the document number of the authorized person.
     * @return the visitor entity with the specified document number.
     */
    VisitorEntity findByDocNumber(Long docNumber);

    /**
     * fetch visitor by docNumber.
     *
     * @param isActive if the visitor is active.
     * @param pageable pageable.
     * @return VisitorEntity list.
     */
    @Query("SELECT v FROM VisitorEntity v WHERE v.isActive = :isActive")
    Page<VisitorEntity> findAllByActive(@Param("isActive") boolean isActive, Pageable pageable);

    /**
     * fetch by filter.
     * @param filter the filter.
     * @param pageable pageable.
     * @return VisitorEntity list.
     */
    @Query("SELECT v FROM VisitorEntity v WHERE v.isActive = true AND ("
            + "LOWER(v.name) LIKE LOWER(CONCAT('%', :filter, '%')) OR "
            + "LOWER(v.lastName) LIKE LOWER(CONCAT('%', :filter, '%')) OR "
            + "CAST(v.docNumber AS string) LIKE CONCAT('%', :filter, '%'))")
    Page<VisitorEntity> findByFilter(String filter, Pageable pageable);
    /**
     * Retrieves a paginated list of VisitorEntity
     * based on specified filters.
     * @param spec     Specification defining the filters .
     * @param pageable Pagination information.
     * @return records that match the given specification and pagination.
     */
    Page<VisitorEntity> findAll(Specification<VisitorEntity> spec, Pageable pageable);
}
