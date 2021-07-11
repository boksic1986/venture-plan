package hu.gaboros.ventureplan.logparser.mapper;

import hu.gaboros.ventureplan.logparser.model.entity.MissionReportEntity;
import hu.gaboros.ventureplan.logparser.model.json.MissionReport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MissionReportMapper {

  MissionReportEntity dtoToEntity(MissionReport dto);
}
