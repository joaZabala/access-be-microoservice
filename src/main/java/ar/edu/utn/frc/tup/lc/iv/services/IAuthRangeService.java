package ar.edu.utn.frc.tup.lc.iv.services;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;

/**
 *  This interface defines the contract for a service
 *  that manages authorized ranges.
 *  It provides a method to retrieve all authorized ranges.
 */
public interface IAuthRangeService {

    /**
     * Retrieves all authorized ranges.
     * @return the list of authorized ranges
     */
    List<AuthRangeDTO> getAuthRanges();
    
}
