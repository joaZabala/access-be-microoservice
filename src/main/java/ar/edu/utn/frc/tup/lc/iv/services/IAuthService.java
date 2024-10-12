package ar.edu.utn.frc.tup.lc.iv.services;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;

public interface IAuthService {

    List<AuthDTO> getAuthsByDocNumber(Long docNumber);

    AuthDTO authorizeVisitor(VisitorAuthRequest visitorAuthRequest);

    AuthEntity getAuthById(Long authId);
    
}
