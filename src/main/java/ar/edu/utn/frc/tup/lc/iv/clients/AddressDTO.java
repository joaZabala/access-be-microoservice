package ar.edu.utn.frc.tup.lc.iv.clients;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code AddressDTO} class represents an address.
 * It serves as a data transfer object (DTO)
 * to encapsulate information about an owner contact,
 * including its identification, area measurements,
 * status, and type.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    /**
     * Address unique identifier.
     */
    @JsonProperty(value = "id")
    private Long id;

    /**
     * The street of the address.
     */
    @JsonProperty(value = "street_address")
    private String streetAddress;

    /**
     * The number of the address street.
     */
    @JsonProperty(value = "number")
    private Integer number;

    /**
     * The floor of the address (if necessary).
     */
    @JsonProperty(value = "floor")
    private Integer floor;

    /**
     * The apartment identifier of the address (if necessary).
     */
    @JsonProperty(value = "apartment")
    private String apartment;

    /**
     * The city of the address.
     */
    @JsonProperty(value = "city")
    private String city;

    /**
     * The province of the address.
     */
    @JsonProperty(value = "province")
    private String province;

    /**
     * The country of the address.
     */
    @JsonProperty(value = "country")
    private String country;

    /**
     * The postal code of the city of the address.
     */
    @JsonProperty(value = "postal_code")
    private Integer postalCode;
}
