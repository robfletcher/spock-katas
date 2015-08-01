package katas.diamond

import spock.genesis.Gen
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class DiamondSpec extends Specification {

  @Subject def function = new Diamond()

  private static final char A = 'A'
  private static final char B = 'B'
  private static final char Z = 'Z'
  private static final Range<Character> RANGE = B..Z

  /**
   * We start with a pathological case and make it pass by simply always
   * throwing an exception.
   */
  @Unroll("#c is rejected")
  def "characters outside of A..Z are rejected"() {
    when:
    function.apply(c)

    then:
    thrown IllegalArgumentException

    where:
    c << Gen.character
            .filter { !(A..Z).contains(it) }
            .take(10)
  }

  /**
   * We can simply return a constant to satisfy this case.
   */
  def "The diamond of A is 'A'"() {
    expect:
    function.apply(A) == ["A"]
  }

  /**
   * We can now start to break down the solution in various ways. I like to
   * think about the properties we know to be true of our desired result. The
   * simplest that should hold for all results is that they should be "square".
   * Making this test pass does not require us to make any changes as we're just
   * returning a constant. We will however keep it passing as we develop the
   * solution further.
   *
   * Note that we only check diamonds for the characters starting at B. We
   * already know from the previous test that the special case of diamond(A) is
   * correct so there's no need to assert anything further about it.
   */
  @Unroll("the diamond of #c is square")
  def "diamonds are always square"() {
    given:
    def result = function.apply(c)

    expect:
    result*.length().every { it == result.size() }

    where:
    c << Gen.these(RANGE)
  }

  /**
   * The next property is that the size of the diamond should depend on the
   * argument. The diamond of A should be 1x1, the diamond of B should be 3x3
   * and so on.
   *
   * Note that we don't need to verify the "width" of the result as our previous
   * test checks that the width and height should always be equal.
   *
   * At this point we're still not making any assertions about what characters
   * are in the result so our implementation can be naive and just use anything.
   *
   * There is a risk that we're falling into the "ugly mirror" anti-pattern here
   * by calculating the dimensions in (presumably) the same way as the function
   * itself.
   */
  @Unroll("the diamond of #c should have #expectedSize rows")
  def "diamonds length corresponds to the character argument"() {
    given:
    def result = function.apply(c)

    expect:
    result.size() == expectedSize

    where:
    c << Gen.these(RANGE)
    expectedSize = ((c - A) * 2) + 1
  }

  /**
   * The next simplest property is that the result should be horizontally and
   * vertically symmetrical.
   *
   * Again, this will pass right away given our current implementation but
   * proves something useful about the result that we can build on in future.
   */
  @Unroll("the diamond of #c should be symmetrical")
  def "diamonds should be symmetrical"() {
    given:
    def result = function.apply(c)

    expect:
    result.every {
      it == it.reverse()
    }

    and:
    result == result.reverse()

    where:
    c << Gen.these(RANGE)
  }

  /**
   * The next property requires us to control the characters placed in each row
   * so that row 0 always contains A, row 1 always contains B and so on. The
   * implementation will need to maintain vertical symmetry. A simple
   * implementation may just increment and then decrement characters which could
   * later be refactored to generate only the "top half" of the diamond and then
   * mirror it.
   *
   * Although this test verifies that only the correct character appears in the
   * correct place in each row, it makes no assertion about the other characters
   * in the row. We can inch the solution forward by just filling each row with
   * the appropriate character.
   *
   * Note that we're only verifying the "top left" quadrant of the result as we
   * already know from a previous test that the diamond is horizontally and
   * vertically symmetrical.
   *
   * This is a rare instance where it seems appropriate to use the assert
   * keyword in a Spock specification. I couldn't find another way to make the
   * assertion that didn't hide what the actual problem was if the test failed.
   * Spock's output does not break down the boolean condition inside an "every"
   * closure (how could it – it would have to do so for every element in the
   * list). This implementation has the disadvantage that it will fail fast on
   * the first incorrect row but it will at least provide diagrammed power
   * assert output so you can see what's wrong.
   */
  @Unroll("each row of diamond #c should contain its character in the right column")
  def "each row should contain its character at the correct position"() {
    given:
    def result = function.apply(c)

    expect:
    int midpoint = result.size().intdiv(2)
    def half = 0..midpoint
    result[half].eachWithIndex { line, i ->
      assert line.charAt(midpoint - i) == (A..Z)[i]
    }

    where:
    c << Gen.these(RANGE)
  }

  /**
   * Our final test requires that every character other than the row character
   * should be padding.
   *
   * Again we save some effort here because of the assertions we have made in
   * previous tests. We already know that the correct A-Z character is on each
   * line and that it appears in the right index. That means here we can simply
   * take half of each line, remove the first occurrence of any A-Z character
   * wherever it is and then assert that we are left with nothing but padding
   * characters.
   *
   * Again we only need to check the "top left" quadrant of the result as we
   * have already ensured that the result is symmetrical.
   *
   * With this in place we have a complete implementation of the diamond.
   */
  @Unroll("each row of diamond #c should contain padding in every other column")
  def "each row should contain padding in every other column"() {
    given:
    def result = function.apply(c)

    expect:
    def half = [0..result.size().intdiv(2)]
    result[half].every { line ->
      line[half].replaceFirst(/[A-Z]/, "") ==~ /-+/
    }

    where:
    c << Gen.these(RANGE)
  }
}
