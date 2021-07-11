package hu.gaboros.ventureplan.logparser.model.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mission_report")
public class MissionReportEntity {

  @Id private Long id;

  private Boolean winner;
  private Boolean predictionCorrect;
  private String addonVersion;
  private String missionName;
  private Long missionId;

  @Column(columnDefinition = "longtext")
  private String logContent;
}
