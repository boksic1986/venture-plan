package hu.gaboros.ventureplan.logparser.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Follower implements Creature {

  private List<Spell> spells;
  private Long role;
  private String name;
  private Long boardIndex;
  private Long health;
  private Long maxHealth;
  private Long level;
  private Long attack;
}
