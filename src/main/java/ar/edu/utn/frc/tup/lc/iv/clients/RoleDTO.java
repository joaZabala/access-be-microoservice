package ar.edu.utn.frc.tup.lc.iv.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code Role} class represents a role in the app.
 * It is used in the service layer to manage roles.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    /**
     * The unique identifier of the role.
     */
    private Long id;

    /**
     * The unique code identifier of the role.
     */
    private Long code;

    /**
     * Name of the role.
     */
    private String name;

    /**
     * PrettyName of the role.
     */
    @JsonProperty("pretty_name")
    private String prettyName;

    /**
     * A description of the role.
     */
    private String description;

    /**
     * Indicates whether the role is active.
     */
    @JsonProperty("is_active")
    private Boolean isActive;
}
