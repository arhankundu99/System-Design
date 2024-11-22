package models;

public interface ISheet {

    void addRowAtEnd();
    void addColumnAtEnd();

    void removeRow(int rowNumber);
    void removeColumn(int columnNumber);
}
