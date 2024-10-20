package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VehicleTypes;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing the access details of a visitor,
 * including personal information, entry and exit times, vehicle details,
 * and any relevant comments.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccessDTO {

    /**
     * The first name of the visitor.
     */
    @JsonProperty("first_name")
    private String name;

    /**
     * The last name of the visitor.
     */
    @JsonProperty("last_name")
    private String lastName;

    /**
     * The document number of the visitor.
     */
    @JsonProperty("doc_number")
    private Long docNumber;

    /**
     * The action of the visitor's access.
     */
    @JsonProperty("action")
    private ActionTypes action;

    /**
     * The date and time of the visitor's access.
     */
    @JsonProperty("action_date")
    private LocalDateTime actionDate;

    /**
     * A description of the car used by the visitor.
     */
    @JsonProperty("vehicle_type")
    private VehicleTypes vehicleType;

    /**
     * A description of the car used by the visitor.
     */
    @JsonProperty("car_description")
    private String carDescription;

    /**
     * The registration number of the vehicle used by the visitor.
     */
    @JsonProperty("vehicle_reg")
    private String vehicleReg;

    /**
     * A description of the vehicle used by the visitor.
     */
    @JsonProperty("vehicle_description")
    private String vehicleDescription;

    /**
     * comments related to the visitor's access.
     */
    @JsonProperty("comments")
    private String comments;
}
