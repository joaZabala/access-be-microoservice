package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.WorkerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.SupplierAccessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class SupplierAccessServiceTest {

    @Mock
    SupplierAccessRepository supplierAccessRepository;

    @Mock
    WorkerAccessService workerAccessService;

    @InjectMocks
    SupplierAccessService supplierAccessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSupplierAccessListTest() {
        AccessesEntity accessesEntity = new AccessesEntity();
        accessesEntity.setEntryDate(LocalDateTime.of(2024, 9, 29, 18, 5));
        accessesEntity.setExitDate(LocalDateTime.of(2024, 9, 29, 18, 23));
        accessesEntity.setVehicleReg("ABC123");
        accessesEntity.setVehicleDescription("Camioneta Roja");

        List<AccessesEntity> accessesEntityList = new ArrayList<>();
        accessesEntityList.add(accessesEntity);

        WorkerAccessDTO workerAccessDTO = new WorkerAccessDTO();
        workerAccessDTO.setExternalId(1L);
        workerAccessDTO.setEntryDateTime(LocalDateTime.of(2024, 9, 29, 18, 5));
        workerAccessDTO.setExitDateTime(LocalDateTime.of(2024, 9, 29, 18, 23));

        when(supplierAccessRepository.getSupplierAccessesList(any(), any(), any(), any()))
                .thenReturn(accessesEntityList);

        when(workerAccessService.mapWorkerAccessDTO(any(AccessesEntity.class)))
                .thenReturn(workerAccessDTO);

        List<WorkerAccessDTO> result = supplierAccessService.getSupplierAccessList(
                "Proveedor", 123L, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));

        verify(supplierAccessRepository).getSupplierAccessesList(any(), any(), any(), any());

        verify(workerAccessService).mapWorkerAccessDTO(accessesEntity);

        assertEquals(1, result.size());
        assertEquals(workerAccessDTO.getExternalId(), result.get(0).getExternalId());
        assertEquals(workerAccessDTO.getEntryDateTime(), result.get(0).getEntryDateTime());
        assertEquals(workerAccessDTO.getExitDateTime(), result.get(0).getExitDateTime());
    }
}