package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable; // Import Serializable
import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Data Transfer Object for creating or updating an authorized range.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRangeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id of the authorized range.
     */
    @JsonProperty("auth_range_id")
    private Long authRangeId;

    /**
     * Date from to form the authorized range.
     */
    @JsonProperty("date_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    @Schema(type = "string", pattern = "dd-MM-yyy", example = "01-01-2022", description = "The start date of the authorized range.")
    private LocalDate dateFrom;

    /**
     * Date until to form the authorized range.
     */
    @JsonProperty("date_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    @Schema(type = "string", pattern = "dd-MM-yyy", example = "02-01-2024", description = "The end date of the authorized range.")
    private LocalDate dateTo;

    /**
     * Hour from to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "08:00", description = "The start time of the authorized range.")
    @JsonProperty("hour_from")
    private LocalTime hourFrom;

    /**
     * Hour until to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "17:00", description = "The end time of the authorized range.")
    @JsonProperty("hour_to")
    private LocalTime hourTo;

    /**
     * Days of the week to form the authorized range.
     */
    @JsonProperty("day_of_weeks")
    private String days;

    /**
     * Id of the plot.
     */
    @JsonProperty("plot_id")
    private Long plotId;

    /**
     * Comment of the authorized range.
     */
    @JsonProperty("comment")
    private String comment;

    /**
     * Status of the authorized range.
     */
    @JsonProperty("is_active")
    private boolean isActive;
}
