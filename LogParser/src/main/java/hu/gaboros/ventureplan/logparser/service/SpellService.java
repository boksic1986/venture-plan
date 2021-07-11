package hu.gaboros.ventureplan.logparser.service;

import hu.gaboros.ventureplan.logparser.mapper.SpellMapper;
import hu.gaboros.ventureplan.logparser.model.entity.SpellEntity;
import hu.gaboros.ventureplan.logparser.model.json.*;
import hu.gaboros.ventureplan.logparser.repository.SpellRepository;
import hu.gaboros.ventureplan.logparser.util.HashUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SpellService {

  private final SpellRepository spellRepository;
  private final SpellMapper spellMapper;
  private final LanguageDetector languageDetector;

  public Long save(MissionReport missionReport) {
    Long numberOfNewSpells = 0L;
    for (Encounter encounter : missionReport.getEncounters()) {
      for (Spell spell : encounter.getSpells()) {
        if (saveSpell(spell, encounter, true)) {
          numberOfNewSpells++;
        }
      }
    }

    for (Follower follower : missionReport.getFollowers().values()) {
      for (Spell spell : follower.getSpells()) {
        if (saveSpell(spell, follower, false)) {
          numberOfNewSpells++;
        }
      }
    }
    return numberOfNewSpells;
  }

  private boolean saveSpell(Spell spell, Creature creature, boolean isEnemy) {
    long id = HashUtil.createHash(spell.getName());

    boolean exists = spellRepository.existsById(id);
    if (!exists) {
      SpellEntity entity = spellMapper.dtoToEntity(spell);
      entity.setId(id);
      entity.setEnemy(isEnemy);
      entity.setCreatureHealth(creature.getMaxHealth());
      entity.setCreatureRole(creature.getRole());
      entity.setCreatureName(creature.getName());
      entity.setCreatureAttack(creature.getAttack());
      entity.setEnglish(languageDetector.isEnglish(spell.getDescription()));
      spellRepository.save(entity);
      return true;
    }
    return false;
  }
}
