package hu.gaboros.ventureplan.logparser.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoAttack extends Spell {

  private Long previewMask;
  private Long schoolMask;
  private Long icon;
  private Long spellTutorialFlag;
}
