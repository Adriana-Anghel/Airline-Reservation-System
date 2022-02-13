import Logic.AirLineManager;
import Logic.ReaderManager;
import constants.Commands;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        AirLineManager manager = new AirLineManager();

        BufferedReader reader = ReaderManager.creareReader();

        String linie = reader.readLine();

        manager.instantiereBufferedWriter();
        while (linie != null) {
            //proceseaza
            System.out.println("--------rand nou---------");
            String[] comenzi = linie.split(" ");
            Commands command;
            try {
                command = Commands.valueOf(comenzi[0]);
            } catch (IllegalArgumentException e) {
                command = Commands.DEFAULT_COMMAND;
            }

            System.out.println(command);

            switch (command) {
                case SIGNUP: {

                    // in final trebuie sa fie doar un apel de metoda
//                    manager.afisareUtilizatori();
                    manager.signUp(comenzi);
//                    manager.afisareUtilizatori();
                    break;
                }
                case LOGIN: {
                    manager.logIn(comenzi);
                    break;
                }

                case LOGOUT: {
                    manager.logOut(comenzi);
                    break;
                }

                case DISPLAY_MY_FLIGHTS: {
                    manager.displayMyFlights();
                    break;
                }

                case ADD_FLIGHT_DETAILS: {
                    manager.addFlightDetails(comenzi);
                    break;
                }

                case ADD_FLIGHT:{
                    manager.addUserFlight(comenzi);
                    break;
                }

                case CANCEL_FLIGHT:{
                    manager.cancelUserFlight(comenzi);
                    break;
                }
                case DELETE_FLIGHT:{
                    manager.deleteFlight(comenzi);
                    break;
                }
                case DISPLAY_FLIGHTS:{
                    manager.displayFlights();
                    break;
                }

                case PERSIST_FLIGHTS:{
                    manager.persistFlights();
                    break;
                }

                case PERSIST_USERS:{
                    manager.persistUsers();
                    break;
                }

                case DEFAULT_COMMAND: {
                    System.out.println("Comanda gresita!");
                    break;
                }
            }

            linie = reader.readLine();
        }

        manager.finalCloseBufferedWriter();
    }
}
