package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.clients.UserDetailDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserRestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.dashboard.DashboardDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AccessesRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.specification.accesses.AccessSpecification;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service implementation for handling
 * operations related to authorized access entities.
 * This service interacts with the repository
 * to manage and retrieve access records.
 */
@Service
public class AccessesService implements IAccessesService {

    /**
     * Repository for managing access entities.
     */
    @Autowired
    private AccessesRepository accessesRepository;

    /**
     * ModelMapper for converting between
     * AccessEntity and AccessDTO.
     */
    @Autowired
    private ModelMapper modelMapper;
    /**
        userRestclient.
     */
    @Autowired
    private UserRestClient userRestClient;
    /**
     * Constant for the 24 Hours.
     */
    private static final int HOUR = 24;
    /**
     * Retrieves all access records from the repository.
     * @return List of AccessDTO representing access records.
     */
    @Override
    public PaginatedResponse<AccessDTO> getAllAccess(AccessesFilter filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("actionDate"));
        LocalDateTime fromDateTime = filter.getFromDate() != null
                ? filter.getFromDate().atStartOfDay()
                : null;

        LocalDateTime toDateTime = filter.getToDate() != null
                ? filter.getToDate().atTime(LocalTime.MAX)
                : null;

        Specification<AccessEntity> spec = AccessSpecification.withFilters(filter, fromDateTime, toDateTime);

        Page<AccessEntity> accesses = accessesRepository.findAll(spec, pageable);

        PaginatedResponse<AccessDTO> response = new PaginatedResponse<>();
        response.setItems(accesses.stream()
                .map(this::mapToAccessDTO).sorted(Comparator.comparing(AccessDTO::getActionDate).reversed())
                .collect(Collectors.toList()));


        response.setTotalElements(accesses.getTotalElements());


        List<Long> uniqueAuthorizerIds = accesses.stream()
                .map(this::mapToAccessDTO)
                .map(AccessDTO::getAuthorizerId)
                .distinct()
                .collect(Collectors.toList());


        List<UserDetailDto> userInfo = userRestClient.getUsersByIds(uniqueAuthorizerIds);

        Map<Long, UserDetailDto> userMap = userInfo.stream()
                .collect(Collectors.toMap(UserDetailDto::getId, user -> user));

        response.getItems().forEach(access -> {
            UserDetailDto user = userMap.get(access.getAuthorizerId());
            if (user != null) {
                access.setAuthName(user.getFirstName());
                access.setAuthLastName(user.getLastName());
            }
        });


