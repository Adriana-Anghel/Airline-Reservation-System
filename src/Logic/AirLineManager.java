package Logic;

import Data.Flight;
import Data.User;
import constants.Messages;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AirLineManager {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/final_project";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private final WriterManager writer = new WriterManager();
    private List<User> allUsers = new ArrayList<>();
    private List<Flight> allFlights = new ArrayList<>();
    private User currentUser = null; //Daca nu avem niciun utilizator conectat, atunci avem null.

    public void instantiereBufferedWriter() {
        writer.instantiereBufferedWriter();
    }

    public void afisareUtilizatori() {
        for (User user : allUsers) {
            System.out.println(user);
        }
    }

    public void signUp(String[] comenzi) {
        String email = comenzi[1];
        String nume = comenzi[2];
        String parola = comenzi[3];
        String parola_confirmata = comenzi[4];

        for (User user : allUsers) {
            if (user.getEmail().equals(email)) {
                writer.write(Messages.getUserAlreadyExist());
                return;
            }
        }

        if (parola.length() < 8) {
            writer.write(Messages.verifyPasswordLength());
            return;
        }

        if (!parola.equals(parola_confirmata)) {
            writer.write(Messages.verifyBothPasswords());
            return;
        }

        User user = new User(email, nume, parola);
        allUsers.add(user);
        writer.write(Messages.getUserAdded(user));
    }


    public void logIn(String[] comenzi) {
        String email = comenzi[1];
        String logInPassword = comenzi[2];

        Optional<User> userOptional = allUsers.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();

        if (userOptional.isEmpty()) {
            writer.write(Messages.getCannotFindUser(email));
            return;
        }

        User user = userOptional.get();
        if (!logInPassword.equals(user.getPassword())) {
            writer.write(Messages.getIncorrectPassword());
            return;
        }

        if (currentUser != null) {
            writer.write(Messages.getAnotherUserIsConnected());
        } else {
            currentUser = user;      // Utilizatorul va deveni user curent
            LocalDateTime.now();
            writer.write(Messages.getDisplayLoginUser(user, LocalDateTime.now()));
        }
    }


    public void logOut(String[] comenzi) {
        String email = comenzi[1];

        if (currentUser == null) {
            return;
        }

        if (!currentUser.getEmail().equals(email)) {
            writer.write(Messages.getCannotLogoutUser(email));
            return;
        }
        writer.write(Messages.getUserLogout(email, LocalDateTime.now()));
        currentUser = null;
    }


    public void displayMyFlights() {
        if (currentUser == null) {
            writer.write(Messages.getNoConnectedUser());
        } else {

            List<Flight> userFlight = currentUser.getUserFlights();
            userFlight.forEach(flight -> {
                writer.write(Messages.getDisplayFlight(flight));
            });
        }
    }

    public void addFlightDetails(String[] comenzi) {
        System.out.println(comenzi[1]);
        int flightId = Integer.parseInt(comenzi[1]);
        String from = comenzi[2];
        String to = comenzi[3];
        String dateAsString = comenzi[4];

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateAsString, dateTimeFormatter);
        int duration = Integer.parseInt(comenzi[5]);


        boolean flightWithIdAlreadyExists = allFlights.stream()
                .anyMatch(flight -> flight.getId() == flightId);

        if (flightWithIdAlreadyExists) {
            writer.write(Messages.getCannotAddFlightWithId(flightId));
            return;
        }

        Flight flight = new Flight(flightId, from, to, date, duration);
        allFlights.add(flight);
        writer.write(Messages.getAddedFligth(flight));
    }

    public void addUserFlight(String[] comenzi) {
        int flightId = Integer.parseInt(comenzi[1]);
        if (currentUser == null) {
            writer.write(Messages.getNoConnectedUser());
            return;
        }

        Optional<Flight> flightOptional = allFlights.stream()
                .filter(flight -> flight.getId() == flightId)
                .findFirst();

        if (flightOptional.isEmpty()) {
            writer.write(Messages.getNoFlightWithId(flightId));
            return;
        }

        boolean userAlreadyHasTicket = currentUser.getUserFlights().stream().anyMatch(flight -> flight.getId() == flightId);
        if (userAlreadyHasTicket) {
            writer.write(Messages.getUserAlreadyHasTicket(currentUser.getEmail(), flightId));
            return;
        }
        currentUser.addFlight(flightOptional.get());
        writer.write(Messages.getUserAddedFlight(currentUser.getEmail(), flightId));

    }

    public void cancelUserFlight(String[] comenzi) {
        int flightId = Integer.parseInt(comenzi[1]);
        if (currentUser == null) {
            writer.write(Messages.getNoConnectedUser());
        }

        boolean existFlight = allFlights.stream().anyMatch(flight -> flight.getId() == flightId);

        if (!existFlight) {
            writer.write(Messages.getNoFlightWithId(flightId));
            return;
        }

        Optional<Flight> flightOptional = currentUser.getUserFlights().stream()
                .filter(flight -> flight.getId() == flightId)
                .findFirst();
        if (flightOptional.isEmpty()) {
            writer.write(Messages.getUserDoesNotHaveTicket(currentUser.getEmail(), flightId));
            return;
        }

        Flight flight = flightOptional.get();
        currentUser.deleteFlight(flight);
        writer.write(Messages.getDeleteFlight(currentUser.getEmail(), flightId));
    }


    public void deleteFlight(String[] comenzi) {
        int flightId = Integer.parseInt(comenzi[1]);

        Optional<Flight> flightOptional = allFlights.stream()
                .filter(flight -> flight.getId() == flightId)
                .findFirst();

        if (flightOptional.isEmpty()) {
            writer.write(Messages.getNoFlightWithId(flightId));
            return;
        }

        Flight flight = flightOptional.get();
        allFlights.remove(flight);
        writer.write(Messages.getFlightDeleted(flightId));

        for (User user : allUsers) {
            if (user.getUserFlights().contains(flight)) {
                user.deleteFlight(flight);
                writer.write(Messages.getNotifyUserForCancelFlight(user.getEmail(), flightId));
            }
        }
    }

    public void displayFlights() {
        allFlights.forEach(flight -> writer.write(Messages.getDisplayFlight(flight)));
    }


    public void persistFlights() {
        String insertFlightSQL = "INSERT INTO flights VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertFlightSQL)) {

            for (Flight flight : allFlights) {
                preparedStatement.setInt(1, flight.getId());
                preparedStatement.setString(2, flight.getFrom());
                preparedStatement.setString(3, flight.getTo());
                preparedStatement.setDate(4, Date.valueOf(flight.getDate()));
                preparedStatement.setInt(5, flight.getDuration());

                preparedStatement.executeUpdate();
            }
            writer.write(Messages.getFlightsSavedInDatabase(LocalDateTime.now()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void persistUsers() {
        String insertUserSQL = "INSERT INTO users (email, name, password) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {

            for (User user : allUsers) {
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getPassword());

                preparedStatement.executeUpdate();
            }
            writer.write(Messages.getUsersSavedInDatabase(LocalDateTime.now()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finalCloseBufferedWriter() {
        writer.closeMethod();
    }

    public List<Flight> getAllFlights() {
        return allFlights;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void setAllFlights(List<Flight> flights) {
        this.allFlights = flights;
    }

    public void setAllUsers(List<User> users) {
        this.allUsers = users;
    }
}

