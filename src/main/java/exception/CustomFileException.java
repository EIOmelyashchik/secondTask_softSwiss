package exception;

/**
 * User: E.Omelyashchik
 * Date: 19.05.20
 */
public class CustomFileException extends Exception {

  public CustomFileException(ErrorType errorType, String pathToFile) {
    super(errorType + String.format(" File: %s", pathToFile));
  }

  public CustomFileException(String message, String pathToFile) {
    super(message + String.format(" File: %s", pathToFile));
  }
}


