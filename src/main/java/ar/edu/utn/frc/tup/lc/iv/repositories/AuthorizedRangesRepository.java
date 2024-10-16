package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
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
public interface AuthorizedRangesRepository extends JpaRepository<AuthRangeEntity, Long> {
    /**
     * Checks if the visitor has a valid invitation.
     * @param startDate      The date to check for access validity.
     * @param startHour      The time to check for access validity.
     * @param documentNumber The visitor's document number.
     * @param day            The day to check for access validity
     * @return true if the visitor has a valid invitation; false otherwise.
     */

    @Query("SELECT CASE WHEN COUNT(ac) > 0 THEN true ELSE false END "
            + "FROM AuthRangeEntity ac "
            + "LEFT JOIN ac.authId v "
            + "WHERE ac.isActive = true "
            + "AND (ac.dateFrom IS NULL OR ac.dateFrom <= :startDate) "
            + "AND (ac.dateTo IS NULL OR ac.dateTo >= :startDate) "
            + "AND (ac.hourFrom IS NULL OR ac.hourFrom <= :startHour) "
            + "AND (ac.hourTo IS NULL OR ac.hourTo >= :startHour) "
            + "AND v.visitor.docNumber = :documentNumber "
            + "AND (ac.days IS NULL OR ac.days LIKE CONCAT('%', :day, '%'))")
    boolean hasInvitation(
            @Param("startDate") LocalDate startDate,
            @Param("startHour") LocalTime startHour,
            @Param("documentNumber") Long documentNumber,
            @Param("day") String day
    );
}

