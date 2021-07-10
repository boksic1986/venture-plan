package hu.gaboros.ventureplan.logparser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Encounter {

  @JsonProperty private AutoAttack autoCombatAutoAttack;
  @JsonProperty private Boolean isElite;
  @JsonProperty private Long health;
  @JsonProperty private Long role;
  @JsonProperty private String name;
  @JsonProperty private Long boardIndex;
  @JsonProperty private Long maxHealth;
  @JsonProperty private Long attack;
}
