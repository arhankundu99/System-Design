package models;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private String name;
    private List<String> params;

    public Command(String input) {
        params = new ArrayList<>();

        String[] tokens = input.split(" ");
        this.name = tokens[0];

        for (int i = 1; i < tokens.length; i++) {
            params.add(tokens[i]);
        }
    }

    public String getName() {
        return this.name;
    }

    public List<String> getParams() {
        return this.params;
    }
}
