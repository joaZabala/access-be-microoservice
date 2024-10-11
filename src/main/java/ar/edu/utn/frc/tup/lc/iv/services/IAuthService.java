package ar.edu.utn.frc.tup.lc.iv.services;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;

public interface IAuthService {

    List<AuthDTO> getAuthsByDocNumber(Long docNumber);
    
}
