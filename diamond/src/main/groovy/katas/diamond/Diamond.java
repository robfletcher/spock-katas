package katas.diamond;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.floorDiv;
import static java.util.Collections.unmodifiableList;

public class Diamond {
  public List<String> apply(Character c) {
    if (c < 'A' || c > 'Z') {
      throw new IllegalArgumentException("character out of range");
    }
    int size = ((c - 'A') * 2) + 1;

    int midpoint = floorDiv(size, 2);
    int before = midpoint;
    int after = 0;

    List<String> result = new ArrayList<>();

    for (int row = 0; row <= midpoint; row++) {
      char rowChar = (char) ('A' + row);

      StringBuilder line = new StringBuilder();
      pad(line, before);
      line.append(rowChar);
      pad(line, after);
      mirror(line);
      result.add(line.toString());

      before--;
      after++;
    }
    mirror(result);

    return unmodifiableList(result);
  }

  private void pad(StringBuilder line, int numChars) {
    for (int col = 0; col < numChars; col++) {
      line.append('-');
    }
  }

  private void mirror(StringBuilder line) {
    line.append(new StringBuffer(line.substring(0, line.length() - 1)).reverse());
  }

  private void mirror(List<String> result) {
    for (int i = result.size() - 2; i >= 0; i--) {
      result.add(result.get(i));
    }
  }
}
