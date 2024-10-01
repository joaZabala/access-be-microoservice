package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing supplier
 * access records from the database.
 */
@Repository
public interface SupplierAccessRepository extends JpaRepository<AccessesEntity, Long> {

    /**
     * Retrieves a list of access records for suppliers.
     * @param authTypeDescription the description of the authorization type
     * @param supplierId         the external ID of the supplier
     * @param dateFrom           the start date of the range
     * @param dateTo             the end date of the range
     * @return a list of {@link AccessesEntity} containing the matching access records
     */
    @Query("SELECT a FROM AccessesEntity a "
            + "WHERE a.authRange.authType.description =:authTypeDescription "
            + "AND a.entryDate >= :dateFrom "
            + "AND (a.exitDate <= :dateTo OR (a.exitDate IS NULL AND a.entryDate <= :dateTo)) "
            + "AND a.authRange.externalId = :supplierId "
            + "ORDER BY a.entryDate DESC")
    List<AccessesEntity> getSupplierAccessesList(
            @Param("authTypeDescription") String authTypeDescription,
            @Param("supplierId") Long supplierId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );
}
