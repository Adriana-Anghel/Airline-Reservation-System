package constants;

import Data.Flight;
import Data.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Messages {

    public static String getUserAdded( User user ) {
        return " User with email " + user.getEmail()  + " was successfully added! ";
    }

    public static String getUserAlreadyExist () {
        return "User already exsists!";
    }

    public static String getCannotFindUser(String email) {
        return "Cannot find user with email: " + email;
    }

    public static String verifyPasswordLength() {
        return "Cannot add user! Password too short!";
    }

    public static String verifyBothPasswords() {
        return "Cannot add user! The passwords are different!";
    }

    public static String getIncorrectPassword() {
        return  "Incorrect password!";
    }

    public static String getAnotherUserIsConnected() {
        return  "Another user is already connected";
    }

    public static String getDisplayLoginUser(User user, LocalDateTime dateTime) {
        return  "User with email " +  user.getEmail() +  " is the current user started from "  + dateTime;
    }

    public static String getCannotLogoutUser(String email) {
        return "The user with email "  + email +  " was not connected! ";
    }

    public static String getUserLogout(String email, LocalDateTime dateTime) {
        return  "User with email "  + email + " successfully disconnected at " + dateTime;
    }

    public static String getNoConnectedUser() {
        return "There is no connected user!";
    }

    public static String getDisplayFlight(Flight flight) {
        return "Flight from " + flight.getFrom() + " to " + flight.getTo() + ", date " + flight.getDate() +
                ", duration: " + flight.getDuration();
    }

    public static String getNoFlightWithId(int flightId) {
        return "The flight with id " + flightId + " does not exist!";
    }

    public static String getCannotAddFlightWithId(int flightId) {
        return "Cannot add flight! There is already a flight with id = " + flightId;
    }

    public static String getAddedFligth(Flight flight) {
        return  "Flight from " + flight.getFrom() + " to " + flight.getTo() + ", date " + flight.getDate() + ", duration " + flight.getDuration() + " successfully added!";
    }

    public static String getUserAlreadyHasTicket(String email, int flightId) {
        return "The user with email " + email + " already have a ticket for flight with id " + flightId;
    }

    public static String getUserAddedFlight(String email, int flightId) {
        return "The flight with id " + flightId + " was successfully added for user with email " +  email;
    }

    public static String getUserDoesNotHaveTicket(String email, int flightId) {
        return "The user with email " + email  + " does not have a ticket for the flight with id " + flightId;
    }

    public static String getDeleteFlight(String email, int flightId) {
        return "The user with email " + email + " has successfully canceled his ticket for flight with id "  + flightId;
    }

    public static String getFlightDeleted(int flightId) {
        return "Flight with id " + flightId + " successfully deleted.";
    }

    public static String getNotifyUserForCancelFlight(String email, int flightId) {
        return "The user with email " + email + " was notified that the flight with id " + flightId + " was canceled!";
    }

    public static String getFlightsSavedInDatabase(LocalDateTime dateTime) {
        return "The flights was successfully saved in the database at " + dateTime;
    }

    public static String getUsersSavedInDatabase(LocalDateTime dateTime){
        return "The users were successfully saved in the database at " + dateTime;
    }
}
