package ar.edu.utn.frc.tup.lc.iv.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * Entity class representing the types of vehicles.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Vehicle_Types")
public class VehicleTypesEntity extends AuditBaseEntity {
    /**
     * Unique identifier of the Authorized Types.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_type_id")
    private Long vehicleTypeId;
    /**
     * Description of the vehicle Types.
     */
    @Column(length = DESCRIPTION_MAX_LENGTH)
    private String description;
    /**
     * Constant for the maximum length of the 'description' field.
     */
    public static final int DESCRIPTION_MAX_LENGTH = 100;
}