        return response;
    }

    /**
     * Retrieves all entries (access actions of type ENTRY).
     *
     * @return List of AccessDTO representing entry records.
     */
    @Override
    public List<AccessDTO> getAllEntries() {
        return accessesRepository.findByAction(ActionTypes.ENTRY).stream()
                .map(this::mapToAccessDTO)
                .sorted(Comparator.comparing(AccessDTO::getActionDate).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all exits (access actions of type EXIT).
     *
     * @return List of AccessDTO representing exit records.
     */
    @Override
    public List<AccessDTO> getAllExits() {
        return accessesRepository.findByAction(ActionTypes.EXIT).stream()
                .map(this::mapToAccessDTO)
                .sorted(Comparator.comparing(AccessDTO::getActionDate).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Retrieves access records filtered by visitor type.
     * @param visitorType The type of the visitor.
     * @return List of AccessDTO for the specified visitor type.
     */
    @Override
    public List<AccessDTO> getAllAccessByType(VisitorType visitorType) {
        return accessesRepository.findByAuthVisitorType(visitorType).stream()
                .map(this::mapToAccessDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves access records filtered by visitor type and external ID.
     * @param visitorType The type of the visitor.
     * @param externalId  External identifier associated with the access.
     * @return List of AccessDTO for the specified visitor type
     * and external ID.
     */
    @Override
    public List<AccessDTO> getAllAccessByTypeAndExternalID(VisitorType visitorType, Long externalId) {
        return accessesRepository.findByAuthVisitorTypeAndAuthExternalID(visitorType, externalId).stream()
                .map(this::mapToAccessDTO)
                .collect(Collectors.toList());
    }

    /**
     * Registers a new access entry in the repository.
     *
     * @param accessEntity The access entity to be registered.
     * @return AccessDTO representing the registered access.
     */
    @Override
    public AccessDTO registerAccess(AccessEntity accessEntity) {
        AccessEntity savedAccess = accessesRepository.save(accessEntity);
        AccessDTO accessDTO = modelMapper.map(savedAccess, AccessDTO.class);
        accessDTO.setName(savedAccess.getAuth().getVisitor().getName());
        accessDTO.setLastName(savedAccess.getAuth().getVisitor().getLastName());
        accessDTO.setDocNumber(savedAccess.getAuth().getVisitor().getDocNumber());
        return accessDTO;
    }
    /**
     * Checks if an action can be performed for a vehicle.
     * @param carPlate the vehicle's plate number.
     * @param action   the action to check.
     * @return true if the action can be performed; false otherwise.
     */
    @Override
    public Boolean canDoAction(String carPlate, ActionTypes action) {
        AccessEntity acc = accessesRepository.findByVehicleReg(carPlate).stream()
                .max(Comparator.comparing(AccessEntity::getActionDate))
                .orElse(null);
        if (acc == null) {
            return true;
        }
        return !acc.getAction().equals(action);
    }

    /**
     * Maps an AccessEntity to an AccessDTO.
     * @param accessEntity AccessEntity to be mapped.
     * @return AccessDTO representing the AccessEntity.
     */
    public AccessDTO mapToAccessDTO(AccessEntity accessEntity) {
        AccessDTO accessDTO = modelMapper.map(accessEntity, AccessDTO.class);

        accessDTO.setAuthorizerId(accessEntity.getAuth().getCreatedUser());
        accessDTO.setDocType(accessEntity.getAuth().getVisitor().getDocumentType());
        accessDTO.setName(accessEntity.getAuth().getVisitor().getName());
        accessDTO.setLastName(accessEntity.getAuth().getVisitor().getLastName());
        accessDTO.setDocNumber(accessEntity.getAuth().getVisitor().getDocNumber());
        accessDTO.setVisitorType(accessEntity.getAuth().getVisitorType());

        return accessDTO;
    }
    /**
     * Retrieves hourly access information within a specified date range.
     * @param from the start date and time (inclusive) of the range
     * @param to   the end date and time (inclusive) of the range
     * @return a list of {@link DashboardDTO} objects representing
     * access counts per hour
     */
    @Override
    public List<DashboardDTO> getHourlyInfo(LocalDateTime from, LocalDateTime to) {
        List<Object[]> results = accessesRepository.findAccessCountsByHourNative(from, to);

        Map<String, Long> hourlyAccessMap = new LinkedHashMap<>();
        for (int hour = 0; hour < HOUR; hour++) {
            String hourString = String.format("%02d:00", hour);
            hourlyAccessMap.put(hourString, 0L);
        }

        for (Object[] row : results) {
            String hour = (String) row[0];
            Long count = ((Number) row[1]).longValue();
            hourlyAccessMap.put(hour, count);
        }
        return hourlyAccessMap.entrySet().stream()
                .map(entry -> new DashboardDTO(entry.getKey(), entry.getValue(), 0L))
                .collect(Collectors.toList());
    }
    /**
     * Retrieves hourly access information within a specified date range.
     * @param from the start date and time (inclusive) of the range
     * @param to   the end date and time (inclusive) of the range
     * @return a list of {@link DashboardDTO} objects representing
     * access counts per day of week
     */
    @Override
    public List<DashboardDTO> getDayOfWeekInfo(LocalDateTime from, LocalDateTime to) {
        List<Object[]> results = accessesRepository.findAccessCountsByDayOfWeekNative(from, to);

        Map<String, Long[]> dayOfWeekAccessMap = new LinkedHashMap<>();
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            String dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase(Locale.ENGLISH);
            dayOfWeekAccessMap.put(dayName, new Long[]{0L, 0L});
        }

        for (Object[] row : results) {
            Integer dayOfWeekValue = ((Number) row[0]).intValue();
            Long entriesCount = ((Number) row[1]).longValue();
            Long exitsCount = ((Number) row[2]).longValue();

            DayOfWeek dayOfWeek = DayOfWeek.of(dayOfWeekValue);
            String dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase(Locale.ENGLISH);

            dayOfWeekAccessMap.put(dayName, new Long[]{entriesCount, exitsCount});
        }

        return dayOfWeekAccessMap.entrySet().stream()
                .map(entry -> new DashboardDTO(entry.getKey(), entry.getValue()[0], entry.getValue()[1])) // entradas y salidas
                .collect(Collectors.toList());
    }
}
