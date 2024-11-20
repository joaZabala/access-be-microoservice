package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.Setup.SetupDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.SetupEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.SetupRepository;
import ar.edu.utn.frc.tup.lc.iv.services.ISetupService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
/**
 * Service for setup entity.
 * */
@Service
public class SetupService implements ISetupService {
    /**
     * repository of authorizations.
     */
    @Autowired
    private SetupRepository setupRepository;
    /**
     * @param setup setup times
     * @return retrieves a setup config
     * */
    @Override
    public SetupDTO updateSetup(SetupDTO setup) {

        Optional<SetupEntity> optionalSetup = setupRepository.findFirstByOrderByIdAsc();

        if (optionalSetup.isEmpty()) {
            throw new EntityNotFoundException("La configuracion inicial no está cargada");
        }

        SetupEntity setupEntity = optionalSetup.get();
        setupEntity.setTargetTime(setup.getTargetTime());
        setupEntity.setTimeOfGrace(setup.getTimeOfGrace());
        setupRepository.save(setupEntity);

        return new SetupDTO(setupEntity.getTimeOfGrace(), setupEntity.getTargetTime());
    }
    /**
     * @return retrieves setup config
     * */
    @Override
    public SetupDTO getSetup() {
        Optional<SetupEntity> optionalSetup = setupRepository.findFirstByOrderByIdAsc();

        if (optionalSetup.isEmpty()) {
            throw new EntityNotFoundException("La configuracion inicial no está cargada");
        }
        SetupEntity setupEntity = optionalSetup.get();

        return new SetupDTO(setupEntity.getTimeOfGrace(), setupEntity.getTargetTime());
    }
}
