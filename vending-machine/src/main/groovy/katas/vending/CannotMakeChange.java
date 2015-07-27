package katas.vending;

import static java.lang.String.format;

public class CannotMakeChange extends IllegalStateException {
  public CannotMakeChange(int inserted, int change) {
    super(format("Cannot make change of %d¢ from %d¢", change, inserted));
  }
}
