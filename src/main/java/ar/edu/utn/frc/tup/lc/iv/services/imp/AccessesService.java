package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.clients.UserDetailDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserRestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.EntryReport.EntryReport;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.DataType;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.dashboard.DashboardDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.GroupByPeriod;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Locale;
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
        accessDTO.setDocType(savedAccess.getAuth().getVisitor().getDocumentType());
        accessDTO.setVisitorType(savedAccess.getAuth().getVisitorType());
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
     * retrieves last access by document number.
     * @param docNumber document number.
     * @return last access.
     */
    @Override
    public AccessEntity getLastAccessByDocNumber(Long docNumber) {
        return accessesRepository.findByAuthVisitorDocNumber(docNumber).stream()
                .max(Comparator.comparing(AccessEntity::getActionDate))
                .orElse(null);
    }
    /**
     * Maps an AccessEntity to an AccessDTO.
     * @param accessEntity AccessEntity to be mapped.
     * @return AccessDTO representing the AccessEntity.
     */
    public AccessDTO mapToAccessDTO(AccessEntity accessEntity) {
        AccessDTO accessDTO = modelMapper.map(accessEntity, AccessDTO.class);

        accessDTO.setAuthorizerId(accessEntity.getCreatedUser());
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

    /**
     * Retrieves access information within a specified date range.
     * @param dateFrom date from
     * @param dateTo local to
     * @return access report.
     */
    @Override
    public EntryReport getAccessByDate(LocalDate dateFrom, LocalDate dateTo) {

        LocalDateTime startDate = dateFrom.atStartOfDay();
        LocalDateTime endDate = dateTo.atTime(LocalTime.MAX);

        return accessesRepository.countEntriesAndExitsBetweenDates(startDate, endDate);
    }
    /**
     * Retrieves access information by visitor type within a specified date range.
     * @param from the start date and time (inclusive) of the range
     * @param to   the end date and time (inclusive) of the range
     * @return a list of {@link DashboardDTO} objects representing
     * access counts per visitor type
     */
    @Override
    public List<DashboardDTO> getAccessesByVisitor(LocalDateTime from, LocalDateTime to) {
        List<Object[]> results = accessesRepository.findAccessCountsByVisitorType(from, to);

        Map<String, Long[]> visitorTypeAccessMap = new HashMap<>();

        for (Object[] row : results) {
            String visitorType = (String) row[0];
            Long entryCount = ((Number) row[1]).longValue();
            Long exitCount = ((Number) row[2]).longValue();

            visitorTypeAccessMap.put(visitorType, new Long[]{entryCount, exitCount});
        }

        return visitorTypeAccessMap.entrySet().stream()
                .map(entry -> new DashboardDTO(entry.getKey(), entry.getValue()[0], entry.getValue()[1])) // entradas y salidas
                .collect(Collectors.toList());
    }
    /**
     * @param from the start date/time (inclusive) of the range.
     * @param to the end date/time (inclusive) of the range.
     * @param visitorType the type of visitor for filtering (optional).
     * @param actionType the type of action for filtering (optional).
     * @param group the period to group the results by (DAY, WEEK, MONTH, YEAR).
     * @param dataType the type of data to retrieve (ALL or INCONSISTENCIES).
     * @return {@link DashboardDTO}  access counts grouped by the specified period.
     */
    @Override
    public List<DashboardDTO> getAccessGrouped(LocalDateTime from,
                                               LocalDateTime to,
                                               VisitorType visitorType,
                                               ActionTypes actionType,
                                               GroupByPeriod group,
                                               DataType dataType
                                               ) {

        String dateFormat;
        DateTimeFormatter formatter;
        ChronoUnit unit;

        switch (group) {
            case DAY:
                dateFormat = "%Y-%m-%d";
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                unit = ChronoUnit.DAYS;
                break;
            case WEEK:
                dateFormat = "%Y-%u";
                formatter = DateTimeFormatter.ofPattern("yyyy-ww");
                unit = ChronoUnit.WEEKS;
                break;
            case MONTH:
                dateFormat = "%Y-%m";
                formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                unit = ChronoUnit.MONTHS;
                break;
            case YEAR:
                dateFormat = "%Y";
                formatter = DateTimeFormatter.ofPattern("yyyy");
                unit = ChronoUnit.YEARS;
                break;
            default:
                throw new IllegalArgumentException("Invalid period for grouping: " + group);
        }

        List<Object[]> results = new ArrayList<>();
        if (dataType == DataType.ALL) {
            results = accessesRepository.findAccessCountsByGroup(from, to, visitorType, actionType, dateFormat);
        } else if (dataType == DataType.INCONSISTENCIES) {
            results = accessesRepository.findInconsistentAccessCountsByGroup(from,
                                                                            to,
                                                                            visitorType,
                                                                            actionType,
                                                                            dateFormat);
        } else if (dataType == DataType.LATE) {
            results = accessesRepository.findLateAccessCountsByGroup(from,
                    to,
                    visitorType,
                    actionType,
                    dateFormat);
        }

        Map<String, Long> accessMap = new HashMap<>();
        for (Object[] row : results) {
            String period = (String) row[0];
            Long accessCount = ((Number) row[1]).longValue();
            accessMap.put(period, accessCount);
        }

        List<DashboardDTO> dashboardData = new ArrayList<>();
        LocalDateTime current = from;
        while (!current.isAfter(to)) {
            String periodKey = current.format(formatter);
            Long accessCount = accessMap.getOrDefault(periodKey, 0L);
            dashboardData.add(new DashboardDTO(periodKey, accessCount, null));
            current = current.plus(1, unit);
        }

        dashboardData.sort(Comparator.comparing(DashboardDTO::getKey));

        return dashboardData;

    }
    /**
     * Retrieves the count of inconsistent access events
     * within the specified date range and filtered by visitor type.
     * @param from the start date and time (inclusive) of the range
     * @param to the end date and time (inclusive) of the range
     * @param visitorType the type of visitor to filter by
     * @return the count of inconsistent access events that match the given criteria
     */
    @Override
    public Long getInconsistentAccessCount(LocalDateTime from,
                                                         LocalDateTime to,
                                                         VisitorType visitorType) {
        return accessesRepository.findAccessInconsistentCounts(from, to, visitorType);

    }
    /**
     * Retrieves last access.
     * @param authId authId
     * @return an optional last access
     * */
    @Override
    public AccessEntity getLastAccessByAuthId(Long authId) {
        List<AccessEntity> accesses = accessesRepository.findAccessByAuthIdDesc(authId);
        return accesses.isEmpty() ? null : accesses.get(0);
    }
}
