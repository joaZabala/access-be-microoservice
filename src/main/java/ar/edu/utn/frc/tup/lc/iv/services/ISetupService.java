package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.Setup.SetupDTO;

/**
 * Service for setup entity.
 * */
public interface ISetupService {
    /**
     * @param setup setup times
     * @return retrieves a setup config
     * */
    SetupDTO updateSetup(SetupDTO setup);
    /**
     * @return retrieves setup config
     * */
    SetupDTO getSetup();
}
