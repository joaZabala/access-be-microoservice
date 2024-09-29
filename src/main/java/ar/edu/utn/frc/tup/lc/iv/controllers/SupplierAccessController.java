package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.WorkerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.SupplierAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for managing supplier access requests and responses.
 */
@RestController
@RequestMapping("/SupplierAccess")
public class SupplierAccessController {
    /**
     * Supplier Access Service dependency injection.
     */
    @Autowired
    private SupplierAccessService supplierAccessService;

    /**
     * Retrieves a list of supplier accesses based on the
     * provided supplier ID and date range.
     * @param supplierId the ID of the supplier for which to retrieve access records
     * @param dateFrom   the start date of the date range for the access records
     * @param dateTo     the end date of the date range for the access records
     * @return a list of {@link WorkerAccessDTO} containing the access records
     */
    @GetMapping("")
    public List<WorkerAccessDTO> getSupplierAccesses(@RequestParam("supplier_id") Long supplierId,
                                                     @RequestParam("date_from") LocalDate dateFrom,
                                                     @RequestParam("date_to") LocalDate dateTo) {
        return supplierAccessService.getSupplierAccessList("Proveedor", supplierId, dateFrom, dateTo);
    }
}

