package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.EmployeeAccessDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for the Employee Access Service, providing
 * methods for retrieving employee access records.
 */
public interface IEmployeeAccessService {

    /**
     * Retrieves a list of employee access records.
     * @param authTypeDescription the description of the authorization type
     * @param externalId the external ID
     * @param dateFrom the start date of the range
     * @param dateTo the end date of the range
     * @return a list of {@link EmployeeAccessDTO} mapped access records
     */
    List<EmployeeAccessDTO> getEmployeeAccessList(String authTypeDescription, Long externalId, LocalDate dateFrom, LocalDate dateTo);
}
