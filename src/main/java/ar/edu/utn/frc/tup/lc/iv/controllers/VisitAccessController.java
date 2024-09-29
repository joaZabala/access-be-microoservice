package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.VisitAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.VisitAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for managing visit access requests and responses.
 */
@RestController
@RequestMapping("/VisitAccess")
public class VisitAccessController {
    /**
     * Visit Access Service dependency injection.
     */
    @Autowired
    private VisitAccessService visitAccessService;

    /**
     * Retrieves a list of visit accesses.
     * @param plot      the ID of the plot
     * @param visitorId the ID of the visitor (optional)
     * @param dateFrom  the start date of the date range for the access records
     * @param dateTo    the end date of the date range for the access records
     * @return a list of {@link VisitAccessDTO} containing the access records
     */
    @GetMapping("")
    public List<VisitAccessDTO> getVisitAccesses(@RequestParam("plot") Long plot,
                                                 @RequestParam(name = "visitor_id", required = false) Long visitorId,
                                                 @RequestParam("date_from") LocalDate dateFrom,
                                                 @RequestParam("date_to") LocalDate dateTo) {
        if (visitorId == null) {
            return visitAccessService.getVisitAccesses(plot, dateFrom, dateTo);
        } else {
            return visitAccessService.getVisitAccessesByID(plot, visitorId, dateFrom, dateTo);
        }
    }
}
