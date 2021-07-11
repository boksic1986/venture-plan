package hu.gaboros.ventureplan.logparser.mapper;

import hu.gaboros.ventureplan.logparser.model.entity.SpellEntity;
import hu.gaboros.ventureplan.logparser.model.json.Spell;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpellMapper {

  SpellEntity dtoToEntity(Spell dto);
}
