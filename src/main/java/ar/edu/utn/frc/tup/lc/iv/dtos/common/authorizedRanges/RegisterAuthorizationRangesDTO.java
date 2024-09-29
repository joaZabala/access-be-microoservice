package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Data Transfer Object for registering authorized ranges.
 * This DTO is used to encapsulate the data required for
 * creating or updating an authorized range.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterAuthorizationRangesDTO {

    /**
     * Unique Authorized type identifier.
     */
    @JsonProperty("auth_type_id")
    private Long authTypeId;

    /**
     * Unique Authorized identifier for the visitor.
     */
    @JsonProperty("visitor_id")
    private Long visitorId;

    /**
     * External ID to identify Suppliers, Employees, Owners, and Cohabitants.
     */
    @JsonProperty("external_id")
    private Long externalId;

    /**
     * Date from which the authorized range is valid.
     */
    @JsonProperty("date_from")
    private LocalDate dateFrom;

    /**
     * Date until which the authorized range is valid.
     */
    @JsonProperty("date_to")
    private LocalDate dateTo;

    /**
     * The start time of the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonProperty("hour_from")
    private LocalTime hourFrom;

    /**
     * The end time of the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonProperty("hour_to")
    private LocalTime hourTo;

    /**
     * Days of the week when access is allowed (e.g., Monday, Tuesday,...).
     */
    @JsonProperty("day_of_weeks")
    private List<DayOfWeek> dayOfWeeks;

    /**
     * Unique identifier of the plot associated with the range.
     */
    @JsonProperty("plot_id")
    private Long plotId;

    /**
     * Additional comments related to the authorized range.
     */
    private String comment;

}
