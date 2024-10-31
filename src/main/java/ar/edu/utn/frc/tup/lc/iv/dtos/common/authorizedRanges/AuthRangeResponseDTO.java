package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing access authorization details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRangeResponseDTO {

    /**
     * Visitor information associated with
     * the access authorization.
     */
    @JsonProperty("visitor")
    private VisitorDTO visitor;

    /**
     * Type of visitor for the access authorization.
     */
    @JsonProperty("visitor_type")
    private VisitorType visitorType;

    /**
     * ID of the plot associated with the access authorization.
     */
    @JsonProperty("plot_id")
    private Long plotId;

    /**
     * List of authorized ranges associated with the visitor.
     */
    @JsonProperty("auth_ranges")
    private List<AuthRangeDTO> authRanges;
}
