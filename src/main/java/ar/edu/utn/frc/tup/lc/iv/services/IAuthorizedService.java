package ar.edu.utn.frc.tup.lc.iv.services;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthorizedDTO;

/**
 * This interface defines the contract for a service
 * that manages authorized persons.
 * It provides a method to retrieve all authorized persons.
 */
public interface IAuthorizedService {
    /**
     * Retrieves a list of all authorized persons.
     * 
     * @return A list of {@link AuthorizedDTO} representing all authorized persons.
     */
    List<AuthorizedDTO> getAuthorized();
}
