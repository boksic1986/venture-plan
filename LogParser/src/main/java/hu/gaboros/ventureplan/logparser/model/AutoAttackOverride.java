package hu.gaboros.ventureplan.logparser.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoAttackOverride {

  @Id private Long id;
  private Integer isRanged;
}
