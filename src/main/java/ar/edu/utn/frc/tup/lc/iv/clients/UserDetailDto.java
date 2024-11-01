package ar.edu.utn.frc.tup.lc.iv.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * This class is returned by the user endpoint.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {
    /**
     * Unique identifier for the user.
     */
    private Long id;

    /**
     * First name of the owner of the user.
     */
    @JsonProperty(value = "first_name")
    private String firstName;

    /**
     * Second name of the owner of the user.
     */
    @JsonProperty(value = "last_name")
    private String lastName;

    /**
     * Name of the user.
     */
    @JsonProperty(value = "user_name")
    private String userName;

    /**
     * Email of the user.
     */
    @JsonProperty(value = "email")
    private String email;

    /**
     * Manages logical deletion.
     */
    @JsonProperty(value = "is_active")
    private boolean isActive;

    /**
     * In case that the user is an owner this field will have its id.
     */
    @JsonProperty(value = "owner_id")
    private Long ownerId;

    /**
     * In case that the user is an owner this field will have the plot id.
     */
    @JsonProperty(value = "plot_id")
    private Long plotId;

    /**
     * Address of the user.
     */
    @JsonProperty(value = "addresses")
    private List<AddressDTO> addresses;

    /**
     * Contact of the user.
     */
    @JsonProperty(value = "contacts")
    private List<ContactDTO> contacts;

    /**
     * Role of the user.
     */
    @JsonProperty(value = "roles")
    private List<RoleDTO> roles;
}
