package ar.edu.utn.frc.tup.lc.iv.repositories;

import ar.edu.utn.frc.tup.lc.iv.entities.AccessesEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.VisitorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class VisitAccessRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private VisitAccessRepository visitAccessRepository;
    @Test
    void getVisitAccessesListByPlotTest() {
        AccessesEntity accessesEntity = new AccessesEntity();
        accessesEntity.setAuthRange(null);
        accessesEntity.setEntryDate(LocalDateTime.of(2024,9,29,18,5));
        accessesEntity.setExitDate(LocalDateTime.of(2024,9,29,18,23));
        accessesEntity.setVisitor(getVisitor());
        accessesEntity.setPlotId(1025L);
        testEntityManager.persist(accessesEntity);
        testEntityManager.flush();
        List<AccessesEntity> visitAccesses = visitAccessRepository.getVisitAccessesList(1025L,
                LocalDateTime.of(2020,1,1,0,0),
                LocalDateTime.of(2030,1,1,0,0));
        assertEquals(25652321L, visitAccesses.get(0).getVisitor().getDocNumber());
    }

    @Test
    void getVisitAccessesListByVisitIdTest() {
        AccessesEntity accessesEntity = new AccessesEntity();
        accessesEntity.setAuthRange(null);
        accessesEntity.setEntryDate(LocalDateTime.of(2024,9,29,18,5));
        accessesEntity.setExitDate(LocalDateTime.of(2024,9,29,18,23));
        accessesEntity.setVisitor(getVisitor());
        accessesEntity.setPlotId(1025L);
        testEntityManager.persist(accessesEntity);
        testEntityManager.flush();
        List<AccessesEntity> visitAccesses = visitAccessRepository.getVisitAccessesList(
                1025L,
                accessesEntity.getVisitor().getVisitorId(),
                LocalDateTime.of(2020,1,1,0,0),
                LocalDateTime.of(2030,1,1,0,0));
        assertEquals(25652321L, visitAccesses.get(0).getVisitor().getDocNumber());
    }

    private VisitorEntity getVisitor(){
            VisitorEntity visitorEntity = new VisitorEntity();
            visitorEntity.setName("Carlos");
            visitorEntity.setLastName("Ochoa");
            visitorEntity.setDocNumber(25652321L);
            visitorEntity.setBirthDate(LocalDate.of(1985, 6, 25));
            visitorEntity.setActive(true);
            Long id = (Long)testEntityManager.persistAndGetId(visitorEntity);
            testEntityManager.flush();
            return testEntityManager.find(VisitorEntity.class, id);
    }
}