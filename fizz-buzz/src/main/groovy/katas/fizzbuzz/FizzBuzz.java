package katas.fizzbuzz;

public class FizzBuzz {
  public String apply(Integer input) {
    StringBuilder result = new StringBuilder();
    if (input % 3 == 0) {
      result.append("fizz");
    }
    if (input % 5 == 0) {
      result.append("buzz");
    }
    return result.length() > 0 ? result.toString() : input.toString();
  }
}
