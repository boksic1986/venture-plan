package hu.gaboros.ventureplan.logparser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class MissionReport {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @JsonProperty private Boolean winner;
  @JsonProperty private Boolean predictionCorrect = true;
  @JsonProperty private String addonVersion = "5.1-beta";
  @JsonProperty private String missionName;

  @JsonProperty(value = "missionID")
  private Long missionId;

  @Transient // for now
  @JsonProperty
  private List<Encounter> encounters;

  @Column(columnDefinition = "longtext")
  private String logContent;
}
