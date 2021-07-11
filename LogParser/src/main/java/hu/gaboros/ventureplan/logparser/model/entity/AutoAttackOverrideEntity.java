package hu.gaboros.ventureplan.logparser.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "auto_attack_override")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoAttackOverrideEntity {

  @Id private Long id;
  private Integer isRanged;
}
