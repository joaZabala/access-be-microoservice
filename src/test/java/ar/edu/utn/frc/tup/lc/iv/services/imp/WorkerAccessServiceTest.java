package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.WorkerAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VehicleTypesEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.EmployeesAccessRepository;
import ar.edu.utn.frc.tup.lc.iv.repositories.WorkerAccessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class WorkerAccessServiceTest {

    @Mock
    private EmployeesAccessRepository employeesAccessRepository;

    @Mock
    private WorkerAccessRepository workerAccessRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TypeMap<AccessesEntity, WorkerAccessDTO> typeMap;

    @InjectMocks
    private WorkerAccessService workerAccessService;

    private AccessesEntity accessesEntity;
    private WorkerAccessDTO expectedWorkerAccessDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        VehicleTypesEntity vehicleType = new VehicleTypesEntity();
        vehicleType.setCarDescription("Sedan");

        accessesEntity = new AccessesEntity();
        accessesEntity.setSupplierEmployeeId(1L);
        accessesEntity.setVehicleType(vehicleType);

        expectedWorkerAccessDTO = new WorkerAccessDTO();
        expectedWorkerAccessDTO.setExternalId(1L);
        expectedWorkerAccessDTO.setCarDescription("Sedan");
        expectedWorkerAccessDTO.setEntryDateTime(LocalDateTime.now());
        expectedWorkerAccessDTO.setExitDateTime(LocalDateTime.now().plusHours(8));

        when(modelMapper.typeMap(AccessesEntity.class, WorkerAccessDTO.class)).thenReturn(typeMap);
        when(typeMap.addMapping(any(), any())).thenReturn(typeMap);
        when(modelMapper.map(any(AccessesEntity.class), eq(WorkerAccessDTO.class)))
                .thenReturn(expectedWorkerAccessDTO);
    }

    @Test
    void testGetWorkerAccessListById() {
        String authTypeDescription = "Full Access";
        Long workerId = 1L;
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();

        List<AccessesEntity> mockAccessesEntities = new ArrayList<>();
        mockAccessesEntities.add(accessesEntity);
        when(employeesAccessRepository.getEmployeeAccessesList(eq(authTypeDescription), eq(workerId), any(), any()))
                .thenReturn(mockAccessesEntities);

        List<WorkerAccessDTO> actualWorkerAccessDTOs = workerAccessService.getWorkerAccessListById(authTypeDescription,
                workerId, dateFrom, dateTo);

        assertEquals(1, actualWorkerAccessDTOs.size());
        assertEquals(expectedWorkerAccessDTO.getExternalId(), actualWorkerAccessDTOs.get(0).getExternalId());
        assertEquals(expectedWorkerAccessDTO.getCarDescription(), actualWorkerAccessDTOs.get(0).getCarDescription());

        verify(employeesAccessRepository, times(1)).getEmployeeAccessesList(eq(authTypeDescription), eq(workerId),
                any(), any());
    }

    @Test
    void testGetWorkerAccessListByPlot() {
        String authTypeDescription = "Site Access";
        Long plotId = 1L;
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();

        List<AccessesEntity> mockAccessesEntities = new ArrayList<>();
        mockAccessesEntities.add(accessesEntity);
        when(workerAccessRepository.getWorkerAccessesListByPlot(eq(authTypeDescription), eq(plotId), any(), any()))
                .thenReturn(mockAccessesEntities);

        List<WorkerAccessDTO> actualWorkerAccessDTOs = workerAccessService
                .getWorkerAccessListByPlot(authTypeDescription, plotId, dateFrom, dateTo);

        assertEquals(1, actualWorkerAccessDTOs.size());
        assertEquals(expectedWorkerAccessDTO.getExternalId(), actualWorkerAccessDTOs.get(0).getExternalId());
        assertEquals(expectedWorkerAccessDTO.getCarDescription(), actualWorkerAccessDTOs.get(0).getCarDescription());

        verify(workerAccessRepository, times(1)).getWorkerAccessesListByPlot(eq(authTypeDescription), eq(plotId), any(),
                any());
    }

    @Test
    void testMapWorkerAccessDTO_WithSupplierEmployeeIdNull() {
        accessesEntity.setSupplierEmployeeId(null);

        when(modelMapper.typeMap(AccessesEntity.class, WorkerAccessDTO.class)).thenReturn(typeMap);
        when(modelMapper.map(any(AccessesEntity.class), eq(WorkerAccessDTO.class)))
                .thenReturn(expectedWorkerAccessDTO);

        WorkerAccessDTO actualWorkerAccessDTO = workerAccessService.mapWorkerAccessDTO(accessesEntity);

        assertEquals(expectedWorkerAccessDTO.getExternalId(), actualWorkerAccessDTO.getExternalId());
        assertEquals(expectedWorkerAccessDTO.getCarDescription(), actualWorkerAccessDTO.getCarDescription());
    }

    @Test
    void testMapWorkerAccessDTO_WithSupplierEmployeeIdPresent() {
        accessesEntity.setSupplierEmployeeId(1L);

        WorkerAccessDTO actualWorkerAccessDTO = workerAccessService.mapWorkerAccessDTO(accessesEntity);

        assertEquals(expectedWorkerAccessDTO.getExternalId(), actualWorkerAccessDTO.getExternalId());
        assertEquals(expectedWorkerAccessDTO.getCarDescription(), actualWorkerAccessDTO.getCarDescription());
    }

    @Test
    void testMapWorkerAccessDTO() {
        WorkerAccessDTO actualWorkerAccessDTO = workerAccessService.mapWorkerAccessDTO(accessesEntity);

        assertEquals(expectedWorkerAccessDTO.getExternalId(), actualWorkerAccessDTO.getExternalId());
        assertEquals(expectedWorkerAccessDTO.getCarDescription(), actualWorkerAccessDTO.getCarDescription());
    }
}
