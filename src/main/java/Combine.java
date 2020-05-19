import exception.CustomFileException;
import exception.ErrorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.FileUtils;
import utils.CommonUtils;

import java.util.Arrays;
import java.util.List;

/**
 * User: E.Omelyashchik
 * Date: 19.05.20
 */
public class Combine {

  private Logger log = LogManager.getLogger();

  /**
   * Combines two integer one-dimensional arrays from files into one.
   *
   * @param pathFirstFile  Path to the first file with array
   * @param pathSecondFile Path to the second file with array
   * @return One-dimensional sorted array of unique values
   */
  public int[] getUniqueArrayFromTwoFiles(String pathFirstFile, String pathSecondFile) throws CustomFileException {
    int[] arrayFistFile = getArrayFromFileWithVerification(pathFirstFile);
    int[] arraySecondFile = getArrayFromFileWithVerification(pathSecondFile);
    int[] result = CommonUtils.combineArrays(arrayFistFile, arraySecondFile);
    log.info(String.format("Result: \"%s\".", Arrays.toString(result)));
    return result;
  }

  private int[] getArrayFromFileWithVerification(String pathFile) throws CustomFileException {
    List<String> text = FileUtils.readFile(pathFile);
    if (text.size() == 0) {
      CustomFileException exception = new CustomFileException(ErrorType.FILE_IS_EMPTY, pathFile);
      log.info(String.format("Result: \"%s\".", exception.getMessage()));
      throw exception;
    }
    if (text.size() != 1) {
      CustomFileException exception = new CustomFileException(ErrorType.LINE_FEED, pathFile);
      log.info(String.format("Result: \"%s\".", exception.getMessage()));
      throw exception;
    }
    try {
      return CommonUtils.transformFromStringToArray(text.get(0));
    } catch (NumberFormatException e) {
      CustomFileException exception = new CustomFileException(ErrorType.UNACCEPTABLE_SYMBOLS + " Details: " +
          e.getMessage(), pathFile);
      log.info(String.format("Result: \"%s\".", exception.getMessage()));
      throw exception;
    }
  }
}
