package utils;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * User: E.Omelyashchik
 * Date: 19.05.20
 */
public class CommonUtils {

  /**
   * Combines two integer one-dimensional array into one.
   *
   * @param firstArray  First array
   * @param secondArray Second array
   * @return One-dimensional sorted array of unique values
   */
  public static int[] combineArrays(@NotNull int[] firstArray, @NotNull int[] secondArray) {
    return IntStream.concat(Arrays.stream(firstArray), Arrays.stream(secondArray))
        .sorted()
        .distinct()
        .toArray();
  }

  /**
   * Transforms a string into an array
   *
   * @param s A string of integers. A separator of numbers is a single space.
   * @return One-dimensional array
   */
  public static int[] transformFromStringToArray(@NotNull String s) {
    return Arrays.stream(s.split(" "))
        .mapToInt(Integer::parseInt)
        .toArray();
  }

  /**
   * Generates [1,5] integers in the range [-100,100)
   */
  public static String getRandomNumbers() {
    Random randomGenerator = new Random();
    StringBuilder buf = new StringBuilder();
    int length = 1 + randomGenerator.nextInt(5);
    for (int i = 0; i < length; i++) {
      if (i != 0)
        buf.append(" ");
      buf.append(Integer.toString(-100 + randomGenerator.nextInt(200)));
    }
    return buf.toString();
  }
}
