import exception.CustomFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.CommonUtils;
import utils.FileUtils;

import java.nio.file.Path;

/**
 * User: E.Omelyashchik
 * Date: 19.05.20
 */
public class Combine_Test extends Assert {

  private Path pathFirstFile;
  private Path pathSecondFile;
  private Logger log = LogManager.getLogger();
  private static final String PATH_FIRST_FILE = "./src/main/resources/array1.txt";
  private static final String PATH_SECOND_FILE = "./src/main/resources/array2.txt";
  private static final String INCORRECT_PATH_FILE = "./src/main/resources/incorrect.txt";

  @DataProvider
  public Object[][] correctData() {
    return new Object[][]{
        {"4 2 1", "10 1 8 3", new int[]{1, 2, 3, 4, 8, 10}},
        {"1 1 1", "1 1 1", new int[]{1}},
        {"-1 1 0", "5 -5", new int[]{-5, -1, 0, 1, 5}},
        {"2147483647 1", "-2147483648", new int[]{-2147483648, 1, 2147483647}},
    };
  }

  @DataProvider
  public Object[][] incorrectData() {
    char[] specialSymbols = "~!@#$%^&*()_+=[]{}:;|,./<>?№%`'\"".toCharArray();
    int len = specialSymbols.length;
    Object[][] objects = new Object[len + 4][];
    for (int i = 0; i < len; i++) {
      objects[i] = new Object[]{String.valueOf(specialSymbols[i]), CommonUtils.getRandomNumbers()};
    }
    objects[len] = new Object[]{"q", CommonUtils.getRandomNumbers()};
    objects[len + 1] = new Object[]{"3  1", CommonUtils.getRandomNumbers()};
    objects[len + 2] = new Object[]{"2147483648", CommonUtils.getRandomNumbers()};
    objects[len + 3] = new Object[]{"-2147483649", CommonUtils.getRandomNumbers()};
    return objects;
  }

  @Test(description = "Positive checks", dataProvider = "correctData")
  public void checkCorrectData(String textFirstFile, String testSecondFile, int[] expectedResult) throws CustomFileException {
    log.info(String.format("Positive check. 1st file: \"%s\". 2nd file: \"%s\".", textFirstFile, testSecondFile));
    pathFirstFile = FileUtils.writeToFile(PATH_FIRST_FILE, textFirstFile);
    pathSecondFile = FileUtils.writeToFile(PATH_SECOND_FILE, testSecondFile);
    int[] actualResult = new Combine().getUniqueArrayFromTwoFiles(PATH_FIRST_FILE, PATH_SECOND_FILE);
    assertEquals(actualResult, expectedResult);
  }

  @Test(description = "Negative checks: invalid characters in the first file", dataProvider = "incorrectData",
      expectedExceptions = CustomFileException.class, expectedExceptionsMessageRegExp =
      "^File contains unacceptable symbols. Details: For input string: \\\"(.*)\\\" File: " + PATH_FIRST_FILE)
  public void checkIncorrectDataFirstFile(String textFirstFile, String testSecondFile) throws CustomFileException {
    log.info(String.format("Negative check. 1st file: \"%s\". 2nd file: \"%s\".", textFirstFile, testSecondFile));
    pathFirstFile = FileUtils.writeToFile(PATH_FIRST_FILE, textFirstFile);
    pathSecondFile = FileUtils.writeToFile(PATH_SECOND_FILE, testSecondFile);
    new Combine().getUniqueArrayFromTwoFiles(PATH_FIRST_FILE, PATH_SECOND_FILE);
  }

  @Test(description = "Negative checks: invalid characters in the second file", dataProvider = "incorrectData",
      expectedExceptions = CustomFileException.class, expectedExceptionsMessageRegExp =
      "^File contains unacceptable symbols. Details: For input string: \\\"(.*)\\\" File: " + PATH_SECOND_FILE)
  public void checkIncorrectDataSecondFile(String testSecondFile, String textFirstFile) throws CustomFileException {
    log.info(String.format("Negative check. 1st file: \"%s\". 2nd file: \"%s\".", textFirstFile, testSecondFile));
    pathFirstFile = FileUtils.writeToFile(PATH_FIRST_FILE, textFirstFile);
    pathSecondFile = FileUtils.writeToFile(PATH_SECOND_FILE, testSecondFile);
    new Combine().getUniqueArrayFromTwoFiles(PATH_FIRST_FILE, PATH_SECOND_FILE);
  }

