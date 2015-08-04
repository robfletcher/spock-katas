package katas.vending;

public interface VendingMachineHardware {

  /**
   * Dispenses `n` of a coin.
   *
   * @throws IllegalStateException if there are fewer than `n` of `coin`.
   */
  void refund(Coin coin, int n);

  /**
   * @return the number of coins of type `coin` currently held.
   */
  int count(Coin coin);

  /**
   * Dispenses a product if it is currently in stock.
   *
   * @throws IllegalStateException if the current stock of `product` is zero.
   */
  void dispense(Product product);

  /**
   * @return the number of `product` currently in stock.
   */
  int stock(Product product);

  /**
   * Adds a coin of given type to the correct tube
   *
   * @param coin the coin to be added
   */
  void addCoin(Coin coin);
}
