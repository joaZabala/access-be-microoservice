package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.clients.UserDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserRestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.visitor.VisitorRequest;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.repositories.VisitorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class VisitorServiceTest {

    @MockBean
    private VisitorRepository visitorRepository;

    @MockBean
    private UserRestClient userRestClient;

    @SpyBean
    private VisitorService visitorService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllVisitorsTest() {
        VisitorEntity visitorEntity = new VisitorEntity(
                1L, "juan", "Perez", DocumentType.DNI, 40252203L, LocalDate.now(), true
        );
        VisitorEntity visitorEntity1 = new VisitorEntity(
                2L, "joaquin", "Perez", DocumentType.DNI, 40252255L, LocalDate.now(), true
        );
        List<VisitorEntity> visitorList = Arrays.asList(visitorEntity, visitorEntity1);

        Page<VisitorEntity> page = new PageImpl<>(visitorList);

        when(visitorRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(page);

        PaginatedResponse<VisitorDTO> result = visitorService.getAllVisitors(0, 10, new VisitorFilter());

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void getAllVisitorsEmptyTest() {
        Page<VisitorEntity> emptyPage = new PageImpl<>(new ArrayList<>());
        when(visitorRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(emptyPage);

        PaginatedResponse<VisitorDTO> result = visitorService.getAllVisitors(0, 10, new VisitorFilter());

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }


    @Test
    void saverOrUpdateVisitorExistingVisitorTest() {
        //given
        VisitorRequest visitorRequest =
                new VisitorRequest("joaquin","zabala", DocumentType.DNI,12345678L,LocalDate.of(2005,3,17));

        //when
        VisitorEntity visitorEntity = new VisitorEntity(1L, "","",DocumentType.DNI,0L,LocalDate.now(),false);
        when(visitorRepository.findByDocNumber(12345678L)).thenReturn(visitorEntity);

        UserDto userDto = new UserDto(1L,"Carlos Sainz");
        when(userRestClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userDto));

        VisitorEntity visitorEntitySave = new VisitorEntity(1L, "joaquin","zabala",DocumentType.DNI,12345678L,LocalDate.of(2005,3,17),true);
        when(visitorRepository.save(any(VisitorEntity.class))).thenReturn(visitorEntitySave);

        //then
        VisitorDTO visitorDTOExpected =
                new VisitorDTO(1L,"joaquin","zabala",DocumentType.DNI,12345678L,LocalDate.of(2005,3,17),null, true);

        VisitorDTO visitorDTOResult = visitorService.saveOrUpdateVisitor(visitorRequest , null);

        assertEquals(visitorDTOExpected , visitorDTOResult);

    }

    @Test
    void saveOrUpdateVisitorNoExistVisitorTest() {
        //given
        VisitorRequest visitorRequest =
                new VisitorRequest("joaquin","zabala", DocumentType.DNI,12345678L,LocalDate.of(2005,3,17));

        //when
        when(visitorRepository.findByDocNumber(12345678L)).thenReturn(null);

        UserDto userDto = new UserDto(1L,"Carlos Sainz");
        when(userRestClient.getUserById(1L)).thenReturn(ResponseEntity.ok(userDto));

        VisitorEntity visitorEntitySave = new VisitorEntity(1L, "joaquin","zabala",DocumentType.DNI,12345678L,LocalDate.of(2005,3,17),true);
        when(visitorRepository.save(any(VisitorEntity.class))).thenReturn(visitorEntitySave);

        //then
        VisitorDTO visitorDTOExpected =
                new VisitorDTO(1L, "joaquin","zabala",DocumentType.DNI,12345678L,LocalDate.of(2005,3,17),null,true);

        VisitorDTO visitorDTOResult = visitorService.saveOrUpdateVisitor(visitorRequest , null);

        assertEquals(visitorDTOExpected , visitorDTOResult);

    }

    @Test
    void getBydocNumberTest() {
        VisitorEntity visitorEntity = new VisitorEntity(1L, "juan", "perez",DocumentType.DNI, 40252203L, LocalDate.now(), true);
        when(visitorRepository.findByDocNumber(40252203L)).thenReturn(visitorEntity);

        VisitorDTO visitorDTO = visitorService.getVisitorByDocNumber(40252203L);
        assertEquals(visitorEntity.getDocNumber(), visitorDTO.getDocNumber());
    }

    @Test
    void getBydocNumberNoExistTest() {
        // when
        when(visitorRepository.findByDocNumber(40252203L)).thenReturn(null);

        // then
        VisitorDTO result = visitorService.getVisitorByDocNumber(40252203L);
        assertNull(result);
        verify(visitorRepository).findByDocNumber(40252203L);
    }

    @Test
    void getByIdTest() {
        VisitorEntity visitorEntity =
                new VisitorEntity(1L, "juan", "perez", DocumentType.DNI,40252203L, LocalDate.now(), true);
        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitorEntity));

        VisitorDTO visitorDTO = visitorService.getVisitorById(1L);

        assertEquals(visitorEntity.getDocNumber(), visitorDTO.getDocNumber());
        assertEquals(visitorEntity.getName(), "juan");
    }

    @Test
    void getByIdNoExistTest() {
        when(visitorRepository.findById(100L)).thenReturn(Optional.empty());

        EntityNotFoundException exception=
                assertThrows(EntityNotFoundException.class,
                        () -> visitorService.getVisitorById(100L));

        assertEquals("No existe el visitante con el id 100", exception.getMessage());
    }

    @Test
    void deleteVisitorTest() {

        VisitorEntity visitorEntity =
                new VisitorEntity(1L, "juan", "perez", DocumentType.DNI,40252203L, LocalDate.now(), true);



        when(visitorRepository.findById(1L)).thenReturn(Optional.of(visitorEntity));

        VisitorEntity visitorEntitySave =
                new VisitorEntity(1L, "juan", "perez", DocumentType.DNI,40252203L, LocalDate.now(), false);
        when(visitorRepository.save(any(VisitorEntity.class))).thenReturn(visitorEntitySave);

        VisitorDTO visitorDTO = visitorService.deleteVisitor(1L);

        assertEquals(visitorEntity.getDocNumber(), visitorDTO.getDocNumber());
        assertFalse(visitorDTO.isActive());
    }

    @Test
    void deleteVisitorNoExistTest() {

        when(visitorRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception =
                Assertions.assertThrows(EntityNotFoundException.class, () -> {
            visitorService.deleteVisitor(1L);
        });

        assertEquals("No existe el visitante con el id 1", exception.getMessage());

    }

    @Test
    void saveOrUpdateVisitorWithExistingId() {
        Long visitorId = 1L;
        VisitorRequest visitorRequest = new VisitorRequest(
                "joaquin",
                "zabala",
                DocumentType.DNI,
                12345678L,
                LocalDate.of(2005,3,17)
        );

        VisitorEntity existingVisitor = new VisitorEntity(
                visitorId,
                "old name",
                "old lastname",
                DocumentType.DNI,
                12345678L,
                LocalDate.now(),
                true
        );

        when(visitorRepository.findById(visitorId)).thenReturn(Optional.of(existingVisitor));
        when(visitorRepository.save(any(VisitorEntity.class))).thenReturn(existingVisitor);

        VisitorDTO result = visitorService.saveOrUpdateVisitor(visitorRequest, visitorId);

        assertNotNull(result);
        assertEquals(visitorRequest.getName(), existingVisitor.getName());
        assertEquals(visitorRequest.getLastName(), existingVisitor.getLastName());
    }

    @Test
    void saveOrUpdateVisitorWithInvalidId() {
        Long invalidId = 999L;
        VisitorRequest visitorRequest = new VisitorRequest(
                "joaquin",
                "zabala",
                DocumentType.DNI,
                12345678L,
                LocalDate.of(2005,3,17)
        );

        when(visitorRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                visitorService.saveOrUpdateVisitor(visitorRequest, invalidId)
        );
    }

    @Test
    void updateExistingVisitorKeepingActiveStatus() {
        Long visitorId = 1L;
        VisitorRequest visitorRequest = new VisitorRequest(
                "new name",
                "new lastname",
                DocumentType.DNI,
                12345678L,
                LocalDate.of(2005,3,17)
        );

        VisitorEntity existingVisitor = new VisitorEntity(
                visitorId,
                "old name",
                "old lastname",
                DocumentType.DNI,
                12345678L,
                LocalDate.now(),
                true
        );

        when(visitorRepository.findById(visitorId)).thenReturn(Optional.of(existingVisitor));
        when(visitorRepository.save(any(VisitorEntity.class))).thenReturn(existingVisitor);

        VisitorDTO result = visitorService.saveOrUpdateVisitor(visitorRequest, visitorId);

        assertNotNull(result);
        assertTrue(result.isActive());
        assertEquals("new name", existingVisitor.getName());
        assertEquals("new lastname", existingVisitor.getLastName());
    }


    @Test
    void getVisitorByDocNumberWithNonExistentDoc() {
        // given
        Long nonExistentDoc = 99999999L;

        // when
        when(visitorRepository.findByDocNumber(nonExistentDoc)).thenReturn(null);

        // then
        VisitorDTO result = visitorService.getVisitorByDocNumber(nonExistentDoc);
        assertNull(result);
        verify(visitorRepository).findByDocNumber(nonExistentDoc);
    }
}