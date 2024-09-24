package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
/**
 * Entity class representing the accesses to the neighborhood.
 * Each access record captures details about the entry and exit
 * of vehicles,including the vehicle type, registration, and any
 * relevant comments or observations.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "Accesses")
@Getter
@Setter
//public class AccessesEntity extends AuditBaseEntity {
public class AccessesEntity extends AuditBaseEntity {
    /**
     * Unique identifier of the Accesses.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long accessId;
    /**
     * The authorized range associated.
     */
    @ManyToOne
    @JoinColumn(name = "auth_range_id")
    private AuthorizedRangesEntity authRange;
    /**
     * Date and time of entry to the neighborhood.
     */
    @Column(name = "entry_date")
    private LocalDateTime entryDate;
    /**
     * Date and time of departure to the neighborhood.
     */
    @Column(name = "exit_date")
    private LocalDateTime exitDate;
    /**
     * Unique vehicle type identifier.
     */
    @ManyToOne
    @JoinColumn(name = "vehicle_type_id")
    private VehicleTypesEntity vehicleType;
    /**
     * Vehicle registration.
     */
    @Column(name = "vehicle_reg")
    private String vehicleReg;
    /**
     * Vehicle Description.
     */
    @Column(name = "vehicle_description")
    private String vehicleDescription;

    /**
     * Identifier of the owner who authorizes an operation.
     */
    @Column(name = "owner_id")
    private Long ownerId;

    /**
     * Visitor associated with the access.
     */
    @ManyToOne
    @JoinColumn(name = "visitor_id")
    private VisitorsEntity visitor;

    /**
     * Observations on access.
     */
    @Column(name = "comments")
    private String comments;
}
