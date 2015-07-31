package katas.diamond

import groovy.transform.CompileStatic
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll
import spock.util.mop.Use
import static net.java.quickcheck.generator.CombinedGenerators.uniqueValues
import static net.java.quickcheck.generator.CombinedGeneratorsIterables.someExcludeValues
import static net.java.quickcheck.generator.PrimitiveGenerators.characters

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
    c << someExcludeValues(uniqueValues(characters()), A..Z).take(20)
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
   */
  @Unroll("the diamond of #c is square")
  def "diamonds are always square"() {
    given:
    def result = function.apply(c)

    expect:
    result.every { it.length() == result.size() }

    where:
    c << RANGE
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
    c << RANGE
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
    c << RANGE
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
   * already know the result is horizontally and vertically symmetrical.
   */
  @Unroll("each row of diamond #c should contain the appropriate character in the right column")
  def "each row should contain its character at the correct position"() {
    given:
    def result = function.apply(c)

    expect:
    for (rowChar in (A..c)) {
      def row = (rowChar - A)
      def col = result.size().intdiv(2) - (rowChar - A)
      assert result[row].charAt(col) == rowChar
    }

    where:
    c << RANGE
  }

  /**
   * Our final test requires that every character other than the row character
   * in the correct column should be -. With this in place we have a complete
   * implementation of the diamond.
   *
   * Again we only need to check the "top left" quadrant of the result as we
   * have already ensured that the result is symmetrical.
   */
  @Unroll("each row of diamond #c should contain - in every other column")
  @Use(StringOps)
  def "each row should contain - in every other column"() {
    given:
    def result = function.apply(c)

    expect:
    for (rowChar in (A..c)) {
      def row = (rowChar - A)
      def midpoint = result.size().intdiv(2)
      def col = midpoint - (rowChar - A)
      def line = result[row][0..midpoint]
      assert line.without(col) ==~ /-+/
    }

    where:
    c << RANGE
  }
}

@CompileStatic
@Category(String)
class StringOps {
  String without(int index) {
    def list = this.toList()
    list.remove(index)
    return list.join("")
  }
}
