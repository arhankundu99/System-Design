# Factory design pattern
## Usage
- Use this pattern when there are many classes inheriting from an interface or abstract class.
- Provides centralised logic to create the objects.

## Code example

### Shape.java
```java
public interface Shape {
    void draw();
}
```

### Circle.java
```java
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Circle::draw()");
    }
}
```

### Square.java
```java
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Square::draw()");
    }
}
```

### ShapeType.java
```java
public enum ShapeType {
    CIRCLE,
    RECTANGLE,
    SQUARE
}
```

### ShapeFactory.java
```java
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
```

### Main.java
```java
public class Main {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();

        Shape circle = shapeFactory.getShape(ShapeType.CIRCLE);
        Shape square = shapeFactory.getShape(ShapeType.SQUARE);

        circle.draw();
        square.draw();
    }
}
```
