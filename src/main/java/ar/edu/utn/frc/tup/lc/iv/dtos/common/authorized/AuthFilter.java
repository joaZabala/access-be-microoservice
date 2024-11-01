package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO for filters.
 */
@Data
public class AuthFilter {
    /**
     * Filter by identifier.
     */
    private Long id;
    /**
     * Filter text to search
     * text values on table .
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
     * Start date for filtering access records.
     */
    private LocalDate fromDate;
    /**
     * End date for filtering access records.
     */
    private LocalDate toDate;
}
