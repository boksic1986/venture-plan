package hu.gaboros.ventureplan.logparser.repository;

import hu.gaboros.ventureplan.logparser.model.entity.SpellEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpellRepository extends JpaRepository<SpellEntity, Long> {}
