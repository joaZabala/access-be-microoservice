package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeRequestDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Data Transfer Object for registering authorized ranges.
 * This DTO is used to encapsulate the data required for
 * creating or updating an authorized range.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorAuthRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Visitor type.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("visitor_type")
    private VisitorType visitorType;

    /**
     * The document number of the visitor.
     */
    @JsonProperty("external_id")
    private Long externalID;

    /**
     * Visitor request.
     */
    @JsonProperty("visitor_request")
    private VisitorRequest visitorRequest;

    /**
     * Authorized range request.
     */
    @JsonProperty("auth_range_request")
    private List<AuthRangeRequestDTO> authRangeRequest;
}
