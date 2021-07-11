package hu.gaboros.ventureplan.logparser.service;

import hu.gaboros.ventureplan.logparser.model.MissionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<MissionReport, Long> {

  @Query("select mr.id from MissionReport mr where mr.hash = ?1")
  Long findIdByHash(long hash);
}
