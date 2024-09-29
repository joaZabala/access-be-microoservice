package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.OwnerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.OwnerAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for managing owner access requests and responses.
 */
@RestController
@RequestMapping("/OwnerAccess")
public class OwnerAccessController {

    /**
     * Owner Access Service dependency injection.
     */
    @Autowired
    private OwnerAccessService ownerAccessService;

    /**
     * Retrieves a list of owner accesses based on the provided
     * plot ID and date range.
     * @param plotId   the ID of the plot for which to retrieve access records
     * @param dateFrom the start date of the date range for the access records
     * @param dateTo   the end date of the date range for the access records
     * @return a list of {@link OwnerAccessDTO} containing the access records
     */
    @GetMapping("")
    public List<OwnerAccessDTO> getOwnerAccesses(@RequestParam("plot_id") Long plotId,
                                                 @RequestParam("date_from") LocalDate dateFrom,
                                                 @RequestParam("date_to") LocalDate dateTo) {
        return ownerAccessService.getOwnerAcessList(plotId, dateFrom, dateTo);
    }
}
