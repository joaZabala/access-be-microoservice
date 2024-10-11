package ar.edu.utn.frc.tup.lc.iv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.utn.frc.tup.lc.iv.entities.AuthEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.AuthRangeEntity;

import java.util.List;

@Repository
public interface AuthRangeRepository extends JpaRepository<AuthRangeEntity, Long> {

    // find by auth id

    List<AuthRangeEntity> findByAuthId(AuthEntity authEntity);

}
