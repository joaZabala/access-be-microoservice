package ar.edu.utn.frc.tup.lc.iv.entities;


import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.VehicleTypes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "accesses")
@Getter
@Setter
//public class AccessesEntity extends AuditBaseEntity {
public class AccessEntity extends AuditBaseEntity {
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
    @JoinColumn(name = "auth_id")
    private AuthEntity auth;
    /**
     * Action type.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private ActionTypes action;
    /**
     * Date and time of the entry/exit to the neighborhood.
     */
    @Column(name = "action_date")
    private LocalDateTime actionDate;
    /**
     * Unique vehicle type identifier.
     */
    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "vehicle_type")
    private VehicleTypes vehicleType;
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
     * Identifier of plot of related access.
     */
    @Column(name = "plot_id")
    private Long plotId;
    /**
     * Identifier of supplier employee.
     */
    @Column(name = "supplier_employee_id")
    private Long supplierEmployeeId;

    /**
     * Observations on access.
     */
    @Column(name = "comments")
    private String comments;

    /**
     * Constructor.
     *
     * @param createdUser         The ID of the user who created the access.
     * @param lastUpdatedUser     The ID of the user who last updated the access.
     * @param auth                The authorized range associated.
     * @param action              The action type.
     * @param actionDate          The date and time of the entry/exit to the neighborhood.
     * @param vehicleType         The unique vehicle type identifier.
     * @param vehicleReg          The vehicle registration.
     * @param vehicleDescription   The vehicle description.
     * @param plotId              The identifier of the plot related to access.
     * @param supplierEmployeeId   The identifier of the supplier employee.
     * @param comments            Any observations on access.
     */
    @SuppressWarnings("PMD.ConstructorWithTooManyParameters")
    public AccessEntity(Long createdUser, Long lastUpdatedUser, AuthEntity auth, ActionTypes action, LocalDateTime actionDate,
                        VehicleTypes vehicleType, String vehicleReg, String vehicleDescription, Long plotId,
                        Long supplierEmployeeId, String comments) {
        super(createdUser, lastUpdatedUser);
        this.auth = auth;
        this.action = action;
        this.actionDate = actionDate;
        this.vehicleType = vehicleType;
        this.vehicleReg = vehicleReg;
        this.vehicleDescription = vehicleDescription;
        this.plotId = plotId;
        this.supplierEmployeeId = supplierEmployeeId;
        this.comments = comments;
    }
}
