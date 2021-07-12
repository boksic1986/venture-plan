package hu.gaboros.ventureplan.logparser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.gaboros.ventureplan.logparser.model.json.MissionReport;
import hu.gaboros.ventureplan.logparser.service.MissionService;
import hu.gaboros.ventureplan.logparser.service.SpellService;
import java.io.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class VentureplanLogparserApplication {

  @Value("${ventureplan.unprocessed_log_folder}")
  private String logFolder;

  @Value("${ventureplan.combined_log.folder}")
  private String combinedLogFolder;

  @Value("${ventureplan.combined_log.file}")
  private String combinedLogFileName;

  public static void main(String[] args) {
    SpringApplication.run(VentureplanLogparserApplication.class, args);
  }

  @Bean
  public CommandLineRunner runner(MissionService missionService, SpellService spellService) {
    return args -> {
      ObjectMapper mapper = new ObjectMapper();

      File combinedLogFile = new File(combinedLogFolder, combinedLogFileName);
      if (!combinedLogFile.isFile()) {
        combinedLogFile.createNewFile();
      }
      OutputStreamWriter writer =
          new OutputStreamWriter(new FileOutputStream(combinedLogFile, true));

      long parsedLogs = 0;
      long newLogs = 0;
      long newInvalidPrediction = 0;
      long newDifferentOutcome = 0;
      long newSpells = 0;
      long startTime = System.currentTimeMillis();
      for (final File fileEntry : new File(logFolder).listFiles()) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileEntry))) {
          String mission;
          while ((mission = br.readLine()) != null) {
            try {
              MissionReport missionReport = mapper.readValue(mission, new TypeReference<>() {});
              boolean newLogCreated = missionService.save(missionReport, mission);
              parsedLogs++;
              if (newLogCreated) {
                writer.append(mission).append("\n");
                writer.flush();
                newLogs++;
                if (BooleanUtils.isFalse(missionReport.getPredictionCorrect())) {
                  newInvalidPrediction++;
                }
                if (BooleanUtils.isTrue(missionReport.getDifferentOutcome())) {
                  newDifferentOutcome++;
                }
              }
              newSpells += spellService.save(missionReport);
            } catch (JsonParseException jsonParseException) {
              // skip, probably just old format
            }
          }
        }
      }

      writer.close();
      long milliseconds = System.currentTimeMillis() - startTime;
      long minutes = (milliseconds / 1000) / 60;
      long seconds = (milliseconds / 1000) % 60;

      log.info("Number of logs parsed: {}", parsedLogs);
      log.info("Number of logs created: {}", newLogs);
      log.info("Number of new invalid predictions: {}", newInvalidPrediction);
      log.info("Number of new different outcomes: {}", newDifferentOutcome);
      log.info("Number of new spells: {}", newSpells);
      log.info(
          "Elapsed time: {}:{}",
          StringUtils.leftPad(String.valueOf(minutes), 2, "0"),
          StringUtils.leftPad(String.valueOf(seconds), 2, "0"));
    };
  }
}
