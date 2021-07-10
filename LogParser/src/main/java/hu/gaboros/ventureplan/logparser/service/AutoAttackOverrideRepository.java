package hu.gaboros.ventureplan.logparser.service;

import hu.gaboros.ventureplan.logparser.model.AutoAttackOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoAttackOverrideRepository extends JpaRepository<AutoAttackOverride, Long> {}
