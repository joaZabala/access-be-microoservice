package ar.edu.utn.frc.tup.lc.iv.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * object used to return a paginated response.
 * @param <T> the type of the items.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    /**
     * the list of items.
     */
    private List<T> items;
    /**
     * the total number of elements.
     */
    @JsonProperty("total_elements")
    private long totalElements;
}
