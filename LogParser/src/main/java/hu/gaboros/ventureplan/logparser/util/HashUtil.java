package hu.gaboros.ventureplan.logparser.util;

public class HashUtil {

  public static long createHash(String string) {
    long h = 1125899906842597L; // prime
    int len = string.length();

    for (int i = 0; i < len; i++) {
      h = 31 * h + string.charAt(i);
    }
    return h;
  }
}
