package katas.vending;

import static java.lang.String.format;

public class OutOfStock extends IllegalStateException {
  public OutOfStock(Product product) {
    super(format("Sorry, we are out of %s", product));
  }
}
