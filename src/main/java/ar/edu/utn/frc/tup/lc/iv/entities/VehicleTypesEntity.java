package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * Entity class representing the types of vehicles.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicle_types")
@Getter
@Setter
@Audited
@AuditTable("vehicle_types_audit")
public class VehicleTypesEntity extends AuditBaseEntity {
    /**
     * Unique identifier of the Authorized Types.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long vehicleTypeId;
    /**
     * Description of the vehicle Types.
     */
    @Column(name = "description", length = DESCRIPTION_MAX_LENGTH)
    private String carDescription;
    /**
     * Constant for the maximum length of the 'description' field.
     */
    public static final int DESCRIPTION_MAX_LENGTH = 100;
}
