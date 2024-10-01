package ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Data Transfer Object representing the access details of an owner,
 * including personal information, entry and exit times,
 * and any relevant comments.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OwnerAccessDTO {

    /**
     * The first name of the owner.
     */
    @JsonProperty("first_name")
    private String name;

    /**
     * The last name of the owner.
     */
    @JsonProperty("last_name")
    private String lastName;

    /**
     * The entry date and time of the owner's access.
     */
    @JsonProperty("entry_date_time")
    private LocalDateTime entryDateTime;

    /**
     * The exit date and time of the owner's access.
     */
    @JsonProperty("exit_date_time")
    private LocalDateTime exitDateTime;

    /**
     * comments related to the owner's access.
     */
    @JsonProperty("comments")
    private String comments;
}
