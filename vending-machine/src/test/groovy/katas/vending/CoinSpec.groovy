package katas.vending

import spock.lang.Specification
import spock.lang.Unroll
import static katas.vending.Coin.*

class CoinSpec extends Specification {
  @Unroll
  def "#coin is printed as '#expected'"() {
    expect:
    coin.toString() == expected

    where:
    coin    | expected
    PENNY   | "1¢"
    NICKEL  | "5¢"
    DIME    | "10¢"
    QUARTER | "25¢"
    DOLLAR  | '$1'
  }
}
