package katas.fizzbuzz

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll
import static net.java.quickcheck.generator.PrimitiveGeneratorsIterables.someIntegers

class FizzBuzzSpec extends Specification {

  @Subject def function = new FizzBuzz()
  @Shared def integers = someIntegers()

  @Unroll("fizzbuzz(#n) returns 'fizz'")
  def "an argument divisible by 3 returns 'fizz'"() {
    expect:
    function.apply(n) == "fizz"

    where:
    n << integers.findAll { it % 3 == 0 && it % 5 != 0 }
  }

  @Unroll("fizzbuzz(#n) returns 'buzz'")
  def "an argument divisible by 5 returns 'buzz'"() {
    expect:
    function.apply(n) == "buzz"

    where:
    n << integers.findAll { it % 5 == 0 && it % 3 != 0 }
  }

  @Unroll("fizzbuzz(#n) returns 'fizzbuzz'")
  def "an argument divisible by 3 and 5 returns 'fizzbuzz'"() {
    expect:
    function.apply(n) == "fizzbuzz"

    where:
    n << integers.findAll { it % 3 == 0 && it % 5 == 0 }
  }

  @Unroll("fizzbuzz(#n) returns '#n'")
  def "any other argument returns itself"() {
    expect:
    function.apply(n) == n.toString()

    where:
    n << integers.findAll { it % 3 != 0 && it % 5 != 0 }
  }

}
