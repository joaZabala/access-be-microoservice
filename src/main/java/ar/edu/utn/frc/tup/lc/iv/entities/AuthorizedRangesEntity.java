package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;
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
@Entity
@Table(name = "Authorized_Ranges")
public class AuthorizedRangesEntity extends AuditBaseEntity {
    /**
     * Unique identifier of the Acceses.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_range_id")
    private Long authRangeId;
    /**
     * Unique Authorized type identifier.
     */
    @ManyToOne
    @JoinColumn(name = "auth_type_id")
    private AuthorizedTypesEntity authType;
    /**
     * Unique Authorized identifier.
     */
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private VisitorsEntity visitorId;
    /**
     * External ID to identify Suppliers, Employees, Owners and Cohabitants.
     */
    @Column(name = "external_id")
    private Long externalId;
    /**
     * Date from to form the authorized range.
     */
    @Column(name = "date_from")
    private LocalDate dateFrom;
    /**
     * Date until to form the authorized range.
     */
    @Column(name = "date_to")
    private LocalDate dateTo;
    /**
     * The start time of the authorized range.
     */
    @Column(name = "hour_from")
    private LocalTime hourFrom;
    /**
     * The end time of the authorized range.
     */
    @Column(name = "hour_to")
    private LocalTime hourTo;
    /**
     * Days of the week when access is allowed (Monday, Tuesday,...).
     */
    @Column(name = "days")
    private String days;
    /**
     * Unique identifier of the plot associated with the range.
     */
    @Column(name = "plot_id")
    private Long plotId;
    /**
     * Additional comments.
     */
    @Column(name = "comments")
    private String comment;
    /**
     * Status indicating if the range is active.
     */
    @Column(name = "is_active")
    private boolean isActive;
}
