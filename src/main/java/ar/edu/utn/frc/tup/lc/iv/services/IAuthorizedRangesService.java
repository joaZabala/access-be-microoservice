package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
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
     * Checks if a person with the given document number
     * has a valid invitation.
     * @param documentNumber The person's
     * identification number.
     * @return {@code true} if a
     * valid invitation exists, {@code false} otherwise.
     */
    Boolean hasInvitation(Long documentNumber);

}
