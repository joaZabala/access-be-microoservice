package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRangeDTO {
    @JsonProperty("auth_range_id")
    private Long authRangeId;

    @JsonProperty("date_from")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    @Schema(type = "string", pattern = "dd-MM-yyy", example = "01-01-2022" , description = "The start date of the authorized range.")
    private LocalDate dateFrom;

    @JsonProperty("date_to")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    @Schema(type = "string", pattern = "dd-MM-yyy", example = "02-01-2024" , description = "The start date of the authorized range.")
    private LocalDate dateTo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "08:00" , description = "The start time of the authorized range.")
    @JsonProperty("hour_from")
    private LocalTime hourFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "17:00" , description = "The end time of the authorized range.")
    @JsonProperty("hour_to")
    private LocalTime hourTo;

    @JsonProperty("day_of_weeks")
    private String days;

    @JsonProperty("plot_id")
    private Long plotId;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("is_active")
    private boolean isActive;
}
