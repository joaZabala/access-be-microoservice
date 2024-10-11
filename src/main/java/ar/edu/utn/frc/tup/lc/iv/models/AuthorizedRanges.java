package ar.edu.utn.frc.tup.lc.iv.models;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthorizedRangesEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

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
     * Unique Authorized identifier.
     */
    private Long visitorId;
    /**
     * External ID to identify Suppliers, Employees, Owners and Cohabitants.
     */
    private Long externalId;
    /**
     * Date from to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private LocalDate dateFrom;
    /**
     * Date until to form the authorized range.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private LocalDate dateTo;
    /**
     * Starting time for the authorized range on each day.
     * Defines the hour from which access is allowed.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hourFrom;
    /**
     * Ending time for the authorized range on each day.
     * Defines the hour until which access is allowed.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime hourTo;
    /**
     * Days of the week for which the authorization is valid (Monday, Wednesday).
     */
    private String days;
    /**
     * Unique plot identifier.
     */
    private Long plotId;

    /**
     * Additional comments or observations related to the authorization.
     */
    private String comment;

    /**
     * Indicates whether the authorization is currently active.
     */
    private boolean isActive;
    /**
     * Constructor that initializes AuthorizedRanges from AuthorizedRangesEntity.
     *
     * @param authorizedRangesEntity the AuthorizedRangesEntity object
     */
    public AuthorizedRanges(AuthorizedRangesEntity authorizedRangesEntity) {
        this.authRangeId = authorizedRangesEntity.getAuthRangeId();
        this.visitorId = authorizedRangesEntity.getVisitorId() != null
                ? authorizedRangesEntity.getVisitorId().getVisitorId() : null;
        this.externalId = authorizedRangesEntity.getExternalId();
        this.dateFrom = authorizedRangesEntity.getDateFrom();
        this.dateTo = authorizedRangesEntity.getDateTo();
        this.hourFrom = authorizedRangesEntity.getHourFrom();
        this.hourTo = authorizedRangesEntity.getHourTo();
        this.days = authorizedRangesEntity.getDays();
        this.plotId = authorizedRangesEntity.getPlotId();
        this.comment = authorizedRangesEntity.getComment();
        this.isActive = authorizedRangesEntity.isActive();
    }
}
