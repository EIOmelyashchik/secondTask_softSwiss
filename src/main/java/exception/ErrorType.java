package exception;

/**
 * User: E.Omelyashchik
 * Date: 19.05.20
 */
public enum ErrorType {

  FILE_NOT_FOUND("File not found."),
  FILE_IS_EMPTY("File is empty."),
  LINE_FEED("File contains line feed."),
  UNACCEPTABLE_SYMBOLS("File contains unacceptable symbols.");

  private String error;

  ErrorType(String error) {
    this.error = error;
  }

  @Override
  public String toString() {
    return error;
  }
}
