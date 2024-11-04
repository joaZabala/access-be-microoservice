package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.dashboard.DashboardDTO;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Controller for managing access-related operations.
 * Provides endpoints for retrieving access records
 * based on visitor type, external ID,
 * and different access states (entries, exits, missing exits).
 */
@RestController
@RequestMapping("/access")
public class AccessController {

    /** Service for handling access data operations. */
    @Autowired
    private IAccessesService accessesService;
    /** Service for handling auth data operations. */
    @Autowired
    private AuthService authService;

    /**
     * Authorize visitor with authorized ranges.
     *
     * @param accessDTO request.
     * @param userId creator.
     * @return authorization created.
     */
    @PostMapping("/authorize")
    public ResponseEntity<AccessDTO> authorizeVisitor(@RequestBody AccessDTO accessDTO,  @RequestHeader("x-user-id") Long userId) {
        AccessDTO access = authService.authorizeVisitor(accessDTO, userId);
        return access == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(access);
    }

    /**
     * Retrieves all access records, optionally filtered
     * by criteria specified in the filter object.
     * @param filter    The filtering criteria
     * @param page      The page number for pagination (default is 0).
     * @param size      The number of records per page (default is 10).
     * @return A list of {@link AccessDTO} containing access records.
     */
    @GetMapping
    public PaginatedResponse<AccessDTO> getAllAccess(@Valid AccessesFilter filter,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {

        return accessesService.getAllAccess(filter, page, size);
    }

    /**
     * Retrieves all access entries.
     * @return List of AccessDTOs representing entries.
     */
    @GetMapping("/entries")
    public List<AccessDTO> getAllEntries() {
        return accessesService.getAllEntries();
    }

    /**
     * Retrieves all access exits.
     * @return List of AccessDTOs representing exits.
     */
    @GetMapping("/exits")
    public List<AccessDTO> getAllExits() {
        return accessesService.getAllExits();
    }

    /**
     * Check if visitor can do an action.
     * @param carPlate car plate
     * @param action action
     * @return boolean.
     */
 @GetMapping("/check-access")
    public Boolean checkAccess(@RequestParam String carPlate, @RequestParam ActionTypes action) {
        return accessesService.canDoAction(carPlate, action);
    }

    /**
     * Retrieves hourly access counts within a specified date range.
     * @param from the start date of the range (inclusive)
     * @param to   the end date of the range (inclusive)
     * @return a list of {@link DashboardDTO} objects
     * containing access counts per hour
     */
    @GetMapping("/hourly")
    public List<DashboardDTO> getHourlyAccesses(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);

        return accessesService.getHourlyInfo(fromDateTime, toDateTime);
    }

    /**
     * Retrieves day of week access counts within a specified date range.
     * @param from the start date of the range (inclusive)
     * @param to   the end date of the range (inclusive)
     * @return a list of {@link DashboardDTO} objects
     * containing access counts per day of week
     */
    @GetMapping("/weekly")
    public List<DashboardDTO> getDayOfWeekAccesses(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);

        return accessesService.getDayOfWeekInfo(fromDateTime, toDateTime);
    }
}
