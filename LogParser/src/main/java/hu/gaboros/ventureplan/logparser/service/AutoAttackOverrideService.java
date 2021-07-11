package hu.gaboros.ventureplan.logparser.service;

import hu.gaboros.ventureplan.logparser.model.entity.AutoAttackOverrideEntity;
import hu.gaboros.ventureplan.logparser.model.json.Encounter;
import hu.gaboros.ventureplan.logparser.model.json.MissionReport;
import hu.gaboros.ventureplan.logparser.repository.AutoAttackOverrideRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AutoAttackOverrideService {

  private final AutoAttackOverrideRepository autoAttackOverrideRepository;

  public void save(MissionReport missionReport) {

    for (Encounter encounter : missionReport.getEncounters()) {
      Long autoCombatSpellId = encounter.getAutoCombatAutoAttack().getAutoCombatSpellId();
      if (autoCombatSpellId != 15 && autoCombatSpellId != 11) {
        log.warn("Unknown autoCombatSpellId:" + autoCombatSpellId);
        continue;
      }

      AutoAttackOverrideEntity autoAttackOverride = new AutoAttackOverrideEntity();
      autoAttackOverride.setId(
          4 + 2 * encounter.getBoardIndex() + 32 * missionReport.getMissionId());
      autoAttackOverride.setIsRanged(autoCombatSpellId == 15 ? 1 : 0);

      autoAttackOverrideRepository.save(autoAttackOverride);
    }
  }
}
