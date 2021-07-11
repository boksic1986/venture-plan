package hu.gaboros.ventureplan.logparser.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Spell {

  private String description;

  @JsonProperty(value = "autoCombatSpellID")
  private Long autoCombatSpellId;

  private Long duration;
  private String name;
  private boolean hasThornsEffect;
  private Long cooldown;
}
