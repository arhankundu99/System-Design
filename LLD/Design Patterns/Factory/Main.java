public class Main {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        Shape circle = shapeFactory.getShape(ShapeType.CIRCLE);
        Shape square = shapeFactory.getShape(ShapeType.SQUARE);

        circle.draw();
        square.draw();
    }
}