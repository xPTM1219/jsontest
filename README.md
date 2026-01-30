# JavaTest

## Old code

```java
// Old code of serializing and Deserializing an object
// This is before json implementation
ObjectOutputStream oos = new ObjectOutputStream(baos);
oos.writeObject(p);
oos.close();

byte[] bytes = baos.toByteArray();
System.out.println("========================================");
System.out.println(Base64.getEncoder().encodeToString(bytes));

// Optional: Deserialize the Base64 string back to a Person object
byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
ObjectInputStream ois = new ObjectInputStream(bais);
Person decodedPerson = (Person) ois.readObject();
ois.close();

// System.out.println("\nDecoded Person Object Details:");
// decodedPerson.display();
```

```java
// In case we want to reuse a map
public static void decodeObject(){
        // Read from JSON file
        Map<String, Person> personsMap = readPersonsFromJson("persons.json");

        // Display the deserialized persons
        for (Map.Entry<String, Person> entry : personsMap.entrySet()) {
            System.out.println("Deserialized from JSON " + entry.getKey() + ":");
            entry.getValue().display();
        }
    }
```
