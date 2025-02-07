package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.EntryReport.EntryReport;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
public interface AccessesRepository extends JpaRepository<AccessEntity, Long> {
    /** Represent a fromDate param. */
    String  FROM_DATE = "fromDate";
    /** Represent a toDate param. */
    String  TO_DATE = "toDate";
    /**
     * Finds a list of AccessEntity by the given AuthEntity.
     *
     * @param authEntity the authorization entity to search for.
     * @return a list of AccessEntity that match the given authorization entity.
     */
    List<AccessEntity> findByAuth(AuthEntity authEntity);
    /**
     * Finds a list of AccessEntity by the specified action type.
     *
     * @param actionType the action type to search for.
     * @return a list of AccessEntity that match the given action type.
     */
    List<AccessEntity> findByAction(ActionTypes actionType);


    /**
     * Finds a list of AccessEntity by the visitor's document number.
     *
     * @param docNumber the document number of the visitor to search for.
     * @return a list of AccessEntity associated with the visitor's
     * document number.
     */
    List<AccessEntity> findByAuthVisitorDocNumber(Long docNumber);
    /**
     * Searches for AccessEntity by either the visitor's name or last name.
     * @param name the first name of the visitor.
     * @param lastName the last name of the visitor.
     * @return a list of AccessEntity that match the visitor's
     * first name or last name.
     */
    List<AccessEntity> searchByAuthVisitorNameOrAuthVisitorLastName(String name, String lastName);

    /**
     * Finds a list of AccessEntity by the visitor's type.
     *
     * @param visitorType the type of visitor to search for.
     * @return a list of AccessEntity that match the specified visitor type.
     */
    List<AccessEntity> findByAuthVisitorType(VisitorType visitorType);
    /**
     * Finds a list of AccessEntity by the visitor's type and the external ID.
     * @param visitorType the type of visitor to search for.
     * @param externalID the external ID associated with the authorization.
     * @return a list of AccessEntity that match the specified visitor type
     * and external ID.
     */
    List<AccessEntity> findByAuthVisitorTypeAndAuthExternalID(VisitorType visitorType, Long externalID);

    /**
     *
     * @param carPlate plate of the car.
     * @return a list of AccessEntity that match the specified car plate.
     */
    List<AccessEntity> findByVehicleReg(String carPlate);
    /**
     * Retrieves a paginated list of AccessEntity
     * based on specified filters.
     * @param spec     Specification defining the filters .
     * @param pageable Pagination information.
     * @return records that match the given specification and pagination.
     */
    Page<AccessEntity> findAll(Specification<AccessEntity> spec, Pageable pageable);
    /**
     * Retrieves access counts grouped by hour within the specified date range.
     * @param fromDate the start date and time (inclusive).
     * @param toDate   the end date and time (inclusive).
     * @return a list of Object arrays where each array contains:
     * String: formatted hour - Long: count of accesses during that hour.
     */
    @Query(value = "SELECT DATE_FORMAT(action_date, '%H:00') AS hour, COUNT(*) AS count "
            + "FROM accesses "
            + "WHERE action_date BETWEEN :fromDate AND :toDate "
            + "GROUP BY DATE_FORMAT(action_date, '%H:00') "
            + "ORDER BY hour", nativeQuery = true)
    List<Object[]> findAccessCountsByHourNative(@Param(FROM_DATE) LocalDateTime fromDate,
                                                @Param(TO_DATE) LocalDateTime toDate);
    /**
     * Retrieves access counts grouped by weekday within the specified date range.
     * @param fromDate the start date and time (inclusive).
     * @param toDate   the end date and time (inclusive).
     * @return a list of Object arrays where each array contains:
     * String: formatted week - Long: count of accesses during that week.
     */
    @Query(value = "SELECT DAYOFWEEK(action_date) AS dayOfWeek, "
            + "SUM(CASE WHEN action = 'ENTRY' THEN 1 ELSE 0 END) AS entryCount, "
            + "SUM(CASE WHEN action = 'EXIT' THEN 1 ELSE 0 END) AS exitCount "
            + "FROM accesses "
            + "WHERE action_date BETWEEN :fromDate AND :toDate "
            + "GROUP BY DAYOFWEEK(action_date) "
            + "ORDER BY dayOfWeek", nativeQuery = true)
    List<Object[]> findAccessCountsByDayOfWeekNative(@Param(FROM_DATE) LocalDateTime fromDate,
                                                     @Param(TO_DATE) LocalDateTime toDate);

