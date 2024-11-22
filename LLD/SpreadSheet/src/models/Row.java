package models;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private final List<Cell> cells;
    private final int DEFAULT_FONT_SIZE = 12;

    public Row(int initialColumns) {
        cells = new ArrayList<>();
        for (int i = 0; i < initialColumns; i++) {
            cells.add(new Cell(FontType.ARIAL, DEFAULT_FONT_SIZE, FontStyle.REGULAR));
        }
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void addCell() {
        cells.add(new Cell(FontType.ARIAL, DEFAULT_FONT_SIZE, FontStyle.REGULAR));
    }

    public void insertCell(int index) {
        cells.add(index, new Cell(FontType.ARIAL, DEFAULT_FONT_SIZE, FontStyle.REGULAR));
    }

    public void removeCell(int index) {
        cells.remove(index);
    }

    public int size() {
        return cells.size();
    }
}
