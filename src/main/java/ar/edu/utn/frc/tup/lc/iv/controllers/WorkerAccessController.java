package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.WorkerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.WorkerAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for managing worker access requests and responses.
 */
@RestController
@RequestMapping("/worker-access")
public class WorkerAccessController {
    /**
     * Worker Access Service dependency injection.
     */
    @Autowired
    private WorkerAccessService workerAccessService;

    /**
     * Retrieves a list of worker accesses based on the plot ID.
     * @param workerId the ID of the worker (optional)
     * @param plotId   the ID of the plot for which to retrieve worker access records
     * @param dateFrom the start date of the date range for the access records
     * @param dateTo   the end date of the date range for the access records
     * @return a list of {@link WorkerAccessDTO} containing the access records
     */
    @GetMapping("")
    public List<WorkerAccessDTO> getWorkerAccess(@RequestParam(name = "worker_id", required = false) Long workerId,
                                                 @RequestParam("plot_id") Long plotId,
                                                 @RequestParam("date_from") LocalDate dateFrom,
                                                 @RequestParam("date_to") LocalDate dateTo) {
        if (workerId == null) {
            return workerAccessService.getWorkerAccessListByPlot("Trabajador", plotId, dateFrom, dateTo);
        } else {
            return workerAccessService.getWorkerAccessListById("Trabajador", workerId, dateFrom, dateTo);
        }
    }
}

