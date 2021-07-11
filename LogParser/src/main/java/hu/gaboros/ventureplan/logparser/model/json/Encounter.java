package hu.gaboros.ventureplan.logparser.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Encounter {

  private AutoAttack autoCombatAutoAttack;
  private Boolean isElite;
  private Long health;
  private Long role;
  private String name;
  private Long boardIndex;
  private Long maxHealth;
  private Long attack;

  @JsonProperty(value = "autoCombatSpells")
  private List<Spell> spells;
}
