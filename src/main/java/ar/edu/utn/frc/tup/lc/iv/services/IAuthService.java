package ar.edu.utn.frc.tup.lc.iv.services;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;

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
     * Retrieves a list of individual authorizations
     * by document number.
     *
     * @param visitorType document number.
     * @return list of authorized persons.
     */
    List<AuthDTO> getAuthsByType(VisitorType visitorType);

    /**
     * Retrieves a list of individual authorizations
     * by document number.
     *
     * @param visitorType document number.
     * @param externalID document number.
     * @return list of authorized persons.
     */
    List<AuthDTO> getAuthsByTypeAndExternalId(VisitorType visitorType, Long externalID ,  Long plotId);

    /**
     * Authorize visitor with authorized ranges.
     *
     * @param visitorAuthRequest request.
     * @param creatorID creator.
     * @return authorization created.
     */
    AuthDTO createAuthorization(VisitorAuthRequest visitorAuthRequest, Long creatorID);

    /**
     * Authorize visitor with authorized ranges.
     * @param accessDTO access
     * @param guardID guard
     * @return authorization
     */
    AccessDTO authorizeVisitor(AccessDTO accessDTO, Long guardID);

    /**
     * Retrieves a list of valid authorizations
     * by document number.
     *
     * @param docNumber document number.
     * @return list of valid authorizations.
     */
    List<AuthDTO> getValidAuthsByDocNumber(Long docNumber);

    /**
     * update authorization list with new authorized ranges.
     *
     * @param existingAuth       existing authorization
     * @param visitorDTO         visitor
     * @param visitorAuthRequest request
     * @return updated authorization
     */
    AuthDTO updateAuthorization(AuthDTO existingAuth, VisitorDTO visitorDTO,
                                VisitorAuthRequest visitorAuthRequest);

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
