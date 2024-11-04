package ar.edu.utn.frc.tup.lc.iv.services.imp;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.clients.UserDetailDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserRestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthRange;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.specification.auth.AuthSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthService;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for handling operations
 * related to Authorized entities.
 */
@Service
public class AuthService implements IAuthService {

    /**
     * repository of authorizations.
     */
    @Autowired
    private AuthRepository authRepository;

    /**
     * service of authorization ranges.
     */
    @Autowired
    private AuthRangeService authRangeService;


    /**
     * repository of authorization ranges.
     */
    @Autowired
    private AuthRangeRepository authRangeRepository;

    /**
     * repository of visitors.
     */
    @Autowired
    private VisitorRepository visitorRepository;

    /**
     * service of visitor.
     */
    @Autowired
    private VisitorService visitorService;

    /**
     * ModelMapper for converting between entities and DTOs.
     */
    @Autowired
    private ModelMapper modelMapper;

    /**
     * service of access.
     */
    @Autowired
    private AccessesService accessesService;
    /**
     userRestclient.
     */
    @Autowired
    private UserRestClient userRestClient;
    /**
     * Get all authorizations.
     * @param filter object with fitlers
     * @param page      The page number for pagination (default is 0).
     * @param size      The number of records per page (default is 10).
     * @return List<AuthDTO>
     */
    @Override
    public List<AuthDTO> getAllAuths(AuthFilter filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<AuthEntity> spec = AuthSpecification.withFilters(filter);

        Page<AuthEntity> authEntities = authRepository.findAll(spec, pageable);

        List<AuthDTO> authDTOs = new ArrayList<>();

        List<Long> uniqueAuthIds = authEntities.getContent().stream()
                .map(AuthEntity::getCreatedUser)
                .distinct()
                .collect(Collectors.toList());

        List<UserDetailDto> userInfo = userRestClient.getUsersByIds(uniqueAuthIds);

        Map<Long, UserDetailDto> userDetailMap = userInfo.stream()
                .collect(Collectors.toMap(UserDetailDto::getId, userDetail -> userDetail));

        for (AuthEntity authEntity : authEntities) {
            VisitorEntity visitorEntity = authEntity.getVisitor();

            if (visitorEntity != null && visitorEntity.isActive()) {

                AuthDTO authDTO = modelMapper.map(authEntity, AuthDTO.class);
                VisitorDTO visitorDTO = modelMapper.map(visitorEntity, VisitorDTO.class);

                List<AuthRangeDTO> authRangeDTOs = authRangeService.getAuthRangesByAuth(authEntity);
                UserDetailDto userDetail = userDetailMap.get(authEntity.getCreatedUser());

                if (userDetail != null) {
                    authDTO.setAuthName(userDetail.getUserName());
                    authDTO.setAuthLastName(userDetail.getLastName());
                }
                authDTO.setAuthorizerId(authEntity.getCreatedUser());
                authDTO.setVisitor(visitorDTO);
                authDTO.setAuthRanges(authRangeDTOs);
                authDTOs.add(authDTO);
            }
        }
        return authDTOs;
    }


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
            List<AuthRangeDTO> authRangeDTOs = authRangeService.getAuthRangesByAuth(authEntity);
            authDTO.setAuthRanges(authRangeDTOs);
            authDTOs.add(authDTO);
        }

        return authDTOs;

    }

    /**
     * Retrieves a list of individual authorizations.
     *
     * @param visitorType document number.
     * @return list of authorized persons.
     */
    @Override
    public List<AuthDTO> getAuthsByType(VisitorType visitorType) {
        List<AuthEntity> authEntities = authRepository.findByVisitorType(visitorType);
        List<AuthDTO> authDTOs = new ArrayList<>();

        for (AuthEntity authEntity : authEntities) {
            AuthDTO authDTO = modelMapper.map(authEntity, AuthDTO.class);
            List<AuthRangeDTO> authRangeDTOs = authRangeService.getAuthRangesByAuth(authEntity);
            authDTO.setAuthRanges(authRangeDTOs);
            authDTOs.add(authDTO);
        }

        return authDTOs;
    }

    /**
     * Retrieves a list of individual authorizations.
     *
     * @param visitorType document number.
     * @param externalID  document number.
     * @return list of authorized persons.
     */
    @Override
    public List<AuthDTO> getAuthsByTypeAndExternalId(VisitorType visitorType, Long externalID, Long plotId) {

        return authRepository.findByVisitorTypeAndExternalIDAndPlotId(visitorType, externalID, plotId).stream()
                .map(authEntity -> modelMapper.map(authEntity, AuthDTO.class)).collect(Collectors.toList());
    }


    /**
     * Retrieves a list of valid authorizations
     * by document number.
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
            if (authDTO.isActive()) {
                List<AuthRangeDTO> validAuthRanges = authRangeService.getValidAuthRanges(authDTO.getAuthRanges(), currentDate,
                        currentTime);
                if (!validAuthRanges.isEmpty()) {
                    authDTO.setAuthRanges(validAuthRanges);
                    validAuths.add(authDTO);
                }
            }
        }

        return validAuths;
    }

    /**
     * update authorization list with new authorized ranges.
     *
     * @param existingAuth       existing authorization
     * @param visitorDTO         visitor
     * @param visitorAuthRequest request
     * @return updated authorization.
     */
    @Override
    public AuthDTO updateAuthorization(AuthDTO existingAuth, VisitorDTO visitorDTO, VisitorAuthRequest visitorAuthRequest) {
        List<AuthRange> newAuthorizedRanges = authRangeService.registerAuthRanges(visitorAuthRequest.getAuthRangeRequest(),
                modelMapper.map(existingAuth, AuthEntity.class), visitorDTO);

        for (AuthRange range : newAuthorizedRanges) {
            AuthRangeDTO authRangeDTO = new AuthRangeDTO();

            authRangeDTO.setAuthRangeId(range.getAuthRangeId());
            authRangeDTO.setActive(range.isActive());
            authRangeDTO.setDaysOfWeek(range.getDaysOfWeek());
            authRangeDTO.setDateFrom(range.getDateFrom());
            authRangeDTO.setDateTo(range.getDateTo());
            authRangeDTO.setHourFrom(range.getHourFrom());
            authRangeDTO.setComment(range.getComment());
            authRangeDTO.setHourTo(range.getHourTo());

            existingAuth.getAuthRanges().add(authRangeDTO);
        }

        return existingAuth; // Devuelve la autorización actualizada
    }
    /**
     * update authorization authorized ranges.
     * @param visitorAuthRequest request
     * @return updated authorization.
     */
    @Override
    public AuthDTO updateAuthorizationByAuthid(VisitorAuthRequest visitorAuthRequest) {

        AuthEntity authEntity = authRepository.findByAuthId(visitorAuthRequest.getAuthId());
        authEntity.setVisitorType(visitorAuthRequest.getVisitorType());
        authEntity.setActive(visitorAuthRequest.isActive());
        authEntity.setPlotId(visitorAuthRequest.getPlotId());
        authEntity.setExternalID(visitorAuthRequest.getExternalID());
        authEntity = authRepository.save(authEntity);

        AuthDTO authDTO = new AuthDTO();
        authDTO.setPlotId(authEntity.getPlotId());
        authDTO.setAuthId(authEntity.getAuthId());
        authDTO.setVisitorType(authEntity.getVisitorType());
        authDTO.setVisitor(modelMapper.map(authEntity.getVisitor(), VisitorDTO.class));
        authDTO.setActive(authEntity.isActive());

        AuthRangeDTO[] rangelist = new AuthRangeDTO[0];
        /*
        if (visitorAuthRequest.getVisitorType() == VisitorType.PROVIDER || visitorAuthRequest.getVisitorType() == VisitorType.WORKER) {
            authDTO.setAuthRanges(authRangeService.getAuthRangesByAuthExternalID(visitorAuthRequest.getExternalID()));
        } else {
        */
            rangelist = authRangeService.updateAuthRangeByAuthId(authEntity.getAuthId(),
                        visitorAuthRequest.getAuthRangeRequest());

            authDTO.setAuthRanges(Arrays.stream(rangelist)
                    .filter(Objects::nonNull)
                    .map(auth -> modelMapper.map(auth, AuthRangeDTO.class))
                    .collect(Collectors.toList()));
        /*}*/

        return authDTO;
    }
    /**
     * Create a new authorization.
     *
     * @param visitorAuthRequest request.
     * @return new authorization.
     */
    @Override
    @Transactional
    public AuthDTO createAuthorization(VisitorAuthRequest visitorAuthRequest) {

        VisitorDTO visitorDTO;

        // verifica si ya existe el visitante en la base de datos
        VisitorDTO visitorDTOAlreadyExist =
                visitorService.getVisitorByDocNumber(visitorAuthRequest.getVisitorRequest().getDocNumber());


        if (visitorDTOAlreadyExist != null) {
            visitorDTO = visitorDTOAlreadyExist;
        } else {
            visitorDTO = visitorService.saveOrUpdateVisitor(visitorAuthRequest.getVisitorRequest(), null);
        }

        Optional<AuthDTO> existingAuthOpt = findExistingAuthorization(visitorAuthRequest);

        // Si existe una autorización con ese visitorType, actualiza la existente
        if (existingAuthOpt.isPresent()) {
            return updateAuthorization(existingAuthOpt.get(), visitorDTO, visitorAuthRequest);
        }
        // Si no existe, crea una nueva autorización
        return createNewAuthorization(visitorDTO, visitorAuthRequest);
    }

    /**
     * Create a new authorization.
     *
     * @param visitorDTO         visitor.
     * @param visitorAuthRequest request.
     * @return new authorization
     */
    protected AuthDTO createNewAuthorization(VisitorDTO visitorDTO, VisitorAuthRequest visitorAuthRequest) {

        AuthEntity authEntity = new AuthEntity();
        authEntity.setVisitor(modelMapper.map(visitorDTO, VisitorEntity.class));
        authEntity.setVisitorType(visitorAuthRequest.getVisitorType());
        authEntity.setActive(true);
        authEntity.setPlotId(visitorAuthRequest.getPlotId());
        authEntity.setExternalID(visitorAuthRequest.getExternalID());
        authEntity.setCreatedDate(LocalDateTime.now());
        authEntity = authRepository.save(authEntity);

        AuthDTO authDTO = new AuthDTO();
        authDTO.setAuthId(authEntity.getAuthId());
        authDTO.setVisitorType(authEntity.getVisitorType());
        authDTO.setVisitor(modelMapper.map(authEntity.getVisitor(), VisitorDTO.class));
        authDTO.setActive(authEntity.isActive());
        authDTO.setPlotId(authEntity.getPlotId());
        authDTO.setAuthorizerId(authEntity.getCreatedUser());
        authDTO.setExternalID(authEntity.getExternalID());


        if (visitorAuthRequest.getVisitorType() == VisitorType.PROVIDER) {
            // Verifica si el proveedor ya tiene autorizaciones en el plot
            List<AuthRangeDTO> authRangeDTOs = authRangeService.getAuthRangesByAuthExternalIdAndPlot(authEntity);

            List<AuthRangeRequestDTO> authRangeRequestDTOs = authRangeDTOs.stream()
                    .map(authRangeDTO -> modelMapper.map(authRangeDTO, AuthRangeRequestDTO.class)).collect(Collectors.toList());

            List<AuthRange> authorizedRangesList =
                    authRangeService.registerAuthRanges(authRangeRequestDTOs, authEntity, visitorDTO);
            authDTO.setAuthRanges(authorizedRangesList.stream()
                    .filter(Objects::nonNull)
                    .map(auth -> modelMapper.map(auth, AuthRangeDTO.class))
                    .collect(Collectors.toList()));

        } else {
            List<AuthRange> authorizedRangesList = authRangeService.registerAuthRanges(visitorAuthRequest.getAuthRangeRequest(),
                    authEntity, visitorDTO);
            authDTO.setAuthRanges(authorizedRangesList.stream()
                    .filter(Objects::nonNull)
                    .map(auth -> modelMapper.map(auth, AuthRangeDTO.class))
                    .collect(Collectors.toList()));
        }

        return authDTO;
    }


    /**
     * Authorize visitor.
     * @param accessDTO accessDto.
     * @param guardID guardId.
     * @return AccessDto
     */
    @Override
    public AccessDTO authorizeVisitor(AccessDTO accessDTO, Long guardID) {
        List<AuthDTO> authDTOs = getValidAuthsByDocNumber(accessDTO.getDocNumber());

        if (authDTOs.isEmpty()) {
            throw new EntityNotFoundException("No existen autorizaciones validas para el documento " + accessDTO.getDocNumber());
        }

        AuthEntity authEntity = authRepository.findById(authDTOs.get(0).getAuthId()).get();

        AccessEntity accessEntity = AccessEntity.builder()
                .createdUser(guardID)
                .lastUpdatedUser(guardID)
                .auth(authEntity)
                .action(accessDTO.getAction())
                .actionDate(LocalDateTime.now())
                .vehicleType(accessDTO.getVehicleType())
                .vehicleReg(accessDTO.getVehicleReg())
                .vehicleDescription(accessDTO.getVehicleDescription())
                .plotId(authEntity.getPlotId())
                .supplierEmployeeId(authDTOs.get(0).getExternalID())
                .comments(accessDTO.getComments())
                .build();

        return accessesService.registerAccess(accessEntity);
    }

    /**
     * Checks if a person is authorized.
     * @param documentNumber The person's
     *                       identification number.
     * @return {@code true} if a
     * valid invitation exists, {@code false} otherwise.
     */
    @Override
    public Boolean isAuthorized(Long documentNumber) {
        return !getValidAuthsByDocNumber(documentNumber).isEmpty();
    }

    /**
     * Retrieves an authorization if exists
     * with the same visitorType.
     * @param visitorAuthRequest request
     * @return optional authorization
     */
    public Optional<AuthDTO> findExistingAuthorization(VisitorAuthRequest visitorAuthRequest) {
        // Busca autorizaciones por documento y filtra por visitorType y plotId
        List<AuthDTO> auths = getAuthsByDocNumber(visitorAuthRequest.getVisitorRequest().getDocNumber());

        return auths.stream()
                .filter(auth -> auth.getVisitorType() == visitorAuthRequest.getVisitorType()
                        && Objects.equals(auth.getPlotId(), visitorAuthRequest.getPlotId()))
                .findFirst();
    }
    /**
     * Deletes the authorization.
     *
     * @param authId the ID of the authorization to delete
     * @return ResponseEntity containing the deleted {@link AuthDTO}
     */
    @Override
    public AuthDTO deleteAuthorization(Long authId) {
        AuthEntity authEntity = authRepository.findByAuthId(authId);
        if (authEntity != null) {
            authEntity.setActive(false);
            authRepository.save(authEntity);
        }
        return null;
    }
    /**
     * Activates the authorization.
     *
     * @param authId the ID of the authorization to activate
     * @return ResponseEntity containing the activated {@link AuthDTO}
     */
    @Override
    public AuthDTO  activateAuthorization(Long authId) {
        AuthEntity authEntity = authRepository.findByAuthId(authId);
        if (authEntity != null) {
            authEntity.setActive(true);
            authRepository.save(authEntity);
        }
        return null;
    }

}
