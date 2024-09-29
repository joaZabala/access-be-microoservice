package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.EmployeeAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.EmployeeAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for managing employee access information,
 * providing endpoints to retrieve access details based on employee ID
 * and specified date range.
 */
@RestController
@RequestMapping("/employee-access")
public class EmployeeAccessController {

    /**
     * Employee Access Service dependency injection.
     */
    @Autowired
    private EmployeeAccessService employeeAccessService;

    /**
     * Retrieves a list of access records for a specific employee.
     * @param employeeId the ID of the employee
     * @param dateFrom the start date for filtering access records
     * @param dateTo the end date for filtering access records
     * @return a list of EmployeeAccessDTO containing access details
     *      for the specified employee
     */
    @GetMapping("")
    public List<EmployeeAccessDTO> getEmployeeAccesses(@RequestParam("employee_id") Long employeeId,
                                                       @RequestParam("date_from") LocalDate dateFrom,
                                                       @RequestParam("date_to") LocalDate dateTo) {
        return employeeAccessService.getEmployeeAccessList("Empleado", employeeId, dateFrom, dateTo);
    }
}
