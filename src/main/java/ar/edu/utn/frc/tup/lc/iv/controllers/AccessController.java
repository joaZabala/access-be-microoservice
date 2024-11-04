package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.EntryReport.EntryReport;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ErrorApi;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.dashboard.DashboardDTO;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.services.IAccessesService;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "Neighborhood Access Control", description = "APIs for managing and monitoring vehicle entries and exits")
public class AccessController {

    /** Service for handling access data operations. */
    @Autowired
    private IAccessesService accessesService;
    /** Service for handling auth data operations. */
    @Autowired
    private AuthService authService;

    /** Response code for successful operations. */
    private static final String SUCCESS_CODE = "200";

    /** Response code for not found errors. */
    private static final String NOT_FOUND_CODE = "404";

    /** Response code for bad request errors. */
    private static final String BAD_REQUEST_CODE = "400";

    /**
     * Authorize visitor with authorized ranges.
     *
     * @param accessDTO request.
     * @param userId    creator.
     * @return authorization created.
     */
    @Operation(summary = "Authorize visitor access", description = "Register a new access for a visitor to enter or exit the neighborhood")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SUCCESS_CODE, description = "Access authorization successfully created",
                content = @Content(schema = @Schema(implementation = AccessDTO.class))),
            @ApiResponse(responseCode = NOT_FOUND_CODE, description = "Vehicle or authorization details not found",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @PostMapping("/authorize")
    public ResponseEntity<AccessDTO> authorizeVisitor(@RequestBody AccessDTO accessDTO,
            @RequestHeader("x-user-id") Long userId) {
        AccessDTO access = authService.authorizeVisitor(accessDTO, userId);
        return access == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(access);
    }

    /**
     * Retrieves all access records based on filtering criteria.
     * @param filter The filtering criteria
     * @param page   The page number for pagination (default is 0).
     * @param size   The number of records per page (default is 10).
     * @return A list of {@link AccessDTO} containing access records.
     */
    @Operation(summary = "Get all access records",
        description = "Retrieves a paginated list of visitor accesses records with optional filtering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully retrieved access records",
                content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
            @ApiResponse(responseCode = BAD_REQUEST_CODE, description = "Invalid filter parameters provided",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @GetMapping
    public PaginatedResponse<AccessDTO> getAllAccess(@Valid AccessesFilter filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return accessesService.getAllAccess(filter, page, size);
    }

    /**
     * Retrieves all access entries.
     *
     * @return List of AccessDTOs representing entries.
     */
    @Operation(summary = "Get all entry records", description = "Retrieves a list of all visitor entries into the neighborhood")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully retrieved entry records",
                content = @Content(schema = @Schema(implementation = AccessDTO.class)))
    })
    @GetMapping("/entries")
    public List<AccessDTO> getAllEntries() {
        return accessesService.getAllEntries();
    }

    /**
     * Retrieves all access exits.
     *
     * @return List of AccessDTOs representing exits.
     */
    @Operation(summary = "Get all exit records", description = "Retrieves a list of all visitor exits from the neighborhood")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully retrieved exit records",
                content = @Content(schema = @Schema(implementation = AccessDTO.class)))
    })
    @GetMapping("/exits")
    public List<AccessDTO> getAllExits() {
        return accessesService.getAllExits();
    }

    /**
     * Check if visitor can do an action.
     *
     * @param carPlate car plate
     * @param action   action
     * @return boolean.
     */
    @Operation(summary = "Check visitor access permission",
        description = "Verifies if a visitor is permitted to perform a specific action (entry/exit) based on its license plate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SUCCESS_CODE, description = "Access check completed successfully",
                content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = BAD_REQUEST_CODE, description = "Invalid car plate or action type",
                content = @Content(schema = @Schema(implementation = ErrorApi.class))),
    })
    @GetMapping("/check-access")
    public Boolean checkAccess(@RequestParam String carPlate, @RequestParam ActionTypes action) {
        return accessesService.canDoAction(carPlate, action);
    }

    /**
     * Retrieves hourly access counts within a specified date range.
     *
     * @param from the start date of the range (inclusive)
     * @param to   the end date of the range (inclusive)
     * @return a list of {@link DashboardDTO} objects
     *         containing access counts per hour
     */
    @GetMapping("/hourly")
    public List<DashboardDTO> getHourlyAccesses(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);

        return accessesService.getHourlyInfo(fromDateTime, toDateTime);
    }

    /**
     * Retrieves day of week access counts within a specified date range.
     *
     * @param from the start date of the range (inclusive)
     * @param to   the end date of the range (inclusive)
     * @return a list of {@link DashboardDTO} objects
     *         containing access counts per day of week
     */
    @GetMapping("/weekly")
    public List<DashboardDTO> getDayOfWeekAccesses(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        LocalDateTime fromDateTime = from.atStartOfDay();
        LocalDateTime toDateTime = to.atTime(LocalTime.MAX);

        return accessesService.getDayOfWeekInfo(fromDateTime, toDateTime);
    }

    /**
     * Retrieves access counts by date range.
     * @param from date from.
     * @param to date to.
     * @return entry report.
     */
    @GetMapping("/getAccessCounts")
    public EntryReport getAccessByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("La fecha 'desde' no puede ser posterior a la fecha 'hasta'");
        }
        return accessesService.getAccessByDate(from, to);
    }
}
