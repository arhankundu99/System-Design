package models;

public class Cell {
    Object data;
    private FontType fontType;
    private int fontSize;
    private FontStyle fontStyle;

    public Cell(Object data, FontType fontType, int fontSize, FontStyle fontStyle) {
        this.fontType = fontType;
        this.fontSize = fontSize;
        this.fontStyle = fontStyle;
        this.data = data;
    }
    public FontType getFontType() {
        return fontType;
    }
    public int getFontSize() {
        return fontSize;
    }
    public FontStyle getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontType(FontType fontType) {
        this.fontType = fontType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
