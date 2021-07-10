package hu.gaboros.ventureplan.logparser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoAttack {

  @JsonProperty private String description;
  @JsonProperty private Long autoCombatSpellID;
  @JsonProperty private Long duration;
  @JsonProperty private Long previewMask;
  @JsonProperty private String name;
  @JsonProperty private boolean hasThornsEffect;
  @JsonProperty private Long schoolMask;
  @JsonProperty private Long icon;
  @JsonProperty private Long cooldown;
  @JsonProperty private Long spellTutorialFlag;
}
