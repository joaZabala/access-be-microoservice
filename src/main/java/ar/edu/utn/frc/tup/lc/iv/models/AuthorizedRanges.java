package ar.edu.utn.frc.tup.lc.iv.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing the authorized ranges.
 * The authorized ranges define a time frame for which an
 * authorized person is allowed access, and it is linked
 * to the authorized type the person.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuthorizedRanges extends AuditBase {
    /**
     * Unique identifier of the Acceses.
     */
    private Long authRangeId;
    /**
     * Unique parcel identifier.
     */
    private Long parcelId;
    /**
     * External ID to identify Suppliers, Employees, Owners and Cohabitants.
     */
    private Long externalId;
    /**
     * Date from to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime dateFrom;
    /**
     * Date until to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime dateTo;
}
