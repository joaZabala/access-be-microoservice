package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for registering authorized ranges.
 * This DTO is used to encapsulate the data required for
 * creating or updating an authorized range.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorAuthRequest {

    /**
     * Visitor type.
     */
    private VisitorType visitorType;

    /**
     * Visitor request.
     */
    private VisitorRequest visitorRequest;

    /**
     * Authorized range request.
     */
    private List<AuthRangeDto> authRangeRequest;
}
