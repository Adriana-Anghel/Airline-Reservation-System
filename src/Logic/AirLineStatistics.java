package Logic;

import Data.Flight;
import Data.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AirLineStatistics {

    public static void main(String[] args) {
        AirLineManager airLineManager = databaseAirLine();

        // 1st example
/*        String mostUsedCityAsDepartureForFlights = findMostUsedCityAsDepartureForFlights(airLineManager);
        System.out.println(mostUsedCityAsDepartureForFlights);*/

        //2nd example
 /*       User userWhoTravelTheMost= findUserWhoTravelTheMost(airLineManager);
        System.out.println(userWhoTravelTheMost);*/

        //3rd example
/*        List<User> allUsersWhoTraveledToRoma = findAllUsersWhoTraveledToCity(airLineManager, "Roma");
        System.out.println(allUsersWhoTraveledToRoma);*/

        //4th example
/*        List<Flight> allFlightsBetweenDates = findAllFlightsBetweenDates(airLineManager, LocalDate.of(2022, 1, 1), LocalDate.of(2022, 2, 13));
        System.out.println(allFlightsBetweenDates);*/

        //5th example
/*        Flight shortestFlight = findShortestFlight(airLineManager);
        System.out.println(shortestFlight);*/

        //6th example
/*        List<User> allUsersWhoTraveledIn = findAllUsersWhoTraveledIn(airLineManager, LocalDate.of(2022, 2, 2));
        System.out.println(allUsersWhoTraveledIn);*/

    }

    private static AirLineManager databaseAirLine() {

        Flight flight1 = new Flight(1, "Otopeni", "Sevillia", LocalDate.of(2021, 9,27), 180);
        Flight flight2 = new Flight(2, "Brasov", "Paris", LocalDate.of(2021, 10,16), 150);
        Flight flight3 = new Flight(3, "Otopeni", "Roma", LocalDate.of(2022, 1,12), 110);
        Flight flight4 = new Flight(4, "Timisoara", "Roma", LocalDate.of(2022, 2,2), 80);

        User user1 = new User("valentin_popa@gmail.com","Valentin Popa", "1234");
        user1.addFlight(flight1);

        User user2 = new User("andrei_coman@gmail.com", "Andrei Coman", "0000");
        user2.addFlight(flight2);
        user2.addFlight(flight3);

        User user3 = new User("mihaela_soare@gmail.com", "Mihaela Soare", "1111");
        user3.addFlight(flight1);
        user3.addFlight(flight3);
        user3.addFlight(flight4);

        AirLineManager airLineManager = new AirLineManager();
        airLineManager.setAllFlights(List.of(flight1,flight2,flight3,flight4));
        airLineManager.setAllUsers(List.of(user1,user2,user3));

        return airLineManager;
    }

    private static String findMostUsedCityAsDepartureForFlights(AirLineManager manager){
        // Va returna orașul din care exista cele mai multe zboruri
        List<Flight> allFlights = manager.getAllFlights();
        Map<String, Long> cityAndNumberOfFlights = allFlights.stream()
                .collect(Collectors.groupingBy(flight -> flight.getFrom(), Collectors.counting()));

        Set<Map.Entry<String, Long>> entries = cityAndNumberOfFlights.entrySet();

        String city = null;
        long maxValue = 0;
        for (Map.Entry<String,Long> entry : entries) {
           if (entry.getValue() > maxValue){
               maxValue = entry.getValue();
               city= entry.getKey();
           }
       }
        return city;
    }


    private static User findUserWhoTravelTheMost(AirLineManager manager){
        // Va returna userul ale cărui zboruri insumeaza cele mai multe minute (nu cel cu cele mai multe zboruri)

        List<User> users = manager.getAllUsers();
        int maxMinutes = 0;
        User userWhoTravelTheMost = users.get(0);

        for (User user : users) {
            int minutes =0;
            for (Flight flight : user.getUserFlights()) {
                minutes += flight.getDuration();
            }
            if (minutes > maxMinutes) {
                maxMinutes = minutes;
                userWhoTravelTheMost = user;
            }
        }

        return userWhoTravelTheMost;
    }



    private static List<User> findAllUsersWhoTraveledToCity(AirLineManager manager, String city){
        // Întoarce lista tuturor utilizatorilor care au calatorit în orașul trimis ca parametru
        // (case insensitive - nu conteaza daca e scris cu majuscula sau nu)

        List<User> users = manager.getAllUsers();
        List<User> usersWhoTravekedToCity = new ArrayList<>();
        for (User user : users) {
            for (Flight flight : user.getUserFlights()) {
                if (flight.getTo().equalsIgnoreCase(city)) {
                    usersWhoTravekedToCity.add(user);
                    break;
                }
            }
        }
        return usersWhoTravekedToCity;
    }



    private static List<Flight> findAllFlightsBetweenDates(AirLineManager manager, LocalDate startDate, LocalDate endDate) {
        // Întoarce toate zborurile care au avut loc între cele doua date calendaristice

        List<Flight> allFlights = manager.getAllFlights();
        List<Flight> flightsBetweenDates =  new ArrayList<>();
        for (Flight flight: allFlights) {
            if (flight.getDate().isAfter(startDate) && flight.getDate().isBefore(endDate)){
                flightsBetweenDates.add(flight);
            }
        }
        return flightsBetweenDates;
    }



    private static Flight findShortestFlight(AirLineManager manager) {
        // Întoarce zborul cu durata cea mai scurta. Dacă sunt mai multe cu aceeași durată, se întoarce cel cu id-ul mai mic.

        List<Flight> allFlights = manager.getAllFlights();
        Comparator<Flight> comparator = (flight1, flight2) -> {
            if (flight1.getDuration() == flight2.getDuration()) {
                return flight1.getId() - flight2.getId();
            } else {
                return flight1.getDuration() - flight2.getDuration();
            }
        };

        TreeSet<Flight> sortedFlights = new TreeSet<>(comparator);
        sortedFlights.addAll(allFlights);
        return sortedFlights.first();
    }



    private static List<User> findAllUsersWhoTraveledIn(AirLineManager manager, LocalDate date){
        // Întoarce toți utilizatorii care au calatorit în acea zi.

        List<User> allUsers = manager.getAllUsers();
        List<User> allUsersWhoTraveledIn = new ArrayList<>();

        for (User user : allUsers) {
            for (Flight flight: user.getUserFlights()) {
                if (flight.getDate() == date) {
                    allUsersWhoTraveledIn.add(user);
                }
            }
        }
        return allUsersWhoTraveledIn;
    }


}
