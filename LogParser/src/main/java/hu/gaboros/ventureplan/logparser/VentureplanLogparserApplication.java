package hu.gaboros.ventureplan.logparser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.gaboros.ventureplan.logparser.model.MissionReport;
import hu.gaboros.ventureplan.logparser.service.MissionService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class VentureplanLogparserApplication {

  @Value("${ventureplan.log_folder}")
  private String logFolder;

  public static void main(String[] args) {
    SpringApplication.run(VentureplanLogparserApplication.class, args);
  }

  @Bean
  public CommandLineRunner runner(MissionService missionService) {
    return args -> {
      ObjectMapper mapper = new ObjectMapper();

      final File folder = new File(logFolder);

      int parsedLogs = 0;
      for (final File fileEntry : folder.listFiles()) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileEntry))) {
          String mission;
          while ((mission = br.readLine()) != null) {
            try {
              MissionReport missionReport =
                  mapper.readValue(mission, new TypeReference<MissionReport>() {});
              missionReport.setLogContent(mission);
              missionService.save(missionReport);
              parsedLogs++;
            } catch (JsonParseException jsonParseException) {
              // skip, probably just old format
            }
          }
        }
      }
      log.info("Number logs parsed: " + parsedLogs);
    };
  }
}
