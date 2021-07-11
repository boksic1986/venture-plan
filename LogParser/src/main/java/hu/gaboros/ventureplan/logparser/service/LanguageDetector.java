package hu.gaboros.ventureplan.logparser.service;

import java.io.IOException;
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
    this.languageDetector.setShortText(true);
  }

  public boolean isEnglish(String content) {
    LanguageResult languageResult = languageDetector.detect(content);
    return languageResult.getLanguage() != null && languageResult.isLanguage(ENGLISH);
  }
}
