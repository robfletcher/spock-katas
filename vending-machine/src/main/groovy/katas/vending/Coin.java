package katas.vending;

public enum Coin {

  PENNY(1),
  NICKEL(5),
  DIME(10),
  QUARTER(25),
  DOLLAR(100);

  private final int value;

  Coin(int value) {
    this.value = value;
  }

  public int value() {
    return value;
  }

  @Override
  public String toString() {
    StringBuilder buffer = new StringBuilder();
    if (value >= 100) {
      buffer.append('$').append(value / 100);
      if (value % 100 > 0) {
        buffer.append('.');
      }
    }
    if (value % 100 > 0) {
      buffer.append(value % 100).append('¢');
    }
    return buffer.toString();
  }
}
