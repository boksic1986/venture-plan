package hu.gaboros.ventureplan.logparser.service;

import hu.gaboros.ventureplan.logparser.model.MissionReport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MissionService {

  private final MissionRepository missionRepository;

  public boolean save(MissionReport missionReport, String content) {
    long hash = createHash(content);

    Long id = missionRepository.findIdByHash(hash);
    if (id == null) {
      missionReport.setLogContent(content);
      missionReport.setHash(hash);
      missionRepository.save(missionReport);
      return true;
    }
    return false;
  }

  private static long createHash(String string) {
    long h = 1125899906842597L; // prime
    int len = string.length();

    for (int i = 0; i < len; i++) {
      h = 31 * h + string.charAt(i);
    }
    return h;
  }
}
