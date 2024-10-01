package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Object representing the access details of an employee,
 * including entry and exit times along with any relevant comments.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeAccessDTO {

    /**
     * The entry date and time of the employee access.
     */
    @JsonProperty("entry_date_time")
    private LocalDateTime entryDateTime;

    /**
     * The exit date and time of the employee access.
     */
    @JsonProperty("exit_date_time")
    private LocalDateTime exitDateTime;

    /**
     * comments related to the employee access.
     */
    @JsonProperty("comments")
    private String comments;
}
