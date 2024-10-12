package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDTO {
    @JsonProperty("auth_id")
    private Long authId;
    @JsonProperty("visitor")
    private VisitorDTO visitor;
    @JsonProperty("visitor_type")
    private VisitorType visitorType;
    @JsonProperty("auth_ranges")
    private List<AuthRangeDTO> authRanges;
    @JsonProperty("is_active")
    private boolean isActive;

}
