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

import java.util.Optional;
import java.util.stream.Collectors;

/**
 *  Service implementation for handling operations
 *  related to Authorized entities.
 */
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
        VisitorDTO visitorDTO = visitorService.saveOrUpdateVisitor(visitorAuthRequest.getVisitorRequest());

        Optional<AuthDTO> existingAuthOpt = findExistingAuthorization(visitorAuthRequest);

        // Si existe una autorización con ese visitorType, actualiza la existente
        if (existingAuthOpt.isPresent()) {
            return updateAuthorization(existingAuthOpt.get(), visitorDTO, visitorAuthRequest);
        }
        // Si no existe, crea una nueva autorización
        return createNewAuthorization(visitorDTO, visitorAuthRequest);
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

    /**
     *  Retrieves an authorization if exists
     *  with the same visitorType.
     * @param visitorAuthRequest request
     * @return optional authorization
     */
    private Optional<AuthDTO> findExistingAuthorization(VisitorAuthRequest visitorAuthRequest) {
        // Buscar autorizaciones por documento y filtrar por visitorType
        List<AuthDTO> auths = getAuthsByDocNumber(visitorAuthRequest.getVisitorRequest().getDocNumber());
        return auths.stream()
                .filter(auth -> auth.getVisitorType() == visitorAuthRequest.getVisitorType())
                .findFirst();
    }

    /**
     *  Create a new authorization
     * @param visitorDTO visitor
     * @param visitorAuthRequest request
     * @return new authorization
     */
    private AuthDTO createNewAuthorization(VisitorDTO visitorDTO, VisitorAuthRequest visitorAuthRequest) {

        AuthEntity authEntity = new AuthEntity();
        authEntity.setVisitor(modelMapper.map(visitorDTO, VisitorEntity.class));
        authEntity.setVisitorType(visitorAuthRequest.getVisitorType());
        authEntity.setActive(true);
        authEntity = authRepository.save(authEntity);

        List<AuthorizedRanges> authorizedRangesList =
                registerAuthRanges(visitorAuthRequest.getAuthRangeRequest(), authEntity, visitorDTO);

        return buildAuthDTO(authEntity, authorizedRangesList);
    }

    /**
     * update authorization list with new authorized ranges,
     * @param existingAuth existing authorization
     * @param visitorDTO visitor
     * @param visitorAuthRequest request
     * @return updated authorization
     */
    private AuthDTO updateAuthorization(AuthDTO existingAuth, VisitorDTO visitorDTO, VisitorAuthRequest visitorAuthRequest) {
        List<AuthorizedRanges> newAuthorizedRanges =
                registerAuthRanges(visitorAuthRequest.getAuthRangeRequest(),
                        modelMapper.map(existingAuth, AuthEntity.class), visitorDTO);

        existingAuth.getAuthRanges().addAll(newAuthorizedRanges.stream()
                .map(auth -> modelMapper.map(auth, AuthRangeDTO.class))
                .collect(Collectors.toList()));

        return existingAuth;  // Devuelve la autorización actualizada
    }

    /**
     * register authorized ranges.
     * @param authRangeRequests list of authorized ranges
     * @param authEntity auth
     * @param visitorDTO visitor
     * @return list of authorized ranges
     */
    private List<AuthorizedRanges> registerAuthRanges(List<AuthRangeRequest> authRangeRequests,
                                                      AuthEntity authEntity, VisitorDTO visitorDTO) {
        List<AuthorizedRanges> authorizedRangesList = new ArrayList<>();

        for (AuthRangeRequest authRangeRequest : authRangeRequests) {
            RegisterAuthorizationRangesDTO registerAuthorizationRangesDTO =
                    modelMapper.map(authRangeRequest, RegisterAuthorizationRangesDTO.class);

            registerAuthorizationRangesDTO.setAuthEntityId(authEntity.getAuthId());
            registerAuthorizationRangesDTO.setVisitorId(visitorDTO.getVisitorId());

            // Registro cada rango de autorización
            AuthorizedRanges authorizedRanges =
                    authorizedRangesService.registerAuthorizedRange(registerAuthorizationRangesDTO);
            authorizedRangesList.add(authorizedRanges);
        }

        return authorizedRangesList;
    }


    /**
     * Build AuthDTO to show in the response
     * @param authEntity auth entity
     * @param authorizedRangesList list of authorized ranges
     * @return AuthDTO
     */
    private AuthDTO buildAuthDTO(AuthEntity authEntity, List<AuthorizedRanges> authorizedRangesList) {
        AuthDTO authDTO = modelMapper.map(authEntity, AuthDTO.class);
        authDTO.setAuthRanges(authorizedRangesList.stream()
                .map(auth -> modelMapper.map(auth, AuthRangeDTO.class))
                .collect(Collectors.toList()));
        return authDTO;
    }

}
