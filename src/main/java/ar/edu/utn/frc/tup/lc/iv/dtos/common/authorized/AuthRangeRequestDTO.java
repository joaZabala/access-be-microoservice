package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Data Transfer Object for creating or updating an authorized range.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRangeRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Format for dates as "dd-MM-yyyy".
     */
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    /**
     * Format for time as "HH:mm:ss".
     */
    private static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * Represents the string data type.
     */
    private static final String STRING_TYPE = "string";

    /**
     * Date from to form the authorized range.
     */
    @JsonProperty("date_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @Schema(type = STRING_TYPE, pattern = DATE_FORMAT, example = "01-01-2022", description = "The start date of the authorized range.")
    @NotNull(message = "DateFrom cant be null")
    private LocalDate dateFrom;

    /**
     * Date until to form the authorized range.
     */
    @JsonProperty("date_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    @Schema(type = STRING_TYPE, pattern = DATE_FORMAT, example = "02-01-2024", description = "The end date of the authorized range.")
    @NotNull(message = "DateTo cant be null")
    private LocalDate dateTo;

    /**
     * Hour from to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
    @Schema(type = STRING_TYPE, pattern = TIME_FORMAT, example = "08:00:00", description = "The start time of the authorized range.")
    @JsonProperty("hour_from")
    @NotNull(message = "HourFrom cant be null")
    private LocalTime hourFrom;

    /**
     * Hour until to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
    @Schema(type = STRING_TYPE, pattern = TIME_FORMAT, example = "17:00:00", description = "The end time of the authorized range.")
    @JsonProperty("hour_to")
    @NotNull(message = "HourTo cant be null")
    private LocalTime hourTo;

    /**
     * Days of the week to form the authorized range.
     */
    @JsonProperty("days_of_week")
    private List<DayOfWeek> daysOfWeek;

    /**
     * Comment of the authorized range.
     */
    @JsonProperty("comment")
    private String comment;
    /**
     * ID of the authorized range.
     */
    @JsonProperty("auth_range_id")
    private Long authRangeId;
    /**
     * Status of the authorized range.
     */
    @JsonProperty("is_active")
    private boolean isActive;
}
