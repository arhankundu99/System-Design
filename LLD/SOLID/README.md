# SOLID Principles
## Single Responsibility Principle
This principle states that a class should have only one responsibility.

## Pros
- Makes code more modular.
- Easy to debug.

## Cons
- Can result in creation of too many classes.

## Example
```java
// The below class has multiple responsibilities.
class Student {
    void registerStudent() {
        
    }
    int calculateTotalMarks() {
        
    }
    
}

// We can break down the class into multiple class.
class StudentRegistrationHandler {
    void register(int studentId) {
        
    }
}

class StudentMarksHandler {
    int calculateTotalMarks(int studentId) {
        
    }
}
```

## Open Closed Principle
This principle states that the classes should be open for extension and closed for modification.
```java
// In the below example, if we want to add another notification type, we would have to modify the NotificationService class which violates the principle
public class NotificationService {
    void handleNotification(String notificationType, String message) {
        if (notificationType.equals("SMS")) {
            // handle sms
        } else if (notificationType.equals("Email")) {
            // handle email
        }
    }
}
public class NotificationController {
    NotificationService notificationService;
    public NotificationController() {
        notificationService = new NotificationService();
    }
    public void handleNotification(String notificationType, String message) {
        notificationService.handleNotification(notificationType, message);
    }
}


// Instead of the above code, we can do the below
public class Notification {
    String message;
    public Notification(String message) {
        this.message = message;
    }
}

public interface INotificationService {
    void handleNotification(Notification notification);
}

public class SMSNotificationService implements INotificationService {
    void handleNotification(Notification notification) {
        System.out.println("Handling SMS Notification");
    }
}

public class EmailNotificationService implements INotificationService {
    void handleNotification(Notification notification) {
        System.out.println("Handling Email Notification");
    }
}

public class NotificationServiceFactory {
    public INotificationService getNotificationService(String notificationType) {
        return switch (notificationType) {
            case "Email" -> new EmailNotificationService();
            case "SMS" -> new SMSNotificationService();
            default -> null;
        };
    }
}

public class NotificationController {
    NotificationServiceFactory notificationServiceFactory;
    public NotificationController() {
        this.notificationServiceFactory = new NotificationServiceFactory();
    }
    void handleNotification(String notificationType, String message) {
        INotificationService notificationService = notificationServiceFactory.getNotificationService(notificationType);
        Notification notification = new Notification(message);
        notificationService.handleNotification(notification);
    }
}

```

## Liskov Substitution Principle
This principle states that subclasses should be substitutable with base classes and should not give any wrong results.

```java
public class Rectangle {
    int x;
    int y;
    
    Rectangle(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    int getX() {
        return x;
    }
    
    int getY() {
        return y;
    }
    
    void setX() {
        return x;
    }
    
    void setY() {
        return y;
    }
}

public class Square extends Rectangle {
    Square(int s) {
        super(s, s);
    }
    
    int get() {
        return this.x;
    }
    
    @Override
    void setW(int s) {
        this.x = s;
        this.y = s;
    }
    
    @Override
    void setH(int s) {
        this.x = s;
        this.y = s;
    }
}

public class Main {
    public static void main(String[] args) {
        Rectangle r1 = new Square(5);
        System.out.println(getArea(r1));
    }
    
    public static int getArea(Rectangle rectangle) {
        int w = rectangle.getW();
        int h = rectangle.getH();
        
        int newH = 10;
        rectangle.setH(newH);
        int expectedArea = newH * w;
        int actualArea = rectangle.getX() * rectangle.getY();
        
        // Here actual area and expected area are not same!
        
        return actualArea;
    }
}
```

## Interface segregation principle
This principle states that interface should be split in such a way that any class which implements this should be able to use all the methods

```java
public class Document {
    
}
public interface IMachine{
    void print(Document doc);
    void scan(Document doc);
    void fax(Document doc);
}

// Now if we want to create a printer class which only has print method, then other methods would remain empty
public class Printer implements IMachine {
    void print(Document doc) {
        System.out.println("Print method is implemented for printer.");
    }
    
    void scan(Document doc) {
        // Not implemented
    }
    
    void fax(Document doc) {
        // Not implemented
    }
}


// Now instead of this, we split the interface like this
public interface IPrinter {
    void print(Document doc);
}

public interface IScanner {
    void scan(Document doc);
}

public interface IFaxMachine {
    void fax(Document doc);
}

public class MyPrinter implements IPrinter {
    void print(Document doc) {
        
    }
}
```

## Dependency Inversion Principle
This principle states that any class which uses a dependency should do so using an interface / wrapper, so that if the dependency changes, we can update our code to use the new dependency in minimal changes

```java
// In the below class, If we want to use StripePaymentClient instead of PaypalClient, we would have to do so many changes.
class PaymentService {
    PaypalClient paypalClient;
    
    void makePayment(int price, String userId) {
        paypalClient.makePayment(userId, price);
    }
}


// Instead of this, do below
interface PaymentProcessor {
    void makePayment(String userId, int price);
}

class PaypalPaymentProcessor implements PaymentProcessor {
    void makePayment(String userId, int price) {
        
    } 
}

class StripePaymentProcessor implements PaymentProcessor {
    void makePayment(String userId, int price) {
        
    }
}

class PaymentService {
    PaymentProcessor paymentProcessor;
    public PaymentService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
    
    public void makePayment(int price, String userId) {
        this.paymentProcessor.makePayment(userId, price);
    }
}
```