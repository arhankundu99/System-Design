package mode;

import model.Log;

import java.io.FileWriter;
import java.io.IOException;

public class FileMode implements AppendMode{
    String filePath;
    public FileMode(String filePath) {
        this.filePath = filePath;
    }
    public void append(Log log) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            fw.write(log.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
