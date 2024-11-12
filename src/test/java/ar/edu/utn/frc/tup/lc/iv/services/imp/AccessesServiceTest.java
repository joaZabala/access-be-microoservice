package ar.edu.utn.frc.tup.lc.iv.services.imp;

import ar.edu.utn.frc.tup.lc.iv.clients.UserDetailDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserDto;
import ar.edu.utn.frc.tup.lc.iv.clients.UserRestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.EntryReport.EntryReport;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.PaginatedResponse;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.AccessesFilter;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.accesses.DataType;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorized.AccessDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.dashboard.DashboardDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.AccessEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import ar.edu.utn.frc.tup.lc.iv.models.*;
import ar.edu.utn.frc.tup.lc.iv.repositories.AccessesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccessesServiceTest {
    @SpyBean
    private AccessesService accessesService;
    @MockBean
    private AccessesRepository accessesRepository;
    @MockBean
    private UserRestClient userRestClient;

    private final String userService = "http://host.docker.internal:8283/users";

    @MockBean
    private RestTemplate restTemplate;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
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

        when(accessesRepository.save(any(AccessEntity.class))).thenReturn(access2);

        accessesService.registerAccess(access2);
        verify(accessesRepository).save(access2);
    }

    @Test
    void testGetAllAccess() {
        AccessesFilter filter = new AccessesFilter();
        filter.setFromDate(null);
        filter.setToDate(null);
        filter.setTextFilter(null);
        filter.setExternalId(null);
        filter.setActionType(ActionTypes.ENTRY);
        filter.setDocumentType(DocumentType.DNI);
        filter.setVehicleType(VehicleTypes.CAR);
        filter.setVisitorType(VisitorType.VISITOR);

        AccessEntity accessEntity = new AccessEntity();
        AuthEntity auth = new AuthEntity();
        VisitorEntity v2 = new VisitorEntity();
        v2.setVisitorId(1L);
        auth.setAuthId(1L);
        auth.setCreatedUser(1L);
        v2.setCreatedUser(1L);
        accessEntity.setVehicleReg("XXXXXX");
        accessEntity.setCreatedUser(1L);
        accessEntity.setVehicleType(VehicleTypes.CAR);
        v2.setDocumentType(DocumentType.DNI);
        auth.setVisitorType(VisitorType.VISITOR);
        accessEntity.setAction(ActionTypes.ENTRY);
        auth.setVisitor(v2);
        accessEntity.setAuth(auth);

        Page<AccessEntity> accessPage = new PageImpl<>(List.of(accessEntity));

        UserDetailDto userDetailDto = new UserDetailDto();
        userDetailDto.setId(1L);
        userDetailDto.setFirstName("John");
        userDetailDto.setLastName("Doe");


        when(accessesRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(accessPage);
        when(userRestClient.getUsersByIds(List.of(1L))).thenReturn(List.of(userDetailDto));

        PaginatedResponse<AccessDTO> response = accessesService.getAllAccess(filter, 0, 10);

        assertEquals(1, response.getItems().size());
        AccessDTO accessDTO = response.getItems().get(0);
    }

    @Test
    public void testGetHourlyInfo() {
        LocalDateTime from = LocalDateTime.of(2024, 11, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 11, 2, 23, 59);
        List<Object[]> mockResults = Arrays.asList(
                new Object[]{"00:00", 10L, 0L},
                new Object[]{"01:00", 5L, 0L}
        );
        when(accessesRepository.findAccessCountsByHourNative(from, to)).thenReturn(mockResults);

        List<DashboardDTO> result = accessesService.getHourlyInfo(from, to);

        assertEquals(24, result.size());

        assertEquals("00:00", result.get(0).getKey());
        assertEquals(10L, result.get(0).getValue());
        assertEquals("01:00", result.get(1).getKey());
        assertEquals(5L, result.get(1).getValue());

        assertEquals("02:00", result.get(2).getKey());
        assertEquals(0L, result.get(2).getValue());
        assertEquals("03:00", result.get(3).getKey());
        assertEquals(0L, result.get(3).getValue());

        verify(accessesRepository).findAccessCountsByHourNative(from, to);
    }
    @Test
    public void testGetDayOfWeekInfo() {
        LocalDateTime from = LocalDateTime.of(2024, 11, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 11, 2, 23, 59);

        List<Object[]> mockResults = Arrays.asList(
                new Object[]{5, 10L, 0L},
                new Object[]{6, 5L, 0L}
        );

        when(accessesRepository.findAccessCountsByDayOfWeekNative(from, to)).thenReturn(mockResults);

        List<DashboardDTO> result = accessesService.getDayOfWeekInfo(from, to);

        assertEquals(7, result.size());
        assertEquals(DayOfWeek.FRIDAY.toString(), result.get(4).getKey());
        assertEquals(10L, result.get(4).getValue());
        assertEquals(DayOfWeek.SATURDAY.toString(), result.get(5).getKey());
        assertEquals(5L, result.get(5).getValue());

        verify(accessesRepository).findAccessCountsByDayOfWeekNative(from, to);
    }

    @Test
    public void testGetInconsistentAccessCount() {
        LocalDateTime from = LocalDateTime.of(2024, 11, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 11, 2, 23, 59);
        VisitorType visitorType = VisitorType.VISITOR;

        when(accessesRepository.findAccessInconsistentCounts(from, to, visitorType)).thenReturn(5L);

        Long result = accessesService.getInconsistentAccessCount(from, to, visitorType);

        assertEquals(5L, result);
        verify(accessesRepository).findAccessInconsistentCounts(from, to, visitorType);
    }

    @Test
    public void testGetAccessByDate() {
        LocalDate dateFrom = LocalDate.of(2024, 1, 1);
        LocalDate dateTo = LocalDate.of(2024, 1, 2);

        EntryReport expectedReport = new EntryReport();
        expectedReport.setEntryCount(10L);
        expectedReport.setExitCount(5L);

        when(accessesRepository.countEntriesAndExitsBetweenDates(
                dateFrom.atStartOfDay(),
                dateTo.atTime(LocalTime.MAX)
        )).thenReturn(expectedReport);

        EntryReport result = accessesService.getAccessByDate(dateFrom, dateTo);

        assertNotNull(result);
        assertEquals(expectedReport.getEntryCount(), result.getEntryCount());
        assertEquals(expectedReport.getExitCount(), result.getExitCount());

        verify(accessesRepository).countEntriesAndExitsBetweenDates(
                dateFrom.atStartOfDay(),
                dateTo.atTime(LocalTime.MAX)
        );
    }
    @Test
    public void testGetAccessesByVisitor() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 2, 23, 59);

        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{"VISITOR", 10L, 5L});

        when(accessesRepository.findAccessCountsByVisitorType(eq(from), eq(to)))
                .thenReturn(mockResults);

        List<DashboardDTO> result = accessesService.getAccessesByVisitor(from, to);

        assertNotNull(result);
        assertEquals(1, result.size());

        DashboardDTO dto = result.get(0);
        assertEquals("VISITOR", dto.getKey());
        assertEquals(10L, dto.getValue());
        assertEquals(5L, dto.getSecondaryValue());

        verify(accessesRepository).findAccessCountsByVisitorType(from, to);
    }

    @Test
    public void testGetAccessGroupedByDay() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 2, 23, 59);
        VisitorType visitorType = VisitorType.VISITOR;
        ActionTypes actionType = ActionTypes.ENTRY;

        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{"2024-01-01", 5L});
        mockResults.add(new Object[]{"2024-01-02", 8L});

        when(accessesRepository.findAccessCountsByGroup(
                eq(from), eq(to), eq(visitorType), eq(actionType), eq("%Y-%m-%d")))
                .thenReturn(mockResults);

        List<DashboardDTO> result = accessesService.getAccessGrouped(
                from, to, visitorType, actionType, GroupByPeriod.DAY, DataType.ALL
        );

        assertEquals(2, result.size());
        assertEquals("2024-01-01", result.get(0).getKey());
        assertEquals(5L, result.get(0).getValue());
        assertEquals("2024-01-02", result.get(1).getKey());
        assertEquals(8L, result.get(1).getValue());
    }

    @Test
    public void testGetAccessGroupedWithInconsistencies() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 2, 23, 59);
        VisitorType visitorType = VisitorType.VISITOR;
        ActionTypes actionType = ActionTypes.ENTRY;

        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{"2024-01-01", 3L});

        when(accessesRepository.findInconsistentAccessCountsByGroup(
                eq(from), eq(to), eq(visitorType), eq(actionType), eq("%Y-%m-%d")))
                .thenReturn(mockResults);

        List<DashboardDTO> result = accessesService.getAccessGrouped(
                from, to, visitorType, actionType, GroupByPeriod.DAY, DataType.INCONSISTENCIES
        );

        assertEquals(2, result.size());
        assertEquals("2024-01-01", result.get(0).getKey());
        assertEquals(3L, result.get(0).getValue());
    }

    @Test
    public void testGetAccessGroupedWithLateData() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 2, 23, 59);
        VisitorType visitorType = VisitorType.VISITOR;
        ActionTypes actionType = ActionTypes.ENTRY;

        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{"2024-01-01", 2L});

        when(accessesRepository.findLateAccessCountsByGroup(
                eq(from), eq(to), eq(visitorType), eq(actionType), eq("%Y-%m-%d")))
                .thenReturn(mockResults);

        List<DashboardDTO> result = accessesService.getAccessGrouped(
                from, to, visitorType, actionType, GroupByPeriod.DAY, DataType.LATE
        );

        assertEquals(2, result.size());
        assertEquals("2024-01-01", result.get(0).getKey());
        assertEquals(2L, result.get(0).getValue());
    }

    @Test
    public void testGetAccessGroupedByWeek() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 7, 23, 59);
        VisitorType visitorType = VisitorType.VISITOR;
        ActionTypes actionType = ActionTypes.ENTRY;

        List<Object[]> mockResults = new ArrayList<>();
        mockResults.add(new Object[]{"2024-01", 15L});

        when(accessesRepository.findAccessCountsByGroup(
                eq(from), eq(to), eq(visitorType), eq(actionType), eq("%Y-%u")))
                .thenReturn(mockResults);

        List<DashboardDTO> result = accessesService.getAccessGrouped(
                from, to, visitorType, actionType, GroupByPeriod.WEEK, DataType.ALL
        );

        assertFalse(result.isEmpty());
        assertEquals("2024-01", result.get(0).getKey());
        assertEquals(15L, result.get(0).getValue());
    }

    @Test
    public void testGetAccessGroupedInvalidPeriod() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 2, 23, 59);
        VisitorType visitorType = VisitorType.VISITOR;
        ActionTypes actionType = ActionTypes.ENTRY;
        GroupByPeriod invalidPeriod = null;

        assertThrows(NullPointerException.class, () -> {
            accessesService.getAccessGrouped(
                    from, to, visitorType, actionType, invalidPeriod, DataType.ALL
            );
        });
    }
}
