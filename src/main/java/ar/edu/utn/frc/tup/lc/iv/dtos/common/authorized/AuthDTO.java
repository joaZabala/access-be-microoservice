package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDTO {

    private Long authId;
    private VisitorDTO visitor;
    private VisitorType visitorType;
    private AuthRangeDTO ranges;
    private List<AuthRangeDTO> authRanges;
    private boolean isActive;

}
