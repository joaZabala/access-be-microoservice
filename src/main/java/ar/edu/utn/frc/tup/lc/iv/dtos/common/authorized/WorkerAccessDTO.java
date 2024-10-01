package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Object representing the access details of a worker,
 * including their external ID, entry and exit times, vehicle details,
 * and any relevant comments.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkerAccessDTO {

    /**
     * The external ID of the worker.
     */
    @JsonProperty("external_id")
    private Long externalId;

    /**
     * The entry date and time of the worker's access.
     */
    @JsonProperty("entry_date_time")
    private LocalDateTime entryDateTime;

    /**
     * The exit date and time of the worker's access.
     */
    @JsonProperty("exit_date_time")
    private LocalDateTime exitDateTime;

    /**
     * A description of the car used by the worker.
     */
    @JsonProperty("car_description")
    private String carDescription;

    /**
     * The registration number of the vehicle used by the worker.
     */
    @JsonProperty("vehicle_reg")
    private String vehicleReg;

    /**
     * A description of the vehicle used by the worker.
     */
    @JsonProperty("vehicle_description")
    private String vehicleDescription;

    /**
     * comments related to the worker's access.
     */
    @JsonProperty("comments")
    private String comments;
}
