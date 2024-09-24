package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;

import java.time.LocalDate;
import java.util.List;

public interface IAccessService {
    List<AccessDTO> getAccessesList(Long authType, Long idType, LocalDate dateFrom, LocalDate dateTo);

}
