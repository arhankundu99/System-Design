package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sheet implements ISheet{
    private List<Row> rows;
    private final int DEFAULT_ROW_SIZE = 12;
    private final String id;
    private final String name;
    public Sheet(String name) {
        id = UUID.randomUUID().toString();
        this.name = name;
        rows = new ArrayList<>();
    }
    public void addRowAtEnd() {
        if (rows.isEmpty()) {
            rows.add(new Row(DEFAULT_ROW_SIZE));
        } else {
            rows.add(new Row(rows.getFirst().size()));
        }
    }
    public void addColumnAtEnd() {
        for (Row row : rows) {
            row.addCell();
        }
    }

    public void addRow(int row) {
        if (row < 0 || row >= rows.size()) {
            throw new IllegalArgumentException("Row index out of bounds");
        }

        Row newRow = new Row(rows.getFirst().size());
        rows.add(row, newRow);
    }

    public void removeRow(int row) {
        if (row < 0 || row >= rows.size()) {
            throw new IllegalArgumentException("Row index out of bounds");
        }
        rows.remove(row);
    }

    public void removeColumn(int column) {
        if (column < 0 || rows.isEmpty() || column >= rows.get(0).size()) {
            throw new IllegalArgumentException("Column index out of bounds");
        }
        for (Row row : rows) {
            row.removeCell(column);
        }
    }
}
