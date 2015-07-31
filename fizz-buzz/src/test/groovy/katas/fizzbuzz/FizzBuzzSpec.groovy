package katas.fizzbuzz

import spock.genesis.Gen
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class FizzBuzzSpec extends Specification {

  @Subject def function = new FizzBuzz()

  /**
   * This first test case can be made to pass by simply returning "fizz"
   * regardless of the argument.
   *
   * return "fizz"
   */
  @Unroll("fizzbuzz(#n) returns 'fizz'")
  def "an argument divisible by 3 returns 'fizz'"() {
    expect:
    function.apply(n) == "fizz"

    where:
    n << Gen.integer.filter { it % 3 == 0 && it % 5 != 0 }.take(20)
  }

  /**
   * Now we can simply divide input into integers that are divisible by 5 and
   * everything else.
   *
   * return n % 5 == 0 ? "buzz" : "fizz"
   */
  @Unroll("fizzbuzz(#n) returns 'buzz'")
  def "an argument divisible by 5 returns 'buzz'"() {
    expect:
    function.apply(n) == "buzz"

    where:
    n << Gen.integer.filter { it % 5 == 0 && it % 3 != 0 }.take(20)
  }

  /**
   * At this point we need to identify inputs that are divisible by both 3 and 5
   * but we can still use a naive algorithm that assumes anything not divisible
   * by 5 is divisible by 3.
   *
   * if (n % 5 == 0) {*   return n % 3 == 0 ? "fizzbuzz" : "buzz"
   *}* return "fizz"
   */
  @Unroll("fizzbuzz(#n) returns 'fizzbuzz'")
  def "an argument divisible by 3 and 5 returns 'fizzbuzz'"() {
    expect:
    function.apply(n) == "fizzbuzz"

    where:
    n << Gen.integer.filter { it % 3 == 0 && it % 5 == 0 }.take(20)
  }

  /**
   * Now we add the final case which requires us to distinguish inputs divisible
   * by neither 3 or 5.
   *
   * if (n % 5 == 0) {*   return n % 3 == 0 ? "fizzbuzz" : "buzz"
   *} else if (n % 3 == 0) {*   return "fizz"
   *}* return n.toString()
   *
   * At this point we have a complete algorithm and can refactor in various ways
   * to produce the most satisfying solution.
   */
  @Unroll("fizzbuzz(#n) returns '#n'")
  def "any other argument returns itself"() {
    expect:
    function.apply(n) == n.toString()

    where:
    n << Gen.integer.filter { it % 3 != 0 && it % 5 != 0 }.take(20)
  }

}
