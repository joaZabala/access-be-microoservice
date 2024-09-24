package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;


import ar.edu.utn.frc.tup.lc.iv.models.Visitors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccessDTO {

    private Long accessId;

    private String name;

    private String lastName;

    private Long docNumber;

    private LocalDateTime entryDateTime;

    private LocalDateTime exitDateTime;

    private String carDescription;

    private String vehicleReg;

    private String vehicleDescription;

    private String Comments;
}
