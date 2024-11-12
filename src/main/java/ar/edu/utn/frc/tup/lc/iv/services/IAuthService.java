package ar.edu.utn.frc.tup.lc.iv.services;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;

/**
 * This interface defines the contract for a service
 * that manages authorized persons.
 */
public interface IAuthService {

    /**
     * Retrieves a list of all authorized persons.
     * @param filter object with fitlers
     * @param page      The page number for pagination (default is 0).
     * @param size      The number of records per page (default is 10).
     * @return A list of {@link AuthDTO} representing
     * all authorized persons.
     */
    List<AuthDTO> getAllAuths(AuthFilter filter, int page, int size);

    /**
     * Retrieves a list of individual authorizations
     * by document number.
     * @param docNumber the document number of the authorized person.
     * @return a list of {@link AuthDTO}
     * representing the authorized persons.
     */
    List<AuthDTO> getAuthsByDocNumber(Long docNumber);

    /**
     * Retrieves a list of individual authorizations
     * by document number.
     *
     * @param visitorType the type of the visitor.
     * @return a list of {@link AuthDTO} representing the authorized persons.
     */
    List<AuthDTO> getAuthsByType(VisitorType visitorType);

    /**
     * Retrieves individual authorizations by document number.
     * @param visitorType the type of the visitor.
     * @param externalID  the external ID for the authorization.
     * @param plotId      the plot ID for the authorization.
     * @return a list of {@link AuthDTO} authorized persons.
     */

    List<AuthDTO> getAuthsByTypeAndExternalId(VisitorType visitorType, Long externalID, Long plotId);

    /**
     * Authorize visitor with authorized ranges.
     * @param visitorAuthRequest the request containing
     *                           visitor authorization details.
     * @return the created {@link AuthDTO} authorization.
     */
    AuthDTO createAuthorization(VisitorAuthRequest visitorAuthRequest);

    /**
     * Authorize visitor with authorized ranges.
     *
     * @param accessDTO the access details for the visitor.
     * @param guardID the ID of the guard authorizing the access.
     * @return the created {@link AccessDTO} authorization.
     */
    AccessDTO authorizeVisitor(AccessDTO accessDTO, Long guardID);

    /**
     * Retrieves a list of valid authorizations
     * by document number.
     *
     * @param docNumber the document number of the authorized person.
     * @return a list of valid {@link AuthDTO} authorizations.
     */
    List<AuthDTO> getValidAuthsByDocNumber(Long docNumber);

    /**
     * Update authorization list with new authorized ranges.
     * @param existingAuth the existing {@link AuthDTO} authorization.
     * @param visitorDTO the {@link VisitorDTO} of the visitor.
     * @param visitorAuthRequest new authorization details.
     * @return the updated {@link AuthDTO} authorization.
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
    /**
     * Deletes the authorization by ID.
     * @param authId    the ID of the authorization to delete
     * @return the deleted {@link AuthDTO}
     */
    AuthDTO deleteAuthorization(Long authId);
    /**
     * Deletes the authorizations by document number.
     * @param docNumber document number of the authorized person.
     * @param documentType document type of authorized person.
     * @return the deleted {@link AuthDTO}
     */
    List<AuthDTO> deleteAllAuthorizationsByDocNumber(Long docNumber , DocumentType documentType);

    /**
     * Activates the authorization by ID.
     * @param authId    the ID of the authorization to activate
     * @return the activated {@link AuthDTO}
     */
    AuthDTO activateAuthorization(Long authId);
    /**
     * Update authorization list with new authorized ranges.
     * @param visitorAuthRequest new authorization details.
     * @return the updated {@link AuthDTO} authorization.
     */
    AuthDTO updateAuthorizationByAuthid(VisitorAuthRequest visitorAuthRequest);
}
