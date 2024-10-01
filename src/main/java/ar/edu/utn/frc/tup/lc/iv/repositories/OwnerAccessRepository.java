package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing owner and
 * resident access records from the database.
 */
@Repository
public interface OwnerAccessRepository extends JpaRepository<AccessesEntity, Long> {

    /**
     * Retrieves a list of access records for owners or residents.
     * @param plotId   the ID of the plot associated with the access records
     * @param dateFrom the start date of the range for filtering access records
     * @param dateTo   the end date of the range for filtering access records
     * @return a list of {@link AccessesEntity} containing the matching access records
     */
    @Query("SELECT a FROM AccessesEntity a "
            + "LEFT JOIN a.authRange ar "
            + "LEFT JOIN ar.authType at "
            + "LEFT JOIN ar.visitorId v "
            + "WHERE (a.authRange.authType.description = 'Propietario' OR a.authRange.authType.description = 'Conviviente') "
            + "AND ((a.entryDate IS NULL AND a.exitDate >= :dateFrom AND a.exitDate <= :dateTo) "
            + "OR (a.exitDate IS NULL AND a.entryDate >= :dateFrom AND a.entryDate <= :dateTo)) "
            + "AND a.authRange.plotId = :plotId "
            + "ORDER BY a.entryDate DESC")
    List<AccessesEntity> getOwnerAccessesList(
            @Param("plotId") Long plotId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );
}
