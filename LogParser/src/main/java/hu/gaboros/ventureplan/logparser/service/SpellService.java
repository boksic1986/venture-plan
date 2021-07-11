package hu.gaboros.ventureplan.logparser.service;

import hu.gaboros.ventureplan.logparser.mapper.SpellMapper;
import hu.gaboros.ventureplan.logparser.model.entity.SpellEntity;
import hu.gaboros.ventureplan.logparser.model.json.Encounter;
import hu.gaboros.ventureplan.logparser.model.json.MissionReport;
import hu.gaboros.ventureplan.logparser.model.json.Spell;
import hu.gaboros.ventureplan.logparser.repository.SpellRepository;
import hu.gaboros.ventureplan.logparser.util.HashUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpellService {

  private final SpellRepository spellRepository;
  private final SpellMapper spellMapper;

  public Long save(MissionReport missionReport) {
    Long numberOfNewSpells = 0L;
    for (Encounter encounter : missionReport.getEncounters()) {
      for (Spell spell : encounter.getSpells()) {
        long id = HashUtil.createHash(spell.getName());

        boolean exists = spellRepository.existsById(id);
        if (!exists) {
          SpellEntity entity = spellMapper.dtoToEntity(spell);
          entity.setId(id);
          entity.setEnemy(true);
          entity.setCreatureHealth(encounter.getMaxHealth());
          entity.setCreatureRole(encounter.getRole());
          entity.setCreatureName(encounter.getName());
          entity.setCreatureAttack(encounter.getAttack());
          spellRepository.save(entity);
          numberOfNewSpells++;
        }
      }
    }
    return numberOfNewSpells;
  }
}
