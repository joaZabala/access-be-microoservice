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

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;

import java.util.stream.Collectors;

@Service
public class AuthService implements IAuthService {

    /**
     * repository of authorizations
     */
    @Autowired
    private AuthRepository authRepository;

    /**
     * repository of authorization ranges
     */
    @Autowired
    private AuthRangeRepository authRangeRepository;

    /**
     * repository of visitors
     */
    @Autowired
    private VisitorRepository visitorRepository;

    /**
     * service of visitor
     */
    @Autowired
    private VisitorService visitorService;
    /**
     * authorized ranges service
     */
    @Autowired
    private AuthorizedRangesService authorizedRangesService;

    /**
     * ModelMapper for converting between entities and DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves a list of individual authorizations
     * by document number.
     * 
     * @param docNumber document number.
     * @return list of authorized persons.
     */
    @Override
    public List<AuthDTO> getAuthsByDocNumber(Long docNumber) {

        VisitorEntity visitorEntity = visitorRepository.findByDocNumber(docNumber);

        List<AuthEntity> authEntities = authRepository.findByVisitor(visitorEntity);

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

    /**
     * Authorize visitor with authorized ranges.
     * 
     * @param visitorAuthRequest request.
     * @return authorization created.
     */
    @Override
    @Transactional
    public AuthDTO authorizeVisitor(VisitorAuthRequest visitorAuthRequest) {
        visitorAuthRequest.getVisitorRequest().setActive(true);

        // llamo al metodo para crear o modificar visitor
        VisitorDTO visitorDTO = visitorService.saveOrUpdateVisitor(visitorAuthRequest.getVisitorRequest());

        AuthEntity authEntity = new AuthEntity();
        authEntity.setVisitor(modelMapper.map(visitorDTO, VisitorEntity.class));
        authEntity.setVisitorType(visitorAuthRequest.getVisitorType());
        authEntity.setActive(true);
        authEntity = authRepository.save(authEntity);

        List<AuthorizedRanges> authorizedRangesList = new ArrayList<>();
        for (AuthRangeRequest authRangeRequest : visitorAuthRequest.getAuthRangeRequest()) {

            // Mapear la autorización individual
            RegisterAuthorizationRangesDTO registerAuthorizationRangesDTO = modelMapper.map(authRangeRequest,
                    RegisterAuthorizationRangesDTO.class);

            registerAuthorizationRangesDTO.setAuthEntityId(authEntity.getAuthId());
            registerAuthorizationRangesDTO.setVisitorId(visitorDTO.getVisitorId());

            // Registrar la autorización
            AuthorizedRanges authorizedRanges = authorizedRangesService
                    .registerAuthorizedRange(registerAuthorizationRangesDTO);

            // Agregarla a la lista
            authorizedRangesList.add(authorizedRanges);
        }

        AuthDTO authDTO = modelMapper.map(authEntity, AuthDTO.class);
        authDTO.setAuthRanges(authorizedRangesList.stream()
                .map(auth -> modelMapper.map(auth, AuthRangeDTO.class)).collect(Collectors.toList()));

        return authDTO;

    }

    /**
     * Retrieves a list of valid authorizations
     * by document number.
     * 
     * @param docNumber document number.
     * @return list of valid authorizations.
     */

    @Override
    public List<AuthDTO> getValidAuthsByDocNumber(Long docNumber) {

        List<AuthDTO> dtos = getAuthsByDocNumber(docNumber);
        List<AuthDTO> validAuths = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        for (AuthDTO authDTO : dtos) {
            List<AuthRangeDTO> validAuthRanges = new ArrayList<>();

            if (authDTO.isActive()) {
                for (AuthRangeDTO authRangeDTO : authDTO.getAuthRanges()) {
                    if (authRangeDTO.isActive()
                            && (authRangeDTO.getDateFrom() == null || !currentDate.isBefore(authRangeDTO.getDateFrom()))
                            && (authRangeDTO.getDateTo() == null || !currentDate.isAfter(authRangeDTO.getDateTo()))
                            && (authRangeDTO.getHourFrom() == null || !currentTime.isBefore(authRangeDTO.getHourFrom()))
                            && (authRangeDTO.getHourTo() == null || !currentTime.isAfter(authRangeDTO.getHourTo()))) {

                        validAuthRanges.add(authRangeDTO);
                    }
                }
                
                if (!validAuthRanges.isEmpty()) {
                    authDTO.setAuthRanges(validAuthRanges);
                    validAuths.add(authDTO);
                }
            }
        }

        return validAuths;
    }

}
