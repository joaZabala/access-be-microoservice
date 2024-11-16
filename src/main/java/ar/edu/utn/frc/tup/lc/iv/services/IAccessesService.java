package ar.edu.utn.frc.tup.lc.iv.services;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This interface defines the contract for a service
 * that manages authorized persons.
 */
public interface IAccessesService {

    /**
     * Retrieves access records with optional filtering and pagination.
     * @param filter the filtering criteria for access records.
     * @param page   the page number for pagination.
     * @param size   the number of records per page.
     * @return a paginated response containing a list of {@link AccessDTO}.
     */
    PaginatedResponse<AccessDTO> getAllAccess(AccessesFilter filter, int page, int size);

    /**
     * Retrieves all entries (access actions of type ENTRY).
     * @return A list of {@link AccessDTO} representing entry records.
     */
    List<AccessDTO> getAllEntries();

    /**
     * Retrieves all exits (access actions of type EXIT).
     * @return A list of {@link AccessDTO} representing exit records.
     */
    List<AccessDTO> getAllExits();

    /**
     * Retrieves access records filtered by visitor type.
     * @param visitorType The type of the visitor to filter by.
     * @return A list of {@link AccessDTO} representing access
     * records for the specified visitor type.
     */
    List<AccessDTO> getAllAccessByType(VisitorType visitorType);

    /**
     * Retrieves access records filtered by visitor type and external ID.
     * @param visitorType The type of the visitor to filter by.
     * @param externalId The external ID associated with the access records.
     * @return A list of {@link AccessDTO} representing access
     * records for the specified visitor type and external ID.
     */
    List<AccessDTO> getAllAccessByTypeAndExternalID(VisitorType visitorType, Long externalId);


    //    List<AccessDTO> getMissingExits();

    /**
     * Registers a new access entry in the system.
     *
     * @param accessEntity The {@link AccessEntity} to be registered.
     * @return A {@link AccessDTO} representing the registered access.
     */
    AccessDTO registerAccess(AccessEntity accessEntity);

    /**
     * Checks if a visitor can do an action.
     * @param carPlate plate of the car.
     * @param action action to do.
     * @return true if the visitor can do the action, false otherwise.
     */
    Boolean canDoAction(String carPlate, ActionTypes action);
    /**
     * Retrieves hourly access information within a specified date range.
     * @param from the start date and time (inclusive) of the range
     * @param to   the end date and time (inclusive) of the range
     * @return a list of {@link DashboardDTO} objects representing
     * access counts per hour
     */
    List<DashboardDTO> getHourlyInfo(LocalDateTime from, LocalDateTime to);
    /**
     * Retrieves hourly access information within a specified date range.
     * @param from the start date and time (inclusive) of the range
     * @param to   the end date and time (inclusive) of the range
     * @return a list of {@link DashboardDTO} objects representing
     * access counts per day of week
     */
    List<DashboardDTO> getDayOfWeekInfo(LocalDateTime from, LocalDateTime to);

    /**
     * Retrieves access records filtered by visitor type and external ID.
     * @param from date from
     * @param localTo local to
     * @return a list of {@link DashboardDTO} objects
     */
    EntryReport getAccessByDate(LocalDate from, LocalDate localTo);
    /**
     * Retrieves access information by visitor type within a specified date range.
     * @param from the start date and time (inclusive) of the range
     * @param to   the end date and time (inclusive) of the range
     * @return a list of {@link DashboardDTO}  access counts per visitor type
     */
    List<DashboardDTO> getAccessesByVisitor(LocalDateTime from, LocalDateTime to);
    /**
     * @param from the start date/time (inclusive) of the range.
     * @param to the end date/time (inclusive) of the range.
     * @param visitorType the type of visitor for filtering (optional).
     * @param actionType the type of action for filtering (optional).
     * @param group the period to group the results by (DAY, WEEK, MONTH, YEAR).
     * @param dataType the type of data to retrieve (ALL or INCONSISTENCIES).
     * @return {@link DashboardDTO}  access counts grouped by the specified period.
     */
    List<DashboardDTO> getAccessGrouped(LocalDateTime from,
                                        LocalDateTime to,
                                        VisitorType visitorType,
                                        ActionTypes actionType,
                                        GroupByPeriod group,
                                        DataType dataType
    );
    /**
     * Retrieves the count of inconsistent access events within
     * the specified date range and filtered by visitor type.
     * @param from the start date and time (inclusive) of the range
     * @param to the end date and time (inclusive) of the range
     * @param visitorType the type of visitor to filter by
     * @return the count of inconsistent access events that match the given criteria
     */
    Long getInconsistentAccessCount(LocalDateTime from,
                                    LocalDateTime to,
                                    VisitorType visitorType);


    /**
     * Retrieves the last access of an authorized person.
     * @param authId
     * @return the last access of the authorized person.
     */
    AccessEntity getLastAccessByAuthId(Long authId);
    /**
     * retrieves last access by document number.
     * @param docNumber document number.
     * @return last access.
     */
    AccessEntity getLastAccessByDocNumber(Long docNumber);
}
