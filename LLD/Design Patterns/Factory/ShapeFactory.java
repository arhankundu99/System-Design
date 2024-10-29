public class ShapeFactory {
    Shape getShape(ShapeType shapeType) {
        switch (shapeType) {
            case CIRCLE: {
                return new Circle();
            }
            case SQUARE: {
                return new Square();
            }
            default: 
                return null;
        }
    }
}