package ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor;

import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import lombok.Data;

/**
 * DTO for filters.
 */
@Data
public class VisitorFilter {
    /**
     * Filter text to search in vehicle
     * registration and visitor details.
     */
    private String textFilter;
    /**
     * Type of document to filter the access records.
     */
    private DocumentType documentType;
    /**
     * Type of visitor to filter the access records.
     */
    private VisitorType visitorType;
    /**
     * active filter the access records.
     */
    private Boolean active;
}
