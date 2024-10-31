package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.interceptor.UserHeaderInterceptor;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthRange;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import ar.edu.utn.frc.tup.lc.iv.services.IAuthRangeService;

/**
 * Service for AuthRange.
 */
@Service
public class AuthRangeService implements IAuthRangeService {

    /**
     * Repository for AuthRange.
     */
    @Autowired
    private AuthRangeRepository authRangeRepository;
    /**
     * repository of authorizations.
     */
    @Autowired
    private AuthRepository authRepository;
    /**
     * Mapper for AuthRange.
     */
    @Autowired
    private ModelMapper modelMapper;
    /**
     * Repository for Visitor.
     */
    @Autowired
    private VisitorRepository visitorRepository;

    /**
     * Retrieves all authorized ranges.
     *
     * @return list of authorized ranges
     */
    @Override
    public List<AuthRangeDTO> getAllAuthRanges() {
        return authRangeRepository.findAll().stream()
                .map(authRange -> modelMapper.map(authRange, AuthRangeDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Get auth ranges by auth.
     *
     * @param auth id of auth
     * @return list of auth ranges.
     */
    @Override
    public List<AuthRangeDTO> getAuthRangesByAuth(AuthEntity auth) {
        return authRangeRepository.findByAuthId(auth).stream()
                .map(authRangeEntity -> {
                    AuthRangeDTO authRangeDTO = modelMapper.map(authRangeEntity, AuthRangeDTO.class);
                    authRangeDTO.setDaysOfWeek(convertDaysOfWeek(authRangeEntity.getDaysOfWeek()));
                    return authRangeDTO;
                }).collect(Collectors.toList());
    }

    /**
     * get auth ranges by auth external id and plot.
     *
     * @param authID id of auth
     * @return list of auth ranges.
     */
    @Override
    public List<AuthRangeDTO> getAuthRangesByAuthExternalIdAndPlot(AuthEntity authID) {
        return authRangeRepository.findByAuthIdExternalIDAndAuthIdPlotId(authID.getExternalID(), authID.getPlotId())
                .stream()
                .map(authRangeEntity -> {
                    AuthRangeDTO authRangeDTO = modelMapper.map(authRangeEntity, AuthRangeDTO.class);
                    authRangeDTO.setDaysOfWeek(convertDaysOfWeek(authRangeEntity.getDaysOfWeek()));
                    return authRangeDTO;
                }).collect(Collectors.toList());
    }

    /**
     * Get auth ranges by auth external id.
     *
     * @param externalID id another microservice
     * @return list of auth ranges.
     */
    @Override
    public List<AuthRangeDTO> getAuthRangesByAuthExternalID(Long externalID) {
        return authRangeRepository.findByAuthIdExternalID(externalID).stream()
                .map(authRangeEntity -> {
                    AuthRangeDTO authRangeDTO = modelMapper.map(authRangeEntity, AuthRangeDTO.class);
                    authRangeDTO.setDaysOfWeek(convertDaysOfWeek(authRangeEntity.getDaysOfWeek()));
                    return authRangeDTO;
                }).collect(Collectors.toList());
    }

    /**
     * Register authorization ranges.
     *
     * @param authRangeRequests details of the authorized range.
     * @param authEntity        the authorized entity.
     * @param visitorDTO        the visitor.
     * @return list of authorized ranges.
     */

    @Override
    public List<AuthRange> registerAuthRanges(List<AuthRangeRequestDTO> authRangeRequests,
                                              AuthEntity authEntity, VisitorDTO visitorDTO) {

        List<AuthRange> authorizedRangesList = new ArrayList<>();

        for (AuthRangeRequestDTO authRangeRequest : authRangeRequests) {
            RegisterAuthorizationRangesDTO registerAuthorizationRangesDTO = modelMapper.map(authRangeRequest,
                    RegisterAuthorizationRangesDTO.class);

            registerAuthorizationRangesDTO.setAuthEntityId(authEntity.getAuthId());
            registerAuthorizationRangesDTO.setVisitorId(visitorDTO.getVisitorId());

            // Registro cada rango de autorización
            AuthRange authorizedRanges = registerAuthorizedRange(registerAuthorizationRangesDTO);
            authorizedRangesList.add(authorizedRanges);
        }

        return authorizedRangesList;
    }

    /**
     * Check if the authorization range is valid.
     * @param authRangeDTO the authorization range to check.
     * @param currentDate  current date.
     * @param currentTime  current time.
     * @return true if the authorization range is valid
     * false otherwise.
     */
    @Override
    public boolean isValidAuthRange(AuthRangeDTO authRangeDTO, LocalDate currentDate, LocalTime currentTime) {
        return authRangeDTO.isActive()
                && (authRangeDTO.getDateFrom() == null || !currentDate.isBefore(authRangeDTO.getDateFrom()))
                && (authRangeDTO.getDateTo() == null || !currentDate.isAfter(authRangeDTO.getDateTo()))
                && (authRangeDTO.getHourFrom() == null || !currentTime.isBefore(authRangeDTO.getHourFrom()))
                && (authRangeDTO.getHourTo() == null || !currentTime.isAfter(authRangeDTO.getHourTo()))
                && authRangeDTO.getDaysOfWeek().contains(currentDate.getDayOfWeek());
    }

    /**
     * Get valid authorization ranges.
     *
     * @param authRanges  list of authorization ranges.
     * @param currentDate current date.
     * @param currentTime current time.
     * @return
     */
    @Override
    public List<AuthRangeDTO> getValidAuthRanges(List<AuthRangeDTO> authRanges, LocalDate currentDate,
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
     * register authorized range.
     *
     * @param authorizedRangeDTO the authorized range to register.
     * @return the authorized range.
     */
    private AuthRange registerAuthorizedRange(RegisterAuthorizationRangesDTO authorizedRangeDTO) {
        if (authorizedRangeDTO == null) {
            throw new IllegalArgumentException("AuthorizedRangeDTO must not be null");
        }
        Long writerUserId = UserHeaderInterceptor.getCurrentUserId();

        AuthRangeEntity authRangeEntity = modelMapper.map(authorizedRangeDTO, AuthRangeEntity.class);
        authRangeEntity.setActive(true);

        if (authorizedRangeDTO.getAuthEntityId() != null && authorizedRangeDTO.getAuthEntityId() != 0L) {
            Optional<AuthEntity> authEntity = authRepository.findById(authorizedRangeDTO.getAuthEntityId());
            authEntity.ifPresent(authRangeEntity::setAuthId);

        } else {
            authRangeEntity.setAuthId(null);
        }

        if (authorizedRangeDTO.getDaysOfWeek() != null && !authorizedRangeDTO.getDaysOfWeek().isEmpty()) {

            authRangeEntity.setDaysOfWeek(authorizedRangeDTO.getDaysOfWeek().stream()
                    .map(DayOfWeek::name)
                    .collect(Collectors.joining(",")));

        } else {
            authRangeEntity.setDaysOfWeek(null);
        }
        authRangeEntity.setCreatedUser(writerUserId);
        authRangeEntity.setCreatedDate(LocalDateTime.now());
        AuthRangeEntity authorizedRange = authRangeRepository.save(authRangeEntity);

        return new AuthRange(authorizedRange);
    }

    /**
     * Convert days of week from string to list.
     *
     * @param daysOfWeek days of week.
     * @return list of days of week.
     */
    private List<DayOfWeek> convertDaysOfWeek(String daysOfWeek) {
        if (daysOfWeek == null || daysOfWeek.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(daysOfWeek.split(","))
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toList());
    }
    /**
     * Retrieves authorization ranges for a visitor.
     * @param visitorType the type of visitor.
     * @param docNumber   the document number of the visitor.
     * @param plotId      the ID of the plot.
     * @return a list of {@link AuthRangeDTO} with the authorization ranges.
     * @throws RuntimeException if entity is not found.
     */
    public List<AuthRangeDTO> getAuthRanges(VisitorType visitorType, Long docNumber, Long plotId) {
        VisitorEntity visitor = visitorRepository.findByDocNumber(docNumber);

        AuthEntity auths = authRepository.findByVisitorAndVisitorTypeAndPlotId(visitor, visitorType, plotId);

        List<AuthRangeEntity> authRangeEntities = authRangeRepository.findByAuthId(auths);

        return authRangeEntities.stream().map(object -> modelMapper.map(object, AuthRangeDTO.class))
                .collect(Collectors.toList());

    }
    /**
     * Marks an existing authorization range as inactive based on the provided ID.
     * @param id  the unique identifier of the authorization range to be deleted.
     * @return an {@link AuthRangeDTO} containing the updated
     * authorization range data with the active status set to false.
     * @throws RuntimeException if the specified authorization range ID is not found.
     */
    public AuthRangeDTO deleteAuthRange(Long id) {
        AuthRangeEntity authRangeEntity = authRangeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AuthRange id not found"));
        authRangeEntity.setActive(false);
        authRangeEntity.setLastUpdatedUser(UserHeaderInterceptor.getCurrentUserId());
        authRangeEntity.setLastUpdatedDate(LocalDateTime.now());
        authRangeRepository.save(authRangeEntity);

        return modelMapper.map(authRangeEntity, AuthRangeDTO.class);
    }
    /**
     * Updates an existing authorization range.
     * @param authId  the unique identifier.
     * @param request  containing the new values for range.
     * @return an {@link AuthRangeDTO}  updated  range data.
     * @throws RuntimeException if not found.
     */
    public AuthRangeDTO updateAuthRange(Long authId, AuthRangeRequestDTO request) {
        AuthRangeEntity authRangeEntity = authRangeRepository.findById(authId)
                .orElseThrow(() -> new RuntimeException("AuthRange id not found"));

        authRangeEntity.setDateFrom(request.getDateFrom());
        authRangeEntity.setDateTo(request.getDateTo());
        authRangeEntity.setHourFrom(request.getHourFrom());
        authRangeEntity.setHourTo(request.getHourTo());
        authRangeEntity.setLastUpdatedUser(UserHeaderInterceptor.getCurrentUserId());
        authRangeEntity.setLastUpdatedDate(LocalDateTime.now());
        authRangeEntity.setDaysOfWeek(request.getDaysOfWeek().stream().map(DayOfWeek::name).collect(Collectors.joining(",")));

        authRangeRepository.save(authRangeEntity);

        return modelMapper.map(authRangeEntity, AuthRangeDTO.class);

    }

}
