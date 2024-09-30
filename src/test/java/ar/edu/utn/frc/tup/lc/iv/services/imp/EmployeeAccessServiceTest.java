package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.EmployeeAccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.repositories.EmployeesAccessRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeAccessServiceTest {

    @Mock
    EmployeesAccessRepository employeesAccessRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    EmployeeAccessService employeeAccessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getEmployeeAccessListTest() {
        AccessesEntity accessesEntity = new AccessesEntity();
        accessesEntity.setEntryDate(LocalDateTime.of(2024, 9, 29, 8, 30));
        accessesEntity.setExitDate(LocalDateTime.of(2024, 9, 29, 17, 30));
        accessesEntity.setComments("Comentario de ejemplo");

        List<AccessesEntity> accessesEntityList = new ArrayList<>();
        accessesEntityList.add(accessesEntity);

        EmployeeAccessDTO employeeAccessDTO = new EmployeeAccessDTO();
        employeeAccessDTO.setEntryDateTime(LocalDateTime.of(2024, 9, 29, 8, 30));
        employeeAccessDTO.setExitDateTime(LocalDateTime.of(2024, 9, 29, 17, 30));
        employeeAccessDTO.setComments("Comentario 2 de ejemplo");

        when(employeesAccessRepository.getEmployeeAccessesList(any(), any(), any(), any()))
                .thenReturn(accessesEntityList);

        when(modelMapper.map(any(AccessesEntity.class), eq(EmployeeAccessDTO.class)))
                .thenReturn(employeeAccessDTO);

        List<EmployeeAccessDTO> result = employeeAccessService.getEmployeeAccessList(
                "Trabajador", 123L, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));

        verify(employeesAccessRepository).getEmployeeAccessesList(any(), any(), any(), any());

        verify(modelMapper).map(accessesEntity, EmployeeAccessDTO.class);

        assertEquals(1, result.size());
        assertEquals(employeeAccessDTO.getEntryDateTime(), result.get(0).getEntryDateTime());
        assertEquals(employeeAccessDTO.getExitDateTime(), result.get(0).getExitDateTime());
        assertEquals(employeeAccessDTO.getComments(), result.get(0).getComments());
    }
}