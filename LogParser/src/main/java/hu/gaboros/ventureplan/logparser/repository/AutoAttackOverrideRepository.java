package hu.gaboros.ventureplan.logparser.repository;

import hu.gaboros.ventureplan.logparser.model.entity.AutoAttackOverrideEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoAttackOverrideRepository
    extends JpaRepository<AutoAttackOverrideEntity, Long> {}
