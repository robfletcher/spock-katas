package katas.vending;

import static java.lang.String.format;

public class InsertCoins extends IllegalStateException {
  public InsertCoins(int inserted, int required) {
    super(format("Insufficient money deposited, %d of %d", inserted, required));
  }
}
