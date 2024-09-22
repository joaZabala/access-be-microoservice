package ar.edu.utn.frc.tup.lc.iv.services;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequestDto;

/**
 * This interface defines the contract for a service
 * that manages authorized persons.
 * It provides a method to retrieve all authorized persons.
 */
public interface VisitorService {
    /**
     * Retrieves a list of all authorized persons.
     *
     * @return A list of {@link VisitorDTO} representing all authorized persons.
     */
    List<VisitorDTO> getAllVisitors();

    /**
     * Creates a new authorization based on the provided data.
     *
     * @param authorizedRequestDto Object containing the necessary data
     *                             to create a new authorization.
     * @return An AuthorizedDTO object representing
     * the newly created authorization.
     */
    VisitorDTO createNewVisitor(VisitorRequestDto authorizedRequestDto);
}
