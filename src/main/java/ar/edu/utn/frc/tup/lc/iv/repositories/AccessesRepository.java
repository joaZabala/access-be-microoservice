package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
     * Retrieves a paginated list of access entities based on filters.
     * @param textFilter   Optional filter for vehicle reg, visitor info, or comments.
     * @param visitorType  Optional type of visitor.
     * @param fromDate     Optional start date for filtering.
     * @param toDate       Optional end date for filtering.
     * @param actionType   Optional type of action.
     * @param documentType Optional visitor document type.
     * @param pageable     Pagination info.
     * @return A page of {@link AccessEntity}.
     */
    @Query("SELECT a FROM AccessEntity a "
            + "LEFT JOIN a.auth auth "
            + "LEFT JOIN auth.visitor visitor "
            + "WHERE (:textFilter IS NULL OR ("
            + "LOWER(a.vehicleReg) LIKE LOWER(CONCAT('%', :textFilter, '%')) OR "
            + "LOWER(CAST(visitor.docNumber AS string)) LIKE LOWER(CONCAT('%', :textFilter, '%')) OR "
            + "LOWER(visitor.name) LIKE LOWER(CONCAT('%', :textFilter, '%')) OR "
            + "LOWER(visitor.lastName) LIKE LOWER(CONCAT('%', :textFilter, '%')) OR "
            + "LOWER(a.comments) LIKE LOWER(CONCAT('%', :textFilter, '%')))) AND "
            + "(:visitorType IS NULL OR auth.visitorType = :visitorType) AND "
            + "(:fromDate IS NULL OR a.actionDate >= :fromDate) AND "
            + "(:toDate IS NULL OR a.actionDate <= :toDate) AND "
            + "(:actionType IS NULL OR a.action = :actionType) AND "
            + "(:documentType IS NULL OR visitor.documentType = :documentType) AND "
            + "(:externalID IS NULL OR auth.externalID = :externalID)")
    Page<AccessEntity> findAccesses(
            @Param("textFilter") String textFilter,
            @Param("visitorType") VisitorType visitorType,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("actionType") ActionTypes actionType,
            @Param("documentType") DocumentType documentType,
            @Param("externalID") Long externalID,
            Pageable pageable);

}

