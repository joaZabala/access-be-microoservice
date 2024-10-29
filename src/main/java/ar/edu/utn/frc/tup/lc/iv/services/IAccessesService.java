package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;

import java.util.List;

/**
 * This interface defines the contract for a service
 * that manages authorized persons.
 */
public interface IAccessesService {

    /**
     * Retrieves all access records.
     * @return A list of {@link AccessDTO} representing
     * all access records.
     */
    List<AccessDTO> getAllAccess();

    /**
     * Retrieves all entries (access actions of type ENTRY).
     * @return A list of {@link AccessDTO} representing entry records.
     */
    List<AccessDTO> getAllEntries();

    /**
     * Retrieves all exits (access actions of type EXIT).
     * @return A list of {@link AccessDTO} representing exit records.
     */
    List<AccessDTO> getAllExits();

    /**
     * Retrieves access records filtered by visitor type.
     * @param visitorType The type of the visitor to filter by.
     * @return A list of {@link AccessDTO} representing access
     * records for the specified visitor type.
     */
    List<AccessDTO> getAllAccessByType(VisitorType visitorType);

    /**
     * Retrieves access records filtered by visitor type and external ID.
     * @param visitorType The type of the visitor to filter by.
     * @param externalId The external ID associated with the access records.
     * @return A list of {@link AccessDTO} representing access
     * records for the specified visitor type and external ID.
     */
    List<AccessDTO> getAllAccessByTypeAndExternalID(VisitorType visitorType, Long externalId);


    //    List<AccessDTO> getMissingExits();

    /**
     * Registers a new access entry in the system.
     *
     * @param accessEntity The {@link AccessEntity} to be registered.
     * @return A {@link AccessDTO} representing the registered access.
     */
    AccessDTO registerAccess(AccessEntity accessEntity);

    /**
     * Checks if a visitor can do an action.
     * @param carPlate plate of the car.
     * @param action action to do.
     * @return true if the visitor can do the action, false otherwise.
     */
    Boolean canDoAction(String carPlate, ActionTypes action);
}
