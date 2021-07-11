package hu.gaboros.ventureplan.logparser.repository;

import hu.gaboros.ventureplan.logparser.model.entity.MissionReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<MissionReportEntity, Long> {}
