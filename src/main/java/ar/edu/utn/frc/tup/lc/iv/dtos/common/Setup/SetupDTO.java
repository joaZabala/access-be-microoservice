package ar.edu.utn.frc.tup.lc.iv.dtos.common.Setup;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
/**
 * DTO for setup config.
 * */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class SetupDTO {
    /**
     * Format for time as "HH:mm:ss".
     */
    private static final String TIME_FORMAT = "HH:mm:ss";
    /**
     * Represents the string data type.
     */
    private static final String STRING_TYPE = "string";
    /**
     * Grace period in minutes before marking an entry as late.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
    @Schema(type = STRING_TYPE, pattern = TIME_FORMAT, example = "17:00:00")
    @JsonProperty("time_of_grace")
    private LocalTime timeOfGrace;
    /**
     * Final time after which employees
     * must leave the facility.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIME_FORMAT)
    @Schema(type = STRING_TYPE, pattern = TIME_FORMAT, example = "17:00:00")
    @JsonProperty("target_time")
    private LocalTime targetTime;
}
