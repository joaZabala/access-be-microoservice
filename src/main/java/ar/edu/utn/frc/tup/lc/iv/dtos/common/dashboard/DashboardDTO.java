package ar.edu.utn.frc.tup.lc.iv.dtos.common.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Data Transfer Object (DTO)
 * representing dashboard data.
 */
@Data
@AllArgsConstructor
public class DashboardDTO {
    /**
     * The key representing a data type in the dashboard.
     */
    private String key;

    /**
     * The value associated with the key,
     * representing a quantifiable metric.
     */
    private Long value;
}
