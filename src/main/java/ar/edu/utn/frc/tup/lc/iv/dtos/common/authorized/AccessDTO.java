package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VehicleTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
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
     * The last name of the visitor.
     */
    @JsonProperty("visitor_type")
    private VisitorType visitorType;

    /**
     * The document number of the visitor.
     */
    @JsonProperty("doc_type")
    private DocumentType docType;

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

    /**
     * authorizer name.
     */
    @JsonProperty("auth_first_name")
    private String authName;

    /**
     * The last name of authorizer.
     */
    @JsonProperty("auth_last_name")
    private String authLastName;

    /**
     * The document number of the visitor.
     */
    @JsonProperty("auth_doc_number")
    private Long authDocNumber;

    /**
     * The document type of the authorizer.
     */
    @JsonProperty("auth_doc_type")
    private String authDocType;


    /**
     * The id of the authorizer.
     */
    @JsonProperty("authorizer_id")
    private Long authorizerId;

}
