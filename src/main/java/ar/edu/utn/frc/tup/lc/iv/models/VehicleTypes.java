package ar.edu.utn.frc.tup.lc.iv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity class representing the types of vehicles.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypes extends AuditBase {
    /**
     * Unique identifier of the Authorized Types.
     */
    private Long vehicleTypeId;
    /**
     * Description of the vehicle Types.
     */
    private String description;
}
