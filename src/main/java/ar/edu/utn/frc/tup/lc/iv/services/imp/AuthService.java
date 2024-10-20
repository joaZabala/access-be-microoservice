package ar.edu.utn.frc.tup.lc.iv.services.imp;

import java.time.LocalDateTime;
import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthRange;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
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
     * Get all authorizations.
     *
     * @return List<AuthDTO>
     */
    @Override
    public List<AuthDTO> getAllAuths() {

        List<AuthEntity> authEntities = authRepository.findAll()
                .stream().filter(AuthEntity::isActive).toList();

        List<AuthDTO> authDTOs = new ArrayList<>();

        for (AuthEntity authEntity : authEntities) {
            VisitorEntity visitorEntity = authEntity.getVisitor();

            if (visitorEntity != null && visitorEntity.isActive()) {

                AuthDTO authDTO = modelMapper.map(authEntity, AuthDTO.class);
                VisitorDTO visitorDTO = modelMapper.map(visitorEntity, VisitorDTO.class);

                List<AuthRangeEntity> authRangeEntitiesList = authRangeRepository.findByAuthId(authEntity)
                        .stream().filter(AuthRangeEntity::isActive).toList();

                List<AuthRangeDTO> authRangeDTOs = authRangeEntitiesList.stream()
                        .map(authRangeEntity -> modelMapper.map(authRangeEntity, AuthRangeDTO.class))
                        .collect(Collectors.toList());

                authDTO.setVisitor(visitorDTO);
                authDTO.setAuthRanges(authRangeDTOs);
                authDTOs.add(authDTO);
            }
        }
        return authDTOs;
    }

    //todo cambiar
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

    @Override
    public List<AuthDTO> getAuthsByType(VisitorType visitorType) {
        return null;
    }

    @Override
    public List<AuthDTO> getAuthsByTypeAndExternalId(VisitorType visitorType, Long externalID) {
        return null;
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
            if (authDTO.isActive()) {
                List<AuthRangeDTO> validAuthRanges = getValidAuthRanges(authDTO.getAuthRanges(), currentDate,
                        currentTime);
                if (!validAuthRanges.isEmpty()) {
                    authDTO.setAuthRanges(validAuthRanges);
                    validAuths.add(authDTO);
                }
            }
        }

        return validAuths;
    }

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
            authRangeDTO.setHourTo(range.getHourTo());
            authRangeDTO.setPlotId(range.getPlotId());

            existingAuth.getAuthRanges().add(authRangeDTO);
        }

        return existingAuth; // Devuelve la autorización actualizada
    }


    @Override
    @Transactional
    public AuthDTO createAuthorization(VisitorAuthRequest visitorAuthRequest, Long creatorID) {
        visitorAuthRequest.getVisitorRequest().setActive(true);
        VisitorDTO visitorDTO;

        // verifica si ya existe el visitante en la base de datos
        VisitorDTO visitorDTOAlreadyExist = visitorService
                .getVisitorByDocNumber(visitorAuthRequest.getVisitorRequest().getDocNumber());

        if (visitorDTOAlreadyExist != null) {
            visitorDTO = visitorDTOAlreadyExist;
        } else {
            visitorDTO = visitorService.saveOrUpdateVisitor(visitorAuthRequest.getVisitorRequest(), null);
        }

        return createNewAuthorization(visitorDTO, visitorAuthRequest, creatorID);
    }

    /**
     * Create a new authorization.
     *
     * @param visitorDTO         visitor
     * @param visitorAuthRequest request
     * @return new authorization
     */
    protected AuthDTO createNewAuthorization(VisitorDTO visitorDTO, VisitorAuthRequest visitorAuthRequest, Long creatorID) {
        AuthEntity authEntity = new AuthEntity(creatorID, creatorID);
        authEntity.setVisitor(modelMapper.map(visitorDTO, VisitorEntity.class));
        authEntity.setVisitorType(visitorAuthRequest.getVisitorType());
        authEntity.setActive(true);
        authEntity.setExternalID(visitorAuthRequest.getExternalID());
        authEntity = authRepository.save(authEntity);

        AuthDTO authDTO = new AuthDTO();
        authDTO.setAuthId(authEntity.getAuthId());
        authDTO.setVisitorType(authEntity.getVisitorType());
        authDTO.setVisitor(modelMapper.map(authEntity.getVisitor(), VisitorDTO.class));
        authDTO.setActive(authEntity.isActive());


        if (visitorAuthRequest.getVisitorType() == VisitorType.PROVIDER){
            authDTO.setAuthRanges(authRangeService.getAuthRangesByAuthExternalID(visitorAuthRequest.getExternalID()));
        } else {
            List<AuthRange>  authorizedRangesList = authRangeService.registerAuthRanges(visitorAuthRequest.getAuthRangeRequest(),
                    authEntity, visitorDTO);
            authDTO.setAuthRanges(authorizedRangesList.stream()
                    .filter(Objects::nonNull)
                    .map(auth -> modelMapper.map(auth, AuthRangeDTO.class))
                    .collect(Collectors.toList()));
        }

        return authDTO;
    }

    @Override
    public AccessDTO authorizeVisitor(AccessDTO accessDTO, Long guardID) {
        List<AuthDTO> authDTOs = getValidAuthsByDocNumber(accessDTO.getDocNumber());

        if (authDTOs.size()<1){
            return null;
        }

        AuthEntity authEntity = authRepository.getReferenceById(authDTOs.get(0).getAuthId());

        AccessEntity accessEntity = new AccessEntity(
                guardID, guardID, authEntity, accessDTO.getAction(), LocalDateTime.now(), accessDTO.getVehicleType(),
                accessDTO.getVehicleReg(),accessDTO.getVehicleDescription(),authDTOs.get(0).getAuthRanges().get(0).getPlotId(),
                authDTOs.get(0).getExternalID(),accessDTO.getComments());

        return accessesService.registerAccess(accessEntity);
    }

    @Override
    public Boolean isAuthorized(Long documentNumber) {
        return !getValidAuthsByDocNumber(documentNumber).isEmpty();
    }

    /**
     * Retrieves a list of valid authorization ranges.
     *
     * @param authRanges  list of authorization ranges.
     * @param currentDate current date.
     * @param currentTime current time.
     * @return list of valid authorization ranges.
     */
    private List<AuthRangeDTO> getValidAuthRanges(List<AuthRangeDTO> authRanges, LocalDate currentDate,
            LocalTime currentTime) {
        List<AuthRangeDTO> validAuthRanges = new ArrayList<>();

        for (AuthRangeDTO authRangeDTO : authRanges) {
            if (isValidAuthRange(authRangeDTO, currentDate, currentTime)) {
                validAuthRanges.add(authRangeDTO);
            }
        }

        return validAuthRanges;
    }

    /**
     * Checks if the given authorization range is valid.
     *
     * @param authRangeDTO the authorization range to check.
     * @param currentDate  current date.
     * @param currentTime  current time.
     * @return true if the authorization range is valid; false otherwise.
     */
    private boolean isValidAuthRange(AuthRangeDTO authRangeDTO, LocalDate currentDate, LocalTime currentTime) {
        return authRangeDTO.isActive()
                && (authRangeDTO.getDateFrom() == null || !currentDate.isBefore(authRangeDTO.getDateFrom()))
                && (authRangeDTO.getDateTo() == null || !currentDate.isAfter(authRangeDTO.getDateTo()))
                && (authRangeDTO.getHourFrom() == null || !currentTime.isBefore(authRangeDTO.getHourFrom()))
                && (authRangeDTO.getHourTo() == null || !currentTime.isAfter(authRangeDTO.getHourTo()));
    }

    /**
     * Retrieves an authorization if exists
     * with the same visitorType.
     *
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


}
