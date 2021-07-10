package hu.gaboros.ventureplan.logparser.service;

import hu.gaboros.ventureplan.logparser.model.MissionReport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MissionService {

  private final MissionRepository missionRepository;

  public void save(MissionReport missionReport) {
    // This will be slow...
    Long id = missionRepository.findIdByLogContent(missionReport.getLogContent());
    if (id == null) {
      missionRepository.save(missionReport);
    }
  }
}
