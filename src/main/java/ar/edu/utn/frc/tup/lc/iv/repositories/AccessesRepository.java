package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
    //TODO hacer los siguientes metodos con queries y no con _ para que pase el pmd
//    /**
//     * Finds a list of AccessEntity by the visitor's document number.
//     *
//     * @param docNumber the document number of the visitor to search for.
//     * @return a list of AccessEntity associated with the visitor's document number.
//     */
//    List<AccessEntity> findByAuth_Visitor_docNumber(Long docNumber);
//    /**
//     * Searches for AccessEntity by either the visitor's name or last name.
//     *
//     * @param name the first name of the visitor.
//     * @param lastName the last name of the visitor.
//     * @return a list of AccessEntity that match the visitor's first name or last name.
//     */
//    List<AccessEntity> searchByAuth_Visitor_NameOrAuth_Visitor_LastName(String name, String lastName);

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
     * @return a list of AccessEntity that match the specified visitor type and external ID.
     */
    List<AccessEntity> findByAuthVisitorTypeAndAuthExternalID(VisitorType visitorType, Long externalID);
}

