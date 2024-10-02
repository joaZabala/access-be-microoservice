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
            "FROM AccessControl ac " +
            "WHERE ac.IsActive " +
            "AND (:startDate IS NULL OR (ac.StartDate IS NULL OR ac.StartDate <= :startDate) " +
            "AND (ac.EndDate IS NULL OR ac.EndDate >= :startDate)) " +
            "AND (:startHour IS NULL OR (ac.StartHour IS NULL OR ac.StartHour <= :startHour) " +
            "AND (ac.EndHour IS NULL OR ac.EndHour >= :startHour)) " +
            "AND ac.DocumentNumber = :documentNumber " +
            "AND (:day IS NULL OR ac.days LIKE CONCAT('%', :day, '%'))")
    boolean hasInvitation(
            @Param("startDate") LocalDate startDate,
            @Param("startHour") LocalTime startHour,
            @Param("documentNumber") Long documentNumber,
            @Param("day") String day
    );
}
