package ar.edu.utn.frc.tup.lc.iv.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The {@code ContactDTO} class represents a contact.
 * It serves as a data transfer object (DTO)
 * to encapsulate information about a owner contact,
 * including its identification, area measurements,
 * status, and type.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContactDTO {
    /**
     * The unique identifier for the contact.
     */
    @JsonProperty(value = "id")
    private Long id;

    /**
     * The type of contact.
     */
    @JsonProperty(value = "contact_type")
    private String contactType;

    /**
     * The value of the contact.
     */
    @JsonProperty(value = "contact_value")
    private String contactValue;

    /**
     * A list of the user subscriptions.
     */
    @JsonProperty(value = "subscriptions")
    private List<String> subscriptions;
}
