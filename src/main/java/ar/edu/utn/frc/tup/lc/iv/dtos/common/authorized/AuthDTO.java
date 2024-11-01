package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for the Authorized entity
 * that represents an authorized person.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDTO {
    /**
     * ID of the authorization person.
     */
    @JsonProperty("auth_id")
    private Long authId;

    /**
     * Type of the authorized person.
     */
    @JsonProperty("visitor_type")
    private VisitorType visitorType;
    /**
     * ID of the plot.
     */
    @JsonProperty("plot_id")
    private Long plotId;

    /**
     * DTO of the authorized person.
     */
    @JsonProperty("visitor")
    private VisitorDTO visitor;

    /**
     * Type of the authorized person.
     */
    @JsonProperty("external_id")
    private Long externalID;


    /**
     * List of authorized ranges.
     */
    @JsonProperty("auth_ranges")
    private List<AuthRangeDTO> authRanges;

    /**
     *  ID of the authorizer person.
     */
    @JsonProperty("authorizer_id")
    private Long authorizerId;

    /**
     * Status of the authorized person.
     */
    @JsonProperty("is_active")
    private boolean isActive;
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
}
