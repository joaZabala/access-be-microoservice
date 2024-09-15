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
    @JoinColumn(name = "auth_id")
    private AuthorizedEntity authorized;
    /**
     * Unique parcel identifier.
     */
    @Column(name = "parcel_id")
    private Long parcelId;
    /**
     * External ID to identify Suppliers, Employees, Owners and Cohabitants.
     */
    @Column(name = "external_id")
    private Long externalId;
    /**
     * Date from to form the authorized range.
     */
    @Column(name = "date_from")
    private LocalDateTime dateFrom;
    /**
     * Date until to form the authorized range.
     */
    @Column(name = "date_to")
    private LocalDateTime dateTo;
}
