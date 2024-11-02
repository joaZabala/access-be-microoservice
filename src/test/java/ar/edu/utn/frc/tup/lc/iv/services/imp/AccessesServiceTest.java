package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.repositories.AccessesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccessesServiceTest {
    @MockBean
    private AccessesService accessesService;
    @MockBean
    private AccessesRepository accessesRepository;
    @Autowired
    ModelMapper modelMapper;
    @Test
    void testGetAllExits() {
        AccessEntity access1 = new AccessEntity();
        access1.setActionDate(LocalDateTime.now().minusDays(1));
        access1.setAction(ActionTypes.EXIT);

        AccessEntity access2= new AccessEntity();
        access2.setActionDate(LocalDateTime.now());
        access2.setAction(ActionTypes.EXIT);

        when(accessesRepository.findByAction(ActionTypes.EXIT)).thenReturn(Arrays.asList(access1, access2));

        List<AccessDTO> result = accessesService.getAllExits();

        assertEquals(2, result.size());
        assertEquals(access2.getActionDate(), result.get(0).getActionDate());
        assertEquals(access1.getActionDate(), result.get(1).getActionDate());
    }
}
