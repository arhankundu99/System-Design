# Singleton
## Usage
- Use when there is a need of creating only a single object, like a db instance.

## Code
### Singleton.java
```java
class Singleton {
    private static Singleton instance;
    private Singleton() {
        if (instance != null) {
            // To avoid reflection to call the constructor
            throw new RuntimeException("Constructor called even when instance is not null.");
        }
    }

    public static Singleton getInstance() {
        if (instance == null) {
            // Make it thread safe
            synchronized(Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }

    public void invoke() {
        System.out.println("Singleton instance method invoked.");
    }
}
```

### Main.java
```java
public class Main {
    public static void main(String[] args) {
        Singleton instance = Singleton.getInstance();
        instance.invoke();
    }
}
```

## Notes
- When we write `synchronized(Singleton.class)`, we are effectively telling Java to lock on the Class object associated with the Singleton class. `Every class in Java has a corresponding Class object that represents the class at runtime`, and Singleton.class is a reference to that object. 
- We cannot use `synchronized(this)` in singleton because the object is not created for the first time when `getInstance()` is called.
- Example of `synchronized(this)`
```java
class Counter {
    private int count = 0;

    public void increment() {
        synchronized(this) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}
```

- If we want a finer grain of control, we can create an object and use this as lock
```java
class Counter {
    private int count = 0;
    private final Object lock = new Object();  // Custom lock object

    public void increment() {
        synchronized(lock) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }
}
```