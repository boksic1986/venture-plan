package hu.gaboros.ventureplan.logparser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty private Boolean predictionCorrect;
  @JsonProperty private String addonVersion;
  @JsonProperty private String missionName;

  @Column(columnDefinition = "longtext")
  private String logContent;
}
