package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.RegisterAuthorizationRangesDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthorizedRangesEntity;
import ar.edu.utn.frc.tup.lc.iv.models.AuthorizedRanges;
import ar.edu.utn.frc.tup.lc.iv.repositories.AuthorizedRangesRepository;
import ar.edu.utn.frc.tup.lc.iv.services.IAuthorizedRangesService;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Service class for managing authorized ranges.
 * This class provides methods to handle operations related to
 * authorized ranges, including creating, updating, and retrieving
 * authorized ranges.
 */
@NoArgsConstructor
@Service
public class AuthorizedRangesService implements IAuthorizedRangesService {
    /**
     * ModelMapper instance for converting
     * between entity objects and DTOs.
     * This mapper simplifies the process of
     * mapping properties from one object to another.
     */
    @Autowired
    private ModelMapper modelMapper;
    /**
     * Repository for accessing authorized ranges data.
     * This repository provides methods for CRUD operations
     * on authorized ranges in the database.
     */
    @Autowired
    private AuthorizedRangesRepository authorizedRangesRepository;

    /**
     * Registers a new authorized range.
     *
     * @param authorizedRangeDTO the data transfer object containing the
     *                           details of the authorized range to register.
     * @return AuthorizedRanges
     */
    @Override
    public AuthorizedRanges registerAuthorizedRange(RegisterAuthorizationRangesDTO authorizedRangeDTO) {
        if (authorizedRangeDTO == null) {
            throw new IllegalArgumentException("AuthorizedRangeDTO must not be null");
        }

        AuthorizedRangesEntity authorizedRangeEntity = modelMapper.map(authorizedRangeDTO, AuthorizedRangesEntity.class);
        authorizedRangeEntity.setActive(true);
        if (authorizedRangeDTO.getDayOfWeeks() != null && !authorizedRangeDTO.getDayOfWeeks().isEmpty()) {

            String daysString = authorizedRangeDTO.getDayOfWeeks().stream()
                    .map(DayOfWeek::name)
                    .collect(Collectors.joining("-"));

            authorizedRangeEntity.setDays(daysString);

        } else {
            authorizedRangeEntity.setDays(null);
        }

        AuthorizedRangesEntity auhorizedRange = authorizedRangesRepository.save(authorizedRangeEntity);

        return new AuthorizedRanges(auhorizedRange);
    }

    /**
     * Checks if a person with the given document number
     * has a valid invitation.
     * @param documentNumber The person's
     * identification number.
     * @return {@code true} if a
     * valid invitation exists, {@code false} otherwise.
     */
    @Override
    public Boolean hasInvitation(Long documentNumber) {
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        String day = dayOfWeek.toString().toUpperCase(Locale.ROOT);
        return authorizedRangesRepository.hasInvitation(localDate, localTime, documentNumber,
                day);
    }
}
