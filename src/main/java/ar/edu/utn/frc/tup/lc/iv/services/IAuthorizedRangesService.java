package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.models.AuthorizedRanges;

/**
 * Service interface for managing authorized ranges.
 * This interface defines the methods for handling
 * operations related to authorized ranges, including
 * creating, updating, retrieving, and deleting authorized ranges.
 */
public interface IAuthorizedRangesService {
    /**
     * Registers a new authorized range.
     *
     * @param authorizedRangeDTO the data transfer object containing the
     *  details of the authorized range to register.
     *  @return AuthorizedRanges
     */
    AuthorizedRanges registerAuthorizedRange(RegisterAuthorizationRangesDTO authorizedRangeDTO);
    /**
     * Checks if a person with a given document number has a valid invitation based on
     * the current date, time, and day of the week.
     *
     * @param documentNumber the unique identification number of the person being checked
     * @return {@code true} if there is a valid invitation for the specified document number,
     *         {@code false} otherwise
     */
    Boolean HasInvitation(Long documentNumber);
}
