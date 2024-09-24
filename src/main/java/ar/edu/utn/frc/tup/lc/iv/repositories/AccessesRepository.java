package ar.edu.utn.frc.tup.lc.iv.repositories;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccessesRepository extends JpaRepository<AccessesEntity, Long> {

    @Query("SELECT a FROM AccessesEntity a " +
            "LEFT JOIN a.authRange ar " +
            "LEFT JOIN ar.authType at " +
            "LEFT JOIN ar.visitorId v " +
            "WHERE ((a.authRange.authType.description = 'Visitante' AND a.authRange.visitorId.visitorId = :visitorId) "+
                    "OR (a.authRange IS NULL AND a.visitor.visitorId = :visitorId))" +
            "AND a.entryDate >= :dateFrom " +
            "AND a.exitDate <= :dateTo " +
            "AND a.ownerId = :owner " +
            "ORDER BY a.entryDate DESC")
    List<AccessesEntity> getVisitAccessesList(
            @Param("owner") Long owner,
            @Param("visitorId") Long visitorId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

    @Query("SELECT a FROM AccessesEntity a " +
            "LEFT JOIN a.authRange ar " +
            "LEFT JOIN ar.authType at " +
            "LEFT JOIN ar.visitorId v " +
            "WHERE ((a.authRange.authType.description = 'Visitante' ) "+
                    "OR (a.authRange IS NULL AND a.visitor IS NOT NULL))" +
            "AND a.entryDate >= :dateFrom " +
            "AND a.exitDate <= :dateTo " +
            "AND a.ownerId = :owner " +
            "ORDER BY a.entryDate DESC")
    List<AccessesEntity> getVisitAccessesList(
            @Param("owner") Long owner,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

    @Query("SELECT a FROM AccessesEntity a " +
            "WHERE a.authRange.authType.description =:authTypeDescription " +
            "AND a.authRange.externalId = :externalId "+
            "AND a.entryDate >= :dateFrom " +
            "AND a.exitDate <= :dateTo " +
            "ORDER BY a.entryDate DESC")
    List<AccessesEntity> getExternalAccessesList(
            @Param("authTypeDescription") String authTypeDescription,
            @Param("externalId") Long externalId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

    @Query("SELECT a FROM AccessesEntity a " +
            "LEFT JOIN a.authRange ar " +
            "LEFT JOIN ar.authType at " +
            "LEFT JOIN ar.visitorId v " +
            "WHERE ((a.visitor IS NULL AND a.ownerId IS NULL) "+
                "OR (a.visitor IS NOT NULL AND a.ownerId IS NOT NULL))"+
            "AND a.exitDate IS NULL " +
            "ORDER BY a.entryDate DESC"
    )
    List<AccessesEntity> getNoExitAccessesList(
    );

    @Query("SELECT a FROM AccessesEntity a " +
            "WHERE a.authRange.authType.description = 'Dueño' " +
            "AND a.authRange.plotId =:plotId " +
            "AND a.entryDate >= :dateFrom " +
            "AND a.exitDate <= :dateTo " +
            "ORDER BY a.entryDate DESC")
    List<AccessesEntity> getOwnerAccessesList(
            @Param("plotId") Long plotId,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );
}
