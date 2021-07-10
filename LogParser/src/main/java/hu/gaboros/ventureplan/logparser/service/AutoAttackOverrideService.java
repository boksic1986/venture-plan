package hu.gaboros.ventureplan.logparser.service;

import hu.gaboros.ventureplan.logparser.model.AutoAttackOverride;
import hu.gaboros.ventureplan.logparser.model.Encounter;
import hu.gaboros.ventureplan.logparser.model.MissionReport;
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
      Long autoCombatSpellID = encounter.getAutoCombatAutoAttack().getAutoCombatSpellID();
      if (autoCombatSpellID != 15 && autoCombatSpellID != 11) {
        log.warn("Unknown autoCombatSpellID:" + autoCombatSpellID);
        continue;
      }

      AutoAttackOverride autoAttackOverride = new AutoAttackOverride();
      autoAttackOverride.setId(
          4 + 2 * encounter.getBoardIndex() + 32 * missionReport.getMissionId());
      autoAttackOverride.setIsRanged(autoCombatSpellID == 15 ? 1 : 0);

      autoAttackOverrideRepository.save(autoAttackOverride);
    }
  }
}
