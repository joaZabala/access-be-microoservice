package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthorizedRangesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Repository of AuthorizedRanges.
 */
@Service
public interface AuthorizedRangesRepository extends JpaRepository<AuthorizedRangesEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(ac) > 0 THEN true ELSE false END " +
            "FROM AuthorizedRangesEntity ac " +
            "LEFT JOIN ac.visitorId v " +
            "WHERE ac.isActive = true " +
            "AND (ac.dateFrom IS NULL OR ac.dateFrom <= :startDate) " +
            "AND (ac.dateTo IS NULL OR ac.dateTo >= :startDate) " +
            "AND (ac.hourFrom IS NULL OR ac.hourFrom <= :startHour) " +
            "AND (ac.hourTo IS NULL OR ac.hourTo >= :startHour) " +
            "AND v.docNumber = :documentNumber " +
            "AND (ac.days IS NULL OR ac.days LIKE CONCAT('%', :day, '%'))")
    boolean hasInvitation(
            @Param("startDate") LocalDate startDate,
            @Param("startHour") LocalTime startHour,
            @Param("documentNumber") Long documentNumber,
            @Param("day") String day
    );
}

