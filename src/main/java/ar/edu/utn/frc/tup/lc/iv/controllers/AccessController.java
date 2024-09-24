package ar.edu.utn.frc.tup.lc.iv.controllers;


import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AuthorizedDTO;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AccessesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/access")
public class AccessController {

    @Autowired
    private AccessesService accessesService;

    @GetMapping("/{accId}")
    public AccessDTO getAccessById(@PathVariable Long accId) {

       return accessesService.getAccessFromID(accId);
    }

    @GetMapping("/getVisitAccessesById")
    public List<AccessDTO> getVisitAccessesById(@RequestHeader("owner") Long owner,
                                                @RequestHeader("visitorId") Long visitorId,
                                                @RequestHeader("dateFrom") LocalDate dateFrom,
                                                @RequestHeader("dateTo") LocalDate dateTo)  {

        return accessesService.getVisitAccessesByID(owner, visitorId, dateFrom, dateTo);
    }

    @GetMapping("/getVisitAccesses")
    public List<AccessDTO> getVisitAccesses(@RequestHeader("owner") Long owner,
                                                @RequestHeader("dateFrom") LocalDate dateFrom,
                                                @RequestHeader("dateTo") LocalDate dateTo)  {

        return accessesService.getVisitAccesses(owner, dateFrom, dateTo);
    }

    @GetMapping("/getEmployeeAccesses")
    public List<AccessDTO> getEmployeeAccesses(@RequestHeader("employee_id") Long employee_id,
                                                @RequestHeader("dateFrom") LocalDate dateFrom,
                                                @RequestHeader("dateTo") LocalDate dateTo) {

        return accessesService.getExternalAccessList("Empleado", employee_id, dateFrom, dateTo);
    }

}