  @Test(description = "Negative checks: empty first file", expectedExceptions = CustomFileException.class,
      expectedExceptionsMessageRegExp = "^File is empty. File: " + PATH_FIRST_FILE)
  public void checkEmptyFirstFile() throws CustomFileException {
    String textFirstFile = "";
    String testSecondFile = CommonUtils.getRandomNumbers();
    log.info(String.format("Negative check. 1st file: \"%s\". 2nd file: \"%s\".", textFirstFile, testSecondFile));
    pathFirstFile = FileUtils.writeToFile(PATH_FIRST_FILE, textFirstFile);
    pathSecondFile = FileUtils.writeToFile(PATH_SECOND_FILE, testSecondFile);
    new Combine().getUniqueArrayFromTwoFiles(PATH_FIRST_FILE, PATH_SECOND_FILE);
  }

  @Test(description = "Negative checks: empty second file", expectedExceptions = CustomFileException.class,
      expectedExceptionsMessageRegExp = "^File is empty. File: " + PATH_SECOND_FILE)
  public void checkEmptySecondFile() throws CustomFileException {
    String textFirstFile = CommonUtils.getRandomNumbers();
    String testSecondFile = "";
    log.info(String.format("Negative check. 1st file: \"%s\". 2nd file: \"%s\".", textFirstFile, testSecondFile));
    pathFirstFile = FileUtils.writeToFile(PATH_FIRST_FILE, textFirstFile);
    pathSecondFile = FileUtils.writeToFile(PATH_SECOND_FILE, testSecondFile);
    new Combine().getUniqueArrayFromTwoFiles(PATH_FIRST_FILE, PATH_SECOND_FILE);
  }

  @Test(description = "Negative checks: data is not on one line in the first file",
      expectedExceptions = CustomFileException.class, expectedExceptionsMessageRegExp = "^File contains line feed. File: " +
      PATH_FIRST_FILE)
  public void checkLineFeedFirstFile() throws CustomFileException {
    String textFirstFile = "\n6";
    String testSecondFile = CommonUtils.getRandomNumbers();
    log.info(String.format("Negative check. 1st file: \"%s\". 2nd file: \"%s\".", textFirstFile, testSecondFile));
    pathFirstFile = FileUtils.writeToFile(PATH_FIRST_FILE, textFirstFile);
    pathSecondFile = FileUtils.writeToFile(PATH_SECOND_FILE, testSecondFile);
    new Combine().getUniqueArrayFromTwoFiles(PATH_FIRST_FILE, PATH_SECOND_FILE);
  }

  @Test(description = "Negative checks: data is not on one line in the first file",
      expectedExceptions = CustomFileException.class, expectedExceptionsMessageRegExp = "^File contains line feed. File: " +
      PATH_SECOND_FILE)
  public void checkLineFeedSecondFile() throws CustomFileException {
    String textFirstFile = "3 2 1";
    String testSecondFile = "5\n6";
    log.info(String.format("Negative check. 1st file: \"%s\". 2nd file: \"%s\".", textFirstFile, testSecondFile));
    pathFirstFile = FileUtils.writeToFile(PATH_FIRST_FILE, textFirstFile);
    pathSecondFile = FileUtils.writeToFile(PATH_SECOND_FILE, testSecondFile);
    new Combine().getUniqueArrayFromTwoFiles(PATH_FIRST_FILE, PATH_SECOND_FILE);
  }

  @Test(description = "Negative checks: wrong path to the first file", expectedExceptions = CustomFileException.class,
      expectedExceptionsMessageRegExp = "^File not found. File: " + PATH_FIRST_FILE)
  public void checkIncorrectFirstFilePath() throws CustomFileException {
    pathFirstFile = FileUtils.writeToFile(INCORRECT_PATH_FILE, "5 6");
    pathSecondFile = FileUtils.writeToFile(PATH_SECOND_FILE, "3 2 1");
    log.info(String.format("Negative check. 1st file: \"%s\". 2nd file: \"%s\".", INCORRECT_PATH_FILE, PATH_SECOND_FILE));
    new Combine().getUniqueArrayFromTwoFiles(PATH_FIRST_FILE, PATH_SECOND_FILE);
  }

  @Test(description = "Negative checks: wrong path to the second file", expectedExceptions = CustomFileException.class,
      expectedExceptionsMessageRegExp = "^File not found. File: " + PATH_SECOND_FILE)
  public void checkIncorrectSecondFilePath() throws CustomFileException {
    pathFirstFile = FileUtils.writeToFile(PATH_FIRST_FILE, "5 6");
    pathSecondFile = FileUtils.writeToFile(INCORRECT_PATH_FILE, "3 2 1");
    log.info(String.format("Negative check. 1st file: \"%s\". 2nd file: \"%s\".", PATH_FIRST_FILE, INCORRECT_PATH_FILE));
    new Combine().getUniqueArrayFromTwoFiles(PATH_FIRST_FILE, PATH_SECOND_FILE);
  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    if (pathFirstFile != null)
      FileUtils.deleteFile(pathFirstFile);
    if (pathSecondFile != null)
      FileUtils.deleteFile(pathSecondFile);
  }
}
