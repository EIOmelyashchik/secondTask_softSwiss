package utils;

import exception.CustomFileException;
import exception.ErrorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * User: E.Omelyashchik
 * Date: 19.05.20
 */
public class FileUtils {

  private static Logger log = LogManager.getLogger();

  public synchronized static List<String> readFile(String filePath) throws CustomFileException {
    try {
      log.info(String.format("Read file \"%s\".", filePath));
      return Files.readAllLines(Paths.get(filePath));
    } catch (IOException ex) {
      CustomFileException exception = new CustomFileException(ErrorType.FILE_NOT_FOUND, filePath);
      log.info(String.format("File read error: \"%s\".", exception.getMessage()));
      throw exception;
    }
  }

  public synchronized static Path writeToFile(String filePath, String text) throws CustomFileException {
    try {
      log.info(String.format("Write \"%s\" to the file \"%s\".", text, filePath));
      return Files.write(Paths.get(filePath), text.getBytes(StandardCharsets.UTF_8));
    } catch (IOException ex) {
      CustomFileException exception = new CustomFileException(ex.getMessage(), filePath);
      log.info(String.format("File write error: \"%s\".", exception.getMessage()));
      throw exception;
    }
  }

  public synchronized static void deleteFile(Path path) {
    try {
      log.info(String.format("Delete file \"%s\".", path));
      Files.delete(path);
    } catch (IOException ex) {
      ex.printStackTrace();
      log.error(String.format("File delete error: \"%s\".", ex.getMessage()));
    }
  }
}
