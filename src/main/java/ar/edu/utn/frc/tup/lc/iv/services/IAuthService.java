package ar.edu.utn.frc.tup.lc.iv.services;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;

/**
 * This interface defines the contract for a service
 * that manages authorized persons.
 */
public interface IAuthService {

    /**
     * Retrieves a list of all authorized persons.
     *
     * @return A list of {@link AuthDTO} representing
     * all authorized persons.
     */
    List<AuthDTO> getAllAuths();

    /**
     * Retrieves a list of individual authorizations
     * by document number.
     *
     * @param docNumber document number.
     * @return list of authorized persons.
     */
    List<AuthDTO> getAuthsByDocNumber(Long docNumber);

    /**
     * Authorize visitor with authorized ranges.
     *
     * @param visitorAuthRequest request.
     * @return authorization created.
     */
    AuthDTO authorizeVisitor(VisitorAuthRequest visitorAuthRequest);


    /**
     * Retrieves a list of valid authorizations
     * by document number.
     *
     * @param docNumber document number.
     * @return list of valid authorizations.
     */
    List<AuthDTO> getValidAuthsByDocNumber(Long docNumber);
    /**
     * Authorize visitor with authorized ranges.
     *
     * @param visitorAuthRequest request.
     * @return authorization created.
     */
    AuthDTO createAuthorization(VisitorAuthRequest visitorAuthRequest, Long creatorID);

    /**
     * Checks if a person with the given document number
     * has a valid invitation.
     * @param documentNumber The person's
     * identification number.
     * @return {@code true} if a
     * valid invitation exists, {@code false} otherwise.
     */
    Boolean isAuthorized(Long documentNumber);
}
