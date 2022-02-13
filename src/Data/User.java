package Data;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String email;
    private String name;
    private String password;
    private List <Flight> userFlights = new ArrayList<>();


    public User(String email, String nume, String password) {
        this.email = email;
        this.name = nume;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", nume='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public List<Flight> getUserFlights() {
        return userFlights;
    }

    public void addFlight(Flight flight) {
        userFlights.add(flight);
    }

    public void deleteFlight(Flight flight) {
        userFlights.remove(flight);
    }
}
