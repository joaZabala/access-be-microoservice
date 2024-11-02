package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.ActionTypes;
import ar.edu.utn.frc.tup.lc.iv.models.DocumentType;
import ar.edu.utn.frc.tup.lc.iv.models.VisitorType;
import ar.edu.utn.frc.tup.lc.iv.repositories.AccessesRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccessesServiceTest {
    @InjectMocks
    private AccessesService accessesService;
    @Mock
    private AccessesRepository accessesRepository;
    @Mock
    ModelMapper modelMapper;
    @Test
    void testGetAllExits() {
        AccessEntity access1 = new AccessEntity();
        access1.setActionDate(LocalDateTime.now().minusDays(1));
        AuthEntity auth1 = new AuthEntity();
        auth1.setCreatedUser(1L);
        access1.setAuth(auth1);

        AccessEntity access2 = new AccessEntity();
        access2.setActionDate(LocalDateTime.now());
        VisitorEntity v1 = new VisitorEntity();
        v1.setDocumentType(DocumentType.DNI);
        AuthEntity auth2 = new AuthEntity();
        auth2.setCreatedUser(1L);
        access2.setAuth(auth2);
        VisitorEntity v2 = new VisitorEntity();
        v1.setDocumentType(DocumentType.DNI);
        auth2.setVisitor(v2);
        auth1.setVisitor(v1);

        when(accessesRepository.findByAction(ActionTypes.EXIT)).thenReturn(Arrays.asList(access1, access2));

        when(modelMapper.map(any(AccessEntity.class), any())).thenAnswer(invocation -> {
            AccessEntity accessEntity = invocation.getArgument(0);
            AccessDTO accessDTO = new AccessDTO();
            accessDTO.setActionDate(accessEntity.getActionDate());
            accessDTO.setAuthorizerId(accessEntity.getAuth().getCreatedUser());
            return accessDTO;
        });

        List<AccessDTO> result = accessesService.getAllExits();
        verify(accessesRepository).findByAction(ActionTypes.EXIT);

        assertEquals(2, result.size());
        assertEquals(access2.getActionDate(), result.get(0).getActionDate());
        assertEquals(access1.getActionDate(), result.get(1).getActionDate());
    }

    @Test
    void testGetAllEntries() {
        AccessEntity access1 = new AccessEntity();
        access1.setActionDate(LocalDateTime.now().minusDays(1));
        AuthEntity auth1 = new AuthEntity();
        auth1.setCreatedUser(1L);
        access1.setAuth(auth1);

        AccessEntity access2 = new AccessEntity();
        access2.setActionDate(LocalDateTime.now());
        VisitorEntity v1 = new VisitorEntity();
        v1.setDocumentType(DocumentType.DNI);
        AuthEntity auth2 = new AuthEntity();
        auth2.setCreatedUser(1L);
        access2.setAuth(auth2);
        VisitorEntity v2 = new VisitorEntity();
        v1.setDocumentType(DocumentType.DNI);
        auth2.setVisitor(v2);
        auth1.setVisitor(v1);

        when(accessesRepository.findByAction(ActionTypes.ENTRY)).thenReturn(Arrays.asList(access1, access2));

        when(modelMapper.map(any(AccessEntity.class), any())).thenAnswer(invocation -> {
            AccessEntity accessEntity = invocation.getArgument(0);
            AccessDTO accessDTO = new AccessDTO();
            accessDTO.setActionDate(accessEntity.getActionDate());
            accessDTO.setAuthorizerId(accessEntity.getAuth().getCreatedUser());
            return accessDTO;
        });

        List<AccessDTO> result = accessesService.getAllEntries();
        verify(accessesRepository).findByAction(ActionTypes.ENTRY);

        assertEquals(2, result.size());
        assertEquals(access2.getActionDate(), result.get(0).getActionDate());
        assertEquals(access1.getActionDate(), result.get(1).getActionDate());
    }
    @Test
    void testGetByTypeAndExternalId() {
        AccessEntity access1 = new AccessEntity();
        access1.setActionDate(LocalDateTime.now().minusDays(1));
        AuthEntity auth1 = new AuthEntity();
        auth1.setCreatedUser(1L);
        access1.setAuth(auth1);

        AccessEntity access2 = new AccessEntity();
        access2.setActionDate(LocalDateTime.now());
        VisitorEntity v1 = new VisitorEntity();
        v1.setDocumentType(DocumentType.DNI);
        AuthEntity auth2 = new AuthEntity();
        auth2.setCreatedUser(1L);
        access2.setAuth(auth2);
        VisitorEntity v2 = new VisitorEntity();
        auth2.setExternalID(1L);
        v1.setDocumentType(DocumentType.DNI);
        auth2.setVisitor(v2);
        auth2.setVisitorType(VisitorType.VISITOR);
        auth1.setVisitor(v1);

        when(accessesRepository.findByAuthVisitorTypeAndAuthExternalID(VisitorType.VISITOR, 1L)).thenReturn(Arrays.asList(access2));

        when(modelMapper.map(any(AccessEntity.class), any())).thenAnswer(invocation -> {
            AccessEntity accessEntity = invocation.getArgument(0);
            AccessDTO accessDTO = new AccessDTO();
            accessDTO.setActionDate(accessEntity.getActionDate());
            accessDTO.setAuthorizerId(accessEntity.getAuth().getCreatedUser());
            return accessDTO;
        });

        List<AccessDTO> result = accessesService.getAllAccessByTypeAndExternalID(VisitorType.VISITOR, 1L);

        assertEquals(1, result.size());
        assertEquals(access2.getActionDate(), result.get(0).getActionDate());
    }

    @Test
    void testGetByAccessesType() {

        AccessEntity access2 = new AccessEntity();
        access2.setActionDate(LocalDateTime.now());
        VisitorEntity v1 = new VisitorEntity();
        v1.setDocumentType(DocumentType.DNI);
        AuthEntity auth2 = new AuthEntity();
        auth2.setCreatedUser(1L);
        access2.setAuth(auth2);
        VisitorEntity v2 = new VisitorEntity();
        auth2.setExternalID(1L);
        v1.setDocumentType(DocumentType.DNI);
        auth2.setVisitor(v2);
        auth2.setVisitorType(VisitorType.VISITOR);

        when(accessesRepository.findByAuthVisitorType(VisitorType.VISITOR)).thenReturn(Arrays.asList(access2));

        when(modelMapper.map(any(AccessEntity.class), any())).thenAnswer(invocation -> {
            AccessEntity accessEntity = invocation.getArgument(0);
            AccessDTO accessDTO = new AccessDTO();
            accessDTO.setActionDate(accessEntity.getActionDate());
            accessDTO.setAuthorizerId(accessEntity.getAuth().getCreatedUser());
            return accessDTO;
        });

        List<AccessDTO> result = accessesService.getAllAccessByType(VisitorType.VISITOR);

        assertEquals(1, result.size());
        assertEquals(access2.getActionDate(), result.get(0).getActionDate());
    }

    @Test
    void canDoActionSuccess() {

        AccessEntity access2 = new AccessEntity();
        access2.setVehicleReg("XXX - 123");
        access2.setAction(ActionTypes.ENTRY);
        access2.setActionDate(LocalDateTime.now());
        VisitorEntity v1 = new VisitorEntity();
        v1.setDocumentType(DocumentType.DNI);
        AuthEntity auth2 = new AuthEntity();
        auth2.setCreatedUser(1L);
        access2.setAuth(auth2);
        VisitorEntity v2 = new VisitorEntity();
        auth2.setExternalID(1L);
        v1.setDocumentType(DocumentType.DNI);
        auth2.setVisitor(v2);
        auth2.setVisitorType(VisitorType.VISITOR);

        when(accessesRepository.findByVehicleReg("XXX - 123")).thenReturn(List.of(access2));

        Boolean result = accessesService.canDoAction("XXX - 123", ActionTypes.EXIT);

        assertTrue(result);
    }

    @Test
    void canDoActionFail() {

        AccessEntity access2 = new AccessEntity();
        access2.setVehicleReg("XXX - 123");
        access2.setAction(ActionTypes.ENTRY);
        access2.setActionDate(LocalDateTime.now());
        VisitorEntity v1 = new VisitorEntity();
        v1.setDocumentType(DocumentType.DNI);
        AuthEntity auth2 = new AuthEntity();
        auth2.setCreatedUser(1L);
        access2.setAuth(auth2);
        VisitorEntity v2 = new VisitorEntity();
        auth2.setExternalID(1L);
        v1.setDocumentType(DocumentType.DNI);
        auth2.setVisitor(v2);
        auth2.setVisitorType(VisitorType.VISITOR);

        when(accessesRepository.findByVehicleReg("XXX - 123")).thenReturn(List.of(access2));

        Boolean result = accessesService.canDoAction("XXX - 123", ActionTypes.ENTRY);

        assertFalse(result);
    }

    @Test
    void canDoActionNull() {

        when(accessesRepository.findByVehicleReg("XXX - 123")).thenReturn(List.of());

        Boolean result = accessesService.canDoAction("XXX - 123", ActionTypes.ENTRY);

        assertTrue(result);
    }

    @Test
    void registerAccess() {

        AccessEntity access2 = new AccessEntity();
        access2.setVehicleReg("XXX - 123");
        access2.setAction(ActionTypes.ENTRY);
        access2.setActionDate(LocalDateTime.now());
        AuthEntity auth2 = new AuthEntity();
        auth2.setCreatedUser(1L);
        access2.setAuth(auth2);
        VisitorEntity v2 = new VisitorEntity();
        auth2.setExternalID(1L);
        auth2.setVisitor(v2);
        auth2.setVisitorType(VisitorType.VISITOR);

        when(modelMapper.map(any(AccessEntity.class), any())).thenAnswer(invocation -> {
            AccessEntity accessEntity = invocation.getArgument(0);
            AccessDTO accessDTO = new AccessDTO();
            accessDTO.setActionDate(accessEntity.getActionDate());
            accessDTO.setAuthorizerId(accessEntity.getAuth().getCreatedUser());
            return accessDTO;
        });
        when(accessesRepository.save(any(AccessEntity.class))).thenReturn(access2);

        accessesService.registerAccess(access2);
        verify(accessesRepository).save(access2);
    }
}