    /**
     * Counts entries and exits between two dates.
     * @param startDate date to start counting from.
     * @param endDate date to end counting.
     * @return a {@link EntryReport} the count of entries and exits.
     */
    @Query("SELECT new ar.edu.utn.frc.tup.lc.iv.dtos.common.EntryReport.EntryReport("
            + "SUM(CASE WHEN a.action = 'ENTRY' THEN 1 ELSE 0 END), "
            + "SUM(CASE WHEN a.action = 'EXIT' THEN 1 ELSE 0 END)) "
            + "FROM AccessEntity a WHERE a.actionDate BETWEEN :startDate AND :endDate")
    EntryReport countEntriesAndExitsBetweenDates(@Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);
    /**
     * Retrieves access counts grouped by hour within the specified date range.
     * @param fromDate the start date and time (inclusive).
     * @param toDate   the end date and time (inclusive).
     * @return a list of Object arrays where each array contains:
     * String: formatted hour - Long: count of accesses during that hour.
     */
    @Query(value = "SELECT auth.visitor_type AS visitorType, "
            + "SUM(CASE WHEN a.action = 'ENTRY' THEN 1 ELSE 0 END) AS entryCount, "
            + "SUM(CASE WHEN a.action = 'EXIT' THEN 1 ELSE 0 END) AS exitCount "
            + "FROM accesses a "
            + "JOIN auths as auth ON a.auth_id = auth.id "
            + "WHERE a.action_date BETWEEN :fromDate AND :toDate "
            + "GROUP BY auth.visitor_type "
            + "ORDER BY visitorType", nativeQuery = true)
    List<Object[]> findAccessCountsByVisitorType(@Param(FROM_DATE) LocalDateTime fromDate,
                                                 @Param(TO_DATE) LocalDateTime toDate);
    /**
     * @param fromDate   the start date and time (inclusive).
     * @param toDate     the end date and time (inclusive).
     * @param visitorType the type of visitor to filter by (nullable).
     * @param action     the action type to filter by (nullable).
     * @param dateFormat represent a format of group
     * @return a list of Object arrays where each array contains:
     */
    @Query("SELECT FUNCTION('DATE_FORMAT', a.actionDate, :dateFormat) AS day, COUNT(a) AS count "
            + "FROM AccessEntity a "
            + "JOIN a.auth auth "
            + "WHERE a.actionDate BETWEEN :fromDate AND :toDate and "
            + "(:visitorType is null or a.auth.visitorType = :visitorType) and "
            + "(:action is null or a.action = :action)"
            + "GROUP BY FUNCTION('DATE_FORMAT', a.actionDate, :dateFormat) "
            + "ORDER BY day")
    List<Object[]> findAccessCountsByGroup(@Param(FROM_DATE) LocalDateTime fromDate,
                                         @Param(TO_DATE) LocalDateTime toDate,
                                         @Param("visitorType") VisitorType visitorType,
                                         @Param("action") ActionTypes action,
                                         @Param("dateFormat") String dateFormat);

    /**
     * @param fromDate   the start date and time (inclusive).
     * @param toDate     the end date and time (inclusive).
     * @param visitorType the type of visitor to filter by (nullable).
     * @param action     the action type to filter by (nullable).
     * @param dateFormat represent a format of group
     * @return list an inconsistent access  grouped by period
     */
    @Query("SELECT FUNCTION('DATE_FORMAT', a.actionDate, :dateFormat) AS day, COUNT(a) AS count "
            + "FROM AccessEntity a "
            + "JOIN a.auth auth "
            + "WHERE a.actionDate BETWEEN :fromDate AND :toDate and "
            + "(:visitorType is null or a.auth.visitorType = :visitorType) and "
            + "(:action is null or a.action = :action) AND a.isInconsistent = true "
            + "GROUP BY FUNCTION('DATE_FORMAT', a.actionDate, :dateFormat) "
            + "ORDER BY day")
    List<Object[]> findInconsistentAccessCountsByGroup(@Param(FROM_DATE) LocalDateTime fromDate,
                                                       @Param(TO_DATE) LocalDateTime toDate,
                                                       @Param("visitorType") VisitorType visitorType,
                                                       @Param("action") ActionTypes action,
                                                       @Param("dateFormat") String dateFormat);

    /**
     * @param fromDate   the start date and time (inclusive).
     * @param toDate     the end date and time (inclusive).
     * @param visitorType the type of visitor to filter by (nullable).
     * @param action     the action type to filter by (nullable).
     * @param dateFormat represent a format of group
     * @return list an inconsistent access  grouped by period
     */
    @Query("SELECT FUNCTION('DATE_FORMAT', a.actionDate, :dateFormat) AS day, COUNT(a) AS count "
            + "FROM AccessEntity a "
            + "JOIN a.auth auth "
            + "WHERE a.actionDate BETWEEN :fromDate AND :toDate and "
            + "(:visitorType is null or a.auth.visitorType = :visitorType) and "
            + "(:action is null or a.action = :action) AND a.notified = true "
            + "GROUP BY FUNCTION('DATE_FORMAT', a.actionDate, :dateFormat) "
            + "ORDER BY day")
    List<Object[]> findLateAccessCountsByGroup(@Param(FROM_DATE) LocalDateTime fromDate,
                                                       @Param(TO_DATE) LocalDateTime toDate,
                                                       @Param("visitorType") VisitorType visitorType,
                                                       @Param("action") ActionTypes action,
                                                       @Param("dateFormat") String dateFormat);

    /**
     * Retrieves the count of inconsistent access
     * events within the specified date range and filtered by visitor type.
     * @param fromDate the start date and time (inclusive) of the range
     * @param toDate the end date and time (inclusive) of the range
     * @param visitorType the type of visitor to filter by
     * @return the count of inconsistent access events that match the given criteria
     */
    @Query("SELECT COUNT(a) AS count "
            + "FROM AccessEntity a "
            + "WHERE a.actionDate BETWEEN :fromDate AND :toDate and "
            + "(:visitorType is null or a.auth.visitorType = :visitorType) and a.isInconsistent = true")
    Long findAccessInconsistentCounts(@Param(FROM_DATE) LocalDateTime fromDate,
                                           @Param(TO_DATE) LocalDateTime toDate,
                                           @Param("visitorType") VisitorType visitorType);


    /**
     * Retrieves the count of late access
     * events within the specified date range and filtered by visitor type.
     * @param authId the authId
     * @return the count of late access events that match the given criteria
     */
    @Query("SELECT a FROM AccessEntity a WHERE a.auth.authId = :authId ORDER BY a.actionDate DESC")
    List<AccessEntity> findAccessByAuthIdDesc(@Param("authId") Long authId);
}

