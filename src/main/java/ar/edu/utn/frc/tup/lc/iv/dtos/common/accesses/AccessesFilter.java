package ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses;

import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VehicleTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
/**
 * DTO for filters.
 */
@Data
public class AccessesFilter {
    /**
     * Filter text to search in vehicle
     * registration and visitor details.
     */
    private String textFilter;
    /**
     * Start date for filtering access records.
     */
    private LocalDate fromDate;
    /**
     * End date for filtering access records.
     */
    private LocalDate toDate;
    /**
     * Type of document to filter the access records.
     */
    private DocumentType documentType;
    /**
     * Type of vehicle to filter the access records.
     */
    private VehicleTypes vehicleType;
    /**
     * Type of action (e.g., enter, exit)
     * to filter the access records.
     */
    private ActionTypes actionType;
    /**
     * Type of visitor to filter the access records.
     */
    private VisitorType visitorType;
    /**
     * External ID to filter the access records.
     */
    private Long externalId;
}
