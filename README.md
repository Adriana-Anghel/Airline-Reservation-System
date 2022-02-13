# Airline-Reservation-System
***


Aplicația "Airline Reservation System" permite utilizatorilor:
* să-și creeze cont (folosind o adresa de email unică și o parola de minim 8 caractere);                                                              
* să se logheze;
* să-și vizualizeze zborurile;
* să-și adauge noi zboruri;
* să anuleze zborurile;


![plane](Airline.png)

Pe lângă aceste acțiuni, se pot vizualiza toate zborurile, indiferent daca un user este conectat sau nu, pot fi adaugate noi curse sau anulate. 
În cazul în care o cursă este anulată, toți utilizatorii ce dețin bilet pentru acel zbor sunt înșiințați. 

---

Datele despre zboruri și utilizatori sunt stocate în baza de date SQL cu ajutorul tabelelor. 

|ID|Email|Name|Password|
|--|-----|----|--------| 
|1|email@gmail.com|name|test1234|

|ID|From|To|Date|Duration|
|--|----|--|----|--------| 
|1|Bucharest|London|2022-02-04|220|

---

* Instrucțiunile implementate pentru utilizator sunt de tipul: 
  * SIGNUP
  * LOGIN
  * LOGOUT
  * DISPLAY_MY_FLIGHTS
  * ADD_FLIGHT
  * CANCEL_FLIGHT
  
* Instrucțiunile implementate pentru zbor sunt de tipul: 
  * ADD_FLIGHT_DETAILS
  * DELETE_FLIGHT
  * DISPLAY_FLIGHTS
  
* Instrucțiuni folosite pentru a actualiza baza de date SQL: 
  * PERSIST_FLIGHTS
  * PERSIST_USERS


> Diverse statistici ale aplicației au fost expuse in clasa _**AirlineStatistics**_: 
   * orașul din care exista cele mai multe zboruri;
   * userul ce petrece în zbor cel mai mut timp;
   * utilizatorii care au călătorit într-o anumită destinație;
   * utilizatorii care au călătorit într-o anumită zi a anului; 
   * zborurile dintre două date calendaristice; 
   * zborul cu durata cea mai scurtă;
    
