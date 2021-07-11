package hu.gaboros.ventureplan.logparser.model.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "spell")
public class SpellEntity {

  @Id private Long id;

  @Column(columnDefinition = "text")
  private String description;

  private Long autoCombatSpellId;
  private Long duration;
  private String name;
  private boolean hasThornsEffect;
  private Long cooldown;
  private Long creatureHealth;
  private Long creatureRole;
  private String creatureName;
  private Long creatureAttack;
  private boolean isEnemy;
}
