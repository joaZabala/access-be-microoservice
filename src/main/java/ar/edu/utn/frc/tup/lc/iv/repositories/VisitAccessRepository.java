package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for accessing visit access records from the database.
 */
@Repository
public interface VisitAccessRepository extends JpaRepository<AccessesEntity, Long> {

    /**
     * Retrieves a list of access records for a specific visitor .
     * @param plotId     the ID of the plot associated with the access records
     * @param visitorId  the ID of the visitor for filtering access records
     * @param dateFrom   the start date of the range for filtering access records
     * @param dateTo     the end date of the range for filtering access records
     * @return a list of {@link AccessesEntity} containing the matching access records
     */
    @Query("SELECT a FROM AccessesEntity a "
            + "LEFT JOIN a.authRange ar "
            + "LEFT JOIN ar.authType at "
            + "LEFT JOIN ar.visitorId v "
            + "WHERE ((a.authRange.authType.description = 'Visitante' AND a.authRange.visitorId.visitorId = :visitorId) "
            + "OR (a.authRange IS NULL AND a.visitor.visitorId = :visitorId))"
            + "AND a.entryDate >= :dateFrom "
            + "AND (a.exitDate <= :dateTo OR (a.exitDate IS NULL AND a.entryDate <= :dateTo)) "
            + "AND a.plotId = :plotId "
            + "ORDER BY a.entryDate DESC")
    List<AccessesEntity> getVisitAccessesList(
            @Param("plotId") Long plotId,
            @Param("visitorId") Long visitorId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

    /**
     * Retrieves a list of access records for all visitors.
     * @param plotId    the ID of the plot associated with the access records
     * @param dateFrom  the start date of the range for filtering access records
     * @param dateTo    the end date of the range for filtering access records
     * @return a list of {@link AccessesEntity} containing the matching access records
     */
    @Query("SELECT a FROM AccessesEntity a "
            + "LEFT JOIN a.authRange ar "
            + "LEFT JOIN ar.authType at "
            + "LEFT JOIN ar.visitorId v "
            + "WHERE ((a.authRange.authType.description = 'Visitante' ) "
            + "OR (a.authRange IS NULL AND a.visitor IS NOT NULL))"
            + "AND a.entryDate >= :dateFrom "
            + "AND (a.exitDate <= :dateTo OR (a.exitDate IS NULL AND a.entryDate <= :dateTo)) "
            + "AND a.plotId = :plotId "
            + "ORDER BY a.entryDate DESC")
    List<AccessesEntity> getVisitAccessesList(
            @Param("plotId") Long plotId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );
}
