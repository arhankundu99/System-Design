# LLD

## To do
- Cricbuzz
- Stock broker
- Ecommerce
- Job Scheduler
- Tic tac toe

## UML Class Relationships
---

## 1. Inheritance (Generalization)

Represents an **"is-a"** relationship where a subclass derives attributes and behaviors from a superclass.

```text
Notation:
+-------------+                     +-------------+
|    Child    |────────────────────▷|   Parent    |
+-------------+                     +-------------+
Line: Solid
Head: Hollow (unfilled) closed triangle pointing toward Parent
```

### Example

```text
+-------------+                     +-------------+
|     Dog     |────────────────────▷|   Animal    |
+-------------+                     +-------------+
```

```javascript
class Animal {}

class Dog extends Animal {
  // Dog is-an Animal
}
```

---

## 2. Directed Association

Represents a **"has-a" / "uses"** relationship where one class holds a reference to another. The referenced object has an independent lifecycle and is passed in from outside.

```text
Notation:
+-------------+                     +-------------+
|   Source    |────────────────────>|   Target    |
+-------------+                     +-------------+
Line: Solid
Head: Open stick arrow pointing toward Target
```

### Example

```text
+-------------+                     +-------------+
|   Driver    |────────────────────>|     Car     |
+-------------+                     +-------------+
```

```javascript
class Driver {
  // Driver receives Car in the constructor (Car exists outside Driver)
  constructor(car) {
    this.car = car;
  }
}
```

---

## 3. Composition

Represents a strong **"owns-a"** part-whole relationship. The child part cannot exist without the parent whole; its entire lifecycle is managed and created internally.

```text
Notation:
+-------------+                     +-------------+
|    Whole    |◆────────────────────|    Part     |
+-------------+                     +-------------+
Line: Solid
Head: Solid (filled) black diamond attached to the Whole
```

### Example

```text
+-------------+                     +-------------+
|    House    |◆────────────────────|    Room     |
+-------------+                     +-------------+
```

```javascript
class House {
  // House creates Room inside itself (Room lifecycle dies with House)
  constructor() {
    this.room = new Room();
  }
}
```

---

## Quick Reference Summary

| Relationship | Symbol | Key Concept | Code Cue |
| :--- | :--- | :--- | :--- |
| **Inheritance** | `────▷` | *"is-a"* | `class Dog extends Animal` |
| **Association** | `────>` | *"uses / has-a"* | Injected via constructor (`this.car = car`) |
| **Composition** | `◆────` | *"owns-a"* | Instantiated internally (`this.room = new Room()`) |
