package katas.vending;

public class VendingMachine {

  private final VendingMachineHardware hopper;

  public VendingMachine(VendingMachineHardware hopper) {
    this.hopper = hopper;
  }

  /**
   * Adds `quantity` of `product` to the machine's stock.
   */
  public void stock(Product product, int quantity) {
  }

  /**
   * Adds the value of `coin` to the total.
   */
  public void insert(Coin coin) {
  }

  /**
   * Dispenses `product` if sufficient money has been inserted. After dispensing
   * `product` any remaining balance is returned.
   *
   * @throws InsertCoins      if insufficient money has been inserted.
   * @throws OutOfStock       if `item` is out of stock.
   * @throws CannotMakeChange if the machine is unable to make change.
   */
  public void purchase(Product product) throws InsertCoins, OutOfStock, CannotMakeChange {
  }

}