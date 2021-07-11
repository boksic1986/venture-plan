package hu.gaboros.ventureplan.logparser.service;

import hu.gaboros.ventureplan.logparser.mapper.MissionReportMapper;
import hu.gaboros.ventureplan.logparser.model.entity.MissionReportEntity;
import hu.gaboros.ventureplan.logparser.model.json.MissionReport;
import hu.gaboros.ventureplan.logparser.repository.MissionRepository;
import hu.gaboros.ventureplan.logparser.util.HashUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MissionService {

  private final MissionRepository missionRepository;
  private final MissionReportMapper missionReportMapper;
  private final LanguageDetector languageDetector;

  public boolean save(MissionReport missionReport, String content) {
    long id = HashUtil.createHash(content);

    boolean exists = missionRepository.existsById(id);
    if (!exists) {
      MissionReportEntity entity = missionReportMapper.dtoToEntity(missionReport);
      entity.setId(id);
      entity.setLogContent(content);
      entity.setEnglish(languageDetector.isEnglish(missionReport.getMissionName()));
      missionRepository.save(entity);
      return true;
    }
    return false;
  }
}
