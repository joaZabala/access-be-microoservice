package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthRangeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRangeResponseDTO {


    @JsonProperty("visitor")
    private VisitorDTO visitor;

    @JsonProperty("visitor_type")
    private VisitorType visitorType;

    @JsonProperty("plot_id")
    private Long plotId;
    

    @JsonProperty("auth_ranges")
    private List<AuthRangeDTO> authRanges;
    
}
