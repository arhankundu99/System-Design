package mode;

import model.Log;

public class ConsoleMode implements AppendMode {
    public void append(Log log) {
        System.out.println(log.toString());
    }
}
