package ar.edu.utn.frc.tup.lc.iv.dtos.common.EntryReport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Entry report.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryReport {
    /**
     * Entry counts.
     */
    @JsonProperty("entry_count")
    private Long entryCount;

    /**
     * exit counts.
     */
    @JsonProperty("exit_count")
    private Long exitCount;
}
