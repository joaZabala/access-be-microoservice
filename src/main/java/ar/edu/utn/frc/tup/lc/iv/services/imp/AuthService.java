package ar.edu.utn.frc.tup.lc.iv.services.imp;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
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
    public AuthDTO authorizeVisitor(VisitorAuthRequest visitorAuthRequest) {
        return null;
    }

    /**
     * Authorize visitor with authorized ranges.
     *
     * @param visitorAuthRequest request.
     * @return authorization created.
     *

    @Override
    @Transactional
    public AuthDTO authorizeVisitor(VisitorAuthRequest visitorAuthRequest) {

        visitorAuthRequest.getVisitorRequest().setActive(true);

        VisitorDTO visitorDTO;

        // verifica si ya existe el visitante en la base de datos
        VisitorDTO visitorDTOAlreadyExist = visitorService
                .getVisitorByDocNumber(visitorAuthRequest.getVisitorRequest().getDocNumber());

        if (visitorDTOAlreadyExist != null) {
            visitorDTO = visitorDTOAlreadyExist;
        } else {
            visitorDTO = visitorService.saveOrUpdateVisitor(visitorAuthRequest.getVisitorRequest());
        }

        Optional<AuthDTO> existingAuthOpt = findExistingAuthorization(visitorAuthRequest);

        // Si existe una autorización con ese visitorType, actualiza la existente
        if (existingAuthOpt.isPresent()) {
            return updateAuthorization(existingAuthOpt.get(), visitorDTO, visitorAuthRequest);
        }
        // Si no existe, crea una nueva autorización
        return createNewAuthorization(visitorDTO, visitorAuthRequest);
    }
*/
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
    public AuthDTO createAuthorization(VisitorAuthRequest visitorAuthRequest, Long creatorID) {
        return null;
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

    /**
     * Create a new authorization.
     *
     * @param visitorDTO         visitor
     * @param visitorAuthRequest request
     * @return new authorization

    protected AuthDTO createNewAuthorization(VisitorDTO visitorDTO, VisitorAuthRequest visitorAuthRequest) {

        AuthEntity authEntity = new AuthEntity();
        authEntity.setVisitor(modelMapper.map(visitorDTO, VisitorEntity.class));
        authEntity.setVisitorType(visitorAuthRequest.getVisitorType());
        authEntity.setActive(true);
        authEntity = authRepository.save(authEntity);

        List<AuthorizedRanges> authorizedRangesList = registerAuthRanges(visitorAuthRequest.getAuthRangeRequest(),
                authEntity, visitorDTO);

        AuthDTO authDTO = new AuthDTO();
        authDTO.setAuthId(authEntity.getAuthId());
        authDTO.setVisitorType(authEntity.getVisitorType());
        authDTO.setVisitor(modelMapper.map(authEntity.getVisitor(), VisitorDTO.class));
        authDTO.setActive(authEntity.isActive());

        authDTO.setAuthRanges(authorizedRangesList.stream()
                .filter(Objects::nonNull)
                .map(auth -> modelMapper.map(auth, AuthRangeDTO.class))
                .collect(Collectors.toList()));

        return authDTO;
    }
*/
    /**
     * update authorization list with new authorized ranges.
     *
     * @param existingAuth       existing authorization
     * @param visitorDTO         visitor
     * @param visitorAuthRequest request
     * @return updated authorization

    private AuthDTO updateAuthorization(AuthDTO existingAuth, VisitorDTO visitorDTO,
            VisitorAuthRequest visitorAuthRequest) {

        // Registra los nuevos rangos de autorización
        List<AuthorizedRanges> newAuthorizedRanges = registerAuthRanges(visitorAuthRequest.getAuthRangeRequest(),
                modelMapper.map(existingAuth, AuthEntity.class), visitorDTO);

        for (AuthorizedRanges range : newAuthorizedRanges) {
            AuthRangeDTO authRangeDTO = new AuthRangeDTO();

            authRangeDTO.setAuthRangeId(range.getAuthRangeId());
            authRangeDTO.setActive(range.isActive());
            authRangeDTO.setDays(range.getDays());
            authRangeDTO.setDateFrom(range.getDateFrom());
            authRangeDTO.setDateTo(range.getDateTo());
            authRangeDTO.setHourFrom(range.getHourFrom());
            authRangeDTO.setHourTo(range.getHourTo());
            authRangeDTO.setPlotId(range.getPlotId());

            existingAuth.getAuthRanges().add(authRangeDTO);
        }

        return existingAuth; // Devuelve la autorización actualizada
    }*/

    /**
     * register authorized ranges.
     *
     * @param authRangeRequests list of authorized ranges
     * @param authEntity        auth
     * @param visitorDTO        visitor
     * @return list of authorized ranges

    protected List<AuthorizedRanges> registerAuthRanges(List<AuthRangeDto> authRangeRequests,
            AuthEntity authEntity, VisitorDTO visitorDTO) {

        List<AuthorizedRanges> authorizedRangesList = new ArrayList<>();

        for (AuthRangeDto authRangeRequest : authRangeRequests) {
            RegisterAuthorizationRangesDTO registerAuthorizationRangesDTO = modelMapper.map(authRangeRequest,
                    RegisterAuthorizationRangesDTO.class);

            registerAuthorizationRangesDTO.setAuthEntityId(authEntity.getAuthId());
            registerAuthorizationRangesDTO.setVisitorId(visitorDTO.getVisitorId());

            // Registro cada rango de autorización
            AuthorizedRanges authorizedRanges = authorizedRangesService
                    .registerAuthorizedRange(registerAuthorizationRangesDTO);
            authorizedRangesList.add(authorizedRanges);
        }

        return authorizedRangesList;
    }*/
}
