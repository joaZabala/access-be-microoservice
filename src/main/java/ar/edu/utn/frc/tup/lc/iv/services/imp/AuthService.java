package ar.edu.utn.frc.tup.lc.iv.services.imp;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.AuthRangeRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.models.AuthorizedRanges;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthService;

import java.util.ArrayList;

import java.util.stream.Collectors;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private AuthRangeRepository authRangeRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private VisitorService visitorService;
    @Autowired
    private AuthorizedRangesService authorizedRangesService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AuthDTO> getAuthsByDocNumber(Long docNumber) {

        VisitorEntity visitorEntity = visitorRepository.findByDocNumber(docNumber);

        List<AuthEntity> authEntities = authRepository.findByVisitor(visitorEntity.getVisitorId());

        List<AuthDTO> authDTOs = new ArrayList<>();

        for (AuthEntity authEntity : authEntities) {

            AuthDTO authDTO = modelMapper.map(authEntity, AuthDTO.class);

            List<AuthRangeEntity> authRangeEntities = authRangeRepository.findByAuthId(authEntity);

            List<AuthRangeDTO> authRangeDTOs = authRangeEntities.stream()
                    .map(authRangeEntity -> modelMapper.map(authRangeEntity, AuthRangeDTO.class))
                    .collect(Collectors.toList());

            authDTO.setAuthRanges(authRangeDTOs);

            authDTOs.add(authDTO);

        }

        return authDTOs;

    }

    @Override
    @Transactional
    public AuthDTO authorizeVisitor(VisitorAuthRequest visitorAuthRequest) {

        visitorAuthRequest.getVisitorRequest().setActive(true);

        //llamo al metodo para crear o modificar visitor
        VisitorDTO visitorDTO = visitorService.saveOrUpdateVisitor(visitorAuthRequest.getVisitorRequest());

        AuthEntity authEntity = new AuthEntity();
        authEntity.setVisitor(modelMapper.map(visitorDTO, VisitorEntity.class));
        authEntity.setVisitorType(visitorAuthRequest.getVisitorType());
        authEntity.setActive(true);
        authEntity = authRepository.save(authEntity);

        List<AuthorizedRanges> authorizedRangesList = new ArrayList<>();
        for (AuthRangeRequest authRangeRequest : visitorAuthRequest.getAuthRangeRequest()) {

            // Mapear la autorización individual
            RegisterAuthorizationRangesDTO registerAuthorizationRangesDTO =
                    modelMapper.map(authRangeRequest, RegisterAuthorizationRangesDTO.class);

            registerAuthorizationRangesDTO.setAuthEntityId(authEntity.getAuthId());
            registerAuthorizationRangesDTO.setVisitorId(visitorDTO.getVisitorId());

            // Registrar la autorización
            AuthorizedRanges authorizedRanges =
                    authorizedRangesService.registerAuthorizedRange(registerAuthorizationRangesDTO);

            // Agregarla a la lista
            authorizedRangesList.add(authorizedRanges);
        }


        AuthDTO authDTO = modelMapper.map(authEntity, AuthDTO.class);
        authDTO.setAuthRanges(authorizedRangesList.stream()
                .map(auth -> modelMapper.map(auth, AuthRangeDTO.class)).collect(Collectors.toList()));

        return authDTO;

    }

    @Override
    public AuthEntity getAuthById(Long authId) {
        return authRepository.findById(authId).orElse(null);
    }


}
