package ar.edu.utn.frc.tup.lc.iv.services;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;

/**
 * This interface defines the contract for a service
 * that manages authorized persons.
 */
public interface IAuthService {

    /**
     * Retrieves a list of individual authorizations
     * by document number.
     * @param docNumber document number.
     * @return list of authorized persons.
     */
    List<AuthDTO> getAuthsByDocNumber(Long docNumber);

    /**
     * Authorize visitor with authorized ranges.
     * @param visitorAuthRequest request.
     * @return authorization created.
     */
    AuthDTO authorizeVisitor(VisitorAuthRequest visitorAuthRequest);
}
