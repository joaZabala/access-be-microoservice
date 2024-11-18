package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.Setup.SetupDTO;

public interface ISetupService {
    /**
     * @param setup setup times
     * @return retrieves a setup config
     * */
    SetupDTO updateSetup(SetupDTO setup) throws Exception;
    /**
     * @return retrieves setup config
     * */
    SetupDTO getSetup() throws Exception;
}
