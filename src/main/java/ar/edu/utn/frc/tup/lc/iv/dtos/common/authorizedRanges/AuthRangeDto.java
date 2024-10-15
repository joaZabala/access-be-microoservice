package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRangeDto {
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
    @Schema(type = "string", pattern = "dd-MM-yyy", example = "02-01-2022", description = "The end date of the authorized range.")
    private LocalDate dateTo;
    /**
     * Starting time for the authorized range on each day.
     * Defines the hour from which access is allowed.
     */
    @JsonProperty("hour_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "08:00:00", description = "The start time of the authorized range.")
    private LocalTime hourFrom;
    /**
     * Ending time for the authorized range on each day
     * Defines the hour until which access is allowed.
     */
    @JsonProperty("hour_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "17:00:00", description = "The end time of the authorized range.")
    private LocalTime hourTo;
    /**
     * Days of the week when access is allowed (e.g., Monday, Tuesday,...).
     */
    @JsonProperty("day_of_weeks")
    private List<DayOfWeek> dayOfWeeks;
    /**
     * Unique plot identifier.
     */
    @JsonProperty("plot_id")
    private Long plotId;

    /**
     * Additional comments or observations related to the authorization.
     */
    private String comment;

    /**
     * Indicates whether the authorization is currently active.
     */
    private boolean isActive;
}
