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

  private static final String EN_US = "enUS";

  private final MissionRepository missionRepository;
  private final MissionReportMapper missionReportMapper;

  public boolean save(MissionReport missionReport, String content) {
    long id = HashUtil.createHash(content);

    boolean exists = missionRepository.existsById(id);
    if (!exists) {
      MissionReportEntity entity = missionReportMapper.dtoToEntity(missionReport);
      entity.setId(id);
      entity.setLogContent(content);
      entity.setEnglish(EN_US.equals(missionReport.getMeta().getLanguage()));
      // Before version 5.4-beta this variable was badly assumed.
      if (entity.getDifferentOutcome() == null) {
        entity.setDifferentOutcome(!entity.getPredictionCorrect());
        entity.setPredictionCorrect(null);
        missionReport.setDifferentOutcome(!missionReport.getPredictionCorrect());
        missionReport.setPredictionCorrect(null);
      }
      missionRepository.save(entity);
      return true;
    }
    return false;
  }
}
