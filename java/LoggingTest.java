import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingTest {
    static Logger logger = Logger.getLogger("loggertest");

    public static void main(String[] args) throws IOException {
        FileHandler handler = new FileHandler("log.txt");
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);

        logger.setLevel(Level.INFO);
        logger.info("logging success");
        try(FileWriter writer = new FileWriter(new File(Paths.get("file.txt").toUri()))) {
            writer.write("Hello world file");
        } catch(IOException e) {
            logger.log(Level.SEVERE, "Exception occured", e);
        }
    }
}