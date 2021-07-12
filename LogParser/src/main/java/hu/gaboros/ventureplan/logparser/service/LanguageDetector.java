package hu.gaboros.ventureplan.logparser.service;

import java.io.IOException;
import java.util.List;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.springframework.stereotype.Service;

@Service
public class LanguageDetector {

  private final String ENGLISH = "en";
  private final OptimaizeLangDetector languageDetector;

  public LanguageDetector() throws IOException {
    this.languageDetector = new OptimaizeLangDetector();
    this.languageDetector.loadModels();
  }

  public boolean isEnglish(String content) {
    List<LanguageResult> languageResults = languageDetector.detectAll(content);
    return languageResults != null
        && !languageResults.isEmpty()
        && languageResults.stream().anyMatch(languageResult -> languageResult.isLanguage(ENGLISH));
  }
}
