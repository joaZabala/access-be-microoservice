package ar.edu.utn.frc.tup.lc.iv.repositories;

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
    List<Object[]> findAccessCountsByHourNative(@Param("fromDate") LocalDateTime fromDate,
                                                @Param("toDate") LocalDateTime toDate);
    /**
     * Retrieves access counts grouped by weekday within the specified date range.
     * @param fromDate the start date and time (inclusive).
     * @param toDate   the end date and time (inclusive).
     * @return a list of Object arrays where each array contains:
     * String: formatted week - Long: count of accesses during that week.
     */
    @Query(value = "SELECT DAYOFWEEK(action_date) AS dayOfWeek, COUNT(*) AS count " +
            "FROM accesses " +
            "WHERE action_date BETWEEN :fromDate AND :toDate " +
            "GROUP BY DAYOFWEEK(action_date) " +
            "ORDER BY dayOfWeek", nativeQuery = true)
    List<Object[]> findAccessCountsByDayOfWeekNative(@Param("fromDate") LocalDateTime fromDate,
                                                     @Param("toDate") LocalDateTime toDate);
}

