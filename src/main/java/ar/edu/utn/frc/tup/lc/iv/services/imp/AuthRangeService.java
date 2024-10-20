package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthRange;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthRangeRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @Override
    public List<AuthRangeDTO> getAllAuthRanges() {
        return List.of();
    }

    @Override
    public List<AuthRangeDTO> getAuthRangesByAuth(AuthEntity authID) {
        return List.of();
    }

    @Override
    public List<AuthRangeDTO> getAuthRangesByAuthExternalID(Long externalID) {
        return List.of();
    }

    @Override
    public List<AuthRange> registerAuthRanges(List<AuthRangeRequestDTO> authRangeRequests, AuthEntity authEntity, VisitorDTO visitorDTO) {
        return List.of();
    }

    @Override
    public boolean isValidAuthRange(AuthRangeDTO authRangeDTO, LocalDate currentDate, LocalTime currentTime) {
        return authRangeDTO.isActive()
                && (authRangeDTO.getDateFrom() == null || !currentDate.isBefore(authRangeDTO.getDateFrom()))
                && (authRangeDTO.getDateTo() == null || !currentDate.isAfter(authRangeDTO.getDateTo()))
                && (authRangeDTO.getHourFrom() == null || !currentTime.isBefore(authRangeDTO.getHourFrom()))
                && (authRangeDTO.getHourTo() == null || !currentTime.isAfter(authRangeDTO.getHourTo()))
                && authRangeDTO.getDaysOfWeek().isEmpty() || authRangeDTO.getDaysOfWeek()
                .contains(currentDate.getDayOfWeek());
    }

    @Override
    public List<AuthRangeDTO> getValidAuthRanges(List<AuthRangeDTO> authRanges, LocalDate currentDate, LocalTime currentTime) {
        return List.of();
    }

    private AuthRange registerAuthorizedRange(RegisterAuthorizationRangesDTO authorizedRangeDTO) {
        if (authorizedRangeDTO == null) {
            throw new IllegalArgumentException("AuthorizedRangeDTO must not be null");
        }

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

        AuthRangeEntity authorizedRange = authRangeRepository.save(authRangeEntity);

        return new AuthRange(authorizedRange);
    }


}
