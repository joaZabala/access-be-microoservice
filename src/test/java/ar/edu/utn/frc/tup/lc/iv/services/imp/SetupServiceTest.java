package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.Setup.SetupDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.SetupEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.SetupRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SetupServiceTest {
    @Mock
    private SetupRepository setupRepository;

    @InjectMocks
    private SetupService setupService;

    private SetupEntity setupEntity;
    private SetupDTO setupDTO;

    @BeforeEach
    public void setUp() {
        setupEntity = new SetupEntity();
        setupEntity.setTargetTime(LocalTime.of(17, 0));
        setupEntity.setTimeOfGrace(LocalTime.of(15, 0));

        setupDTO = new SetupDTO(LocalTime.of(15, 0), LocalTime.of(17, 0));
    }

    @Test
    void testUpdateSetup() {
        SetupEntity setupEntity = new SetupEntity();
        setupEntity.setTimeOfGrace(LocalTime.of(10, 0));
        setupEntity.setTargetTime(LocalTime.of(18, 0));

        SetupDTO setupDTO = new SetupDTO(LocalTime.of(9, 0), LocalTime.of(17, 0));

        when(setupRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(setupEntity));

        SetupDTO result = setupService.updateSetup(setupDTO);

        assertNotNull(result);
        assertEquals(result.getTimeOfGrace(), LocalTime.of(9, 0));
        assertEquals(result.getTargetTime(), LocalTime.of(17, 0));

        verify(setupRepository, times(1)).save(setupEntity);
    }

    @Test
    void testGetSetup() throws Exception {
        SetupEntity setupEntity = new SetupEntity();
        setupEntity.setTimeOfGrace(LocalTime.of(10, 0));
        setupEntity.setTargetTime(LocalTime.of(18, 0));

        when(setupRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(setupEntity));

        SetupDTO result = setupService.getSetup();

        assertNotNull(result);
        assertEquals(result.getTimeOfGrace(), LocalTime.of(10, 0));
        assertEquals(result.getTargetTime(), LocalTime.of(18, 0));
    }
}
