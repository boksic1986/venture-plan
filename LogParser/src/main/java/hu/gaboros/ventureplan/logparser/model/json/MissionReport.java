package hu.gaboros.ventureplan.logparser.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MissionReport {

  private Boolean winner;
  private Boolean predictionCorrect = true;
  private Boolean differentOutcome;
  private String addonVersion = "5.1-beta";
  private String missionName;
  private Meta meta;

  @JsonProperty(value = "missionID")
  private Long missionId;

  private List<Encounter> encounters;
  private Map<String, Follower> followers;
}
