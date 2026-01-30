package org.xptm.jsontest;

import java.io.*;
import java.util.Base64;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONTokener;

/**
  FileName: JsonTest.java<br />
  Author : Rafael Moreno-Marte<br />
  Description: <br/>
          <b>This program makes a Byte-Based stream object and then turns it into a base64 string<br/>
           to allow storing any information of any object in a json format.<br/><br/></b>
*/
public class JsonTest{
    public static void main(String[] args) throws IOException{
        Account acc = new Account();
        Address addr = new Address();
        Person person = new Person(acc, addr);

        person.display();
        encodeObject(person);
        decodeObject();
    }

    public static void encodeObject(Person p){
        // Serialize Person objects to Base64
        Map<String, String> personsBase64 = new HashMap<>();
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(p);
            oos.close();
            String base64Encoded = Base64.getEncoder().encodeToString(baos.toByteArray());
            System.out.println("===================");
            System.out.println("Encoded string before JSON: " + base64Encoded);
            System.out.println("===================");
            personsBase64.put("person1", base64Encoded);

            // Write to JSON file
            writePersonsToJson(personsBase64, "persons.json");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decodeObject(){
        // Read from JSON file
        Person personsMap = readPersonsFromJson("persons.json");

        // Display the deserialized persons
        personsMap.display();
    }

    public static void writePersonsToJson(Map<String, String> persons, String filename) {
        JSONObject jsonObject = new JSONObject();
        JSONObject personsObject = new JSONObject();

        for (Map.Entry<String, String> entry : persons.entrySet()) {
            personsObject.put(entry.getKey(), entry.getValue());
        }

        jsonObject.put("persons", personsObject);

        try (FileWriter file = new FileWriter(filename)) {
            file.write(jsonObject.toString(4)); // 4 is the indentation factor
            System.out.println("Successfully wrote JSON to file: " + filename);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the JSON file.");
            e.printStackTrace();
        }
    }

    public static Person readPersonsFromJson(String filename) {
//        Map<String, Person> personsMap = new HashMap<>();
        Person person = null;

        try (FileReader reader = new FileReader(filename)) {
            JSONObject jsonObject = new JSONObject(new JSONTokener(reader));
            JSONObject personsObject = jsonObject.getJSONObject("persons");

            for (String key : personsObject.keySet()) {
                String base64Encoded = personsObject.getString(key);
                byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);

                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(decodedBytes));
                    person = (Person) ois.readObject();
                    ois.close();

//                    personsMap.put(key, person);
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Error deserializing person with key: " + key);
                    e.printStackTrace();
                }
            }
        } catch (IOException | JSONException e) {
            System.err.println("An error occurred while reading the JSON file.");
            e.printStackTrace();
        }

//        return personsMap;
        return person;
    }
}

class Account implements Serializable{
    private int accountNum;
    private float accountBalance;
    private String accountLocation;

    // Default constructor
    public Account() {
        this.setAccountBalance(100.43f);
        this.setAccountLocation("Maryland");
        this.setAccountNum(29739);
    }

    /**
     * Gets the account number.
     * @return the account number
     */
    public int getAccountNum() {
        return accountNum;
    }

    /**
     * Sets the account number.
     * @param accountNum the account number to set
     */
    public void setAccountNum(int accountNum) {
        this.accountNum = accountNum;
    }

    /**
     * Gets the account balance.
     * @return the account balance
     */
    public float getAccountBalance() {
        return accountBalance;
    }

    /**
     * Sets the account balance.
     * @param accountBalance the account balance to set
     */
    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * Gets the account location.
     * @return the account location
     */
    public String getAccountLocation() {
        return accountLocation;
    }

    /**
     * Sets the account location.
     * @param accountLocation the account location to set
     */
    public void setAccountLocation(String accountLocation) {
        this.accountLocation = accountLocation;
    }

    /**
     * Displays the account details.
     */
    public void display() {
        System.out.println("Account Details:");
        System.out.println("----------------");
        System.out.println("Account Number: " + this.accountNum);
        System.out.println("Balance: $" + this.accountBalance);
        System.out.println("Location: " + this.accountLocation);
        System.out.println("----------------");
    }
}

class Address implements Serializable{
    private String city;
    private String state;
    private String country;
    private String zipcode;

    // Default constructor
    public Address() {
        this.setCity("Las Vegas");
        this.setCountry("United States");
        this.setState("Nevada");
        this.setZipcode("89145");
    }

    /**
     * Gets the city.
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the state.
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state.
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the country.
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country.
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the zipcode.
     * @return the zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets the zipcode.
     * @param zipcode the zipcode to set
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Displays the address details.
     */
    public void display() {
        System.out.println("Address:");
        System.out.println("----------------");
        System.out.println("City: " + this.city);
        System.out.println("State: " + this.state);
        System.out.println("Country: " + this.country);
        System.out.println("Zip Code: " + this.zipcode);
        System.out.println("----------------");
    }
}

class Person implements Serializable{
    private String name;
    private Address address;
    private Account account;

    // Default constructor
    public Person(Account acc, Address addr) {
        this.setAccount(acc);
        this.setAddress(addr);
        this.setName("The Ghoul");
    }

    /**
     * Gets the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address.
     * @return the address
     */
    public void getAddress() {
        address.display();
    }

    /**
     * Sets the address.
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets the account.
     * @return the account
     */
    public float getAccount() {
        return account.getAccountBalance();
    }

    /**
     * Sets the account.
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Displays the person's details.
     */
    public void display() {
        System.out.println("Person Details:");
        System.out.println("---------------");
        System.out.println("Name: " + this.name);

        if (this.address != null) {
            this.getAddress();
        } else {
            System.out.println("Address: Not available");
        }

        if (this.account != null) {
            System.out.println("Account: " + this.getAccount());
        } else {
            System.out.println("Account: Not available");
        }
        System.out.println("---------------");
    }
}
