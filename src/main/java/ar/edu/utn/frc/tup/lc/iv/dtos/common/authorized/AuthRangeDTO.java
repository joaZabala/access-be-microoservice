package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRangeDTO {
    private Long authRangeId;
    private String dateFrom;
    private String dateTo;
    private String hourFrom;
    private String hourTo;
    private String days;
    private Long plotId;
    private String comment;
    private boolean isActive;
}
