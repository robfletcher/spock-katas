package katas.diamond;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.unmodifiableList;

public class Diamond {
  public List<String> apply(Character c) {
    if (c < 'A' || c > 'Z') {
      throw new IllegalArgumentException("character out of range");
    }
    int midpoint = c - 'A';
    return unmodifiableList(buildRows(midpoint));
  }

  private List<String> buildRows(int midpoint) {
    List<String> result = new ArrayList<>();
    for (int row = 0; row <= midpoint; row++) {
      result.add(buildRow(midpoint, row));
    }
    mirror(result);
    return result;
  }

  private String buildRow(int midpoint, int rowIndex) {
    char rowChar = (char) ('A' + rowIndex);

    StringBuilder line = new StringBuilder();
    pad(line, midpoint - rowIndex);
    line.append(rowChar);
    pad(line, rowIndex);
    mirror(line);
    return line.toString();
  }

  private void pad(StringBuilder line, int numChars) {
    for (int col = 0; col < numChars; col++) {
      line.append('-');
    }
  }

  private void mirror(StringBuilder line) {
    line.append(new StringBuilder(line.substring(0, line.length() - 1)).reverse());
  }

  private void mirror(List<String> lines) {
    int index = lines.size();
    for (int i = 0; i < index - 1; i++) {
      lines.add(index, lines.get(i));
    }
  }
}
