package ar.edu.utn.frc.tup.lc.iv.dtos.common.EntryReport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryReport {
    @JsonProperty("entry_count")
    private Long EntryCount;
    @JsonProperty("exit_count")
    private Long ExitCount;
}
