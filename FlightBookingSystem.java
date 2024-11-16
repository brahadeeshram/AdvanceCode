package AdvanceCode;

import java.util.*;

class Passenger {
    int id;
    String name;
    int seatsBooked;
    int passengerTicket;
    
    Passenger(int id, String name, int seatsBooked) {
        this.id = id;
        this.name = name;
        this.seatsBooked = seatsBooked;
    }

    @Override
    public String toString() {
        return "Passenger ID: " + id + ", Name: " + name + ", Seats: " + seatsBooked;
    }
}

class Flight {
    String flightName;
    int totalSeats;
    int bookedSeats;
    int ticketPrice;
    List<Passenger> passengers;

    Flight(String flightName) {
        this.flightName = flightName;
        this.totalSeats = 50;
        this.bookedSeats = 0;
        this.ticketPrice = 5000;
        this.passengers = new ArrayList<>();
    }

    boolean bookTicket(Passenger passenger, int seatsRequired) {
        if (bookedSeats + seatsRequired <= totalSeats) {
            bookedSeats += seatsRequired;
            passengers.add(passenger);
            passenger.passengerTicket=ticketPrice;
            System.out.println("Booking successful. Ticket Price: " + ticketPrice);
            ticketPrice += 200;  // Increase price after each booking
            return true;
        } else {
            System.out.println("Booking failed. Not enough seats available.");
            return false;
        }
    }

    void cancelTicket(int passengerId) {
        Passenger toRemove = null;
        for (Passenger p : passengers) {
            if (p.id == passengerId) {
                toRemove = p;
                break;
            }
        }

        if (toRemove != null) {
            passengers.remove(toRemove);
            bookedSeats -= toRemove.seatsBooked;
            int passengerTicket = toRemove.passengerTicket;
            ticketPrice-=200;
            System.out.println("Cancellation successful. Refund Amount: " + passengerTicket);
        } else {
            System.out.println("Passenger not found.");
        }
    }

    void printFlightDetails() {
        System.out.println("Flight Name: " + flightName);
        System.out.println("Total Seats: " + totalSeats);
        System.out.println("Available Seats: " + (totalSeats - bookedSeats));
        System.out.println("Current Ticket Price: " + ticketPrice);
        System.out.println("Passenger Details:");
        for (Passenger p : passengers) {
            System.out.println(p);
        }
    }
}

public class FlightBookingSystem {
    static Scanner scanner = new Scanner(System.in);
    static Map<String, Flight> flights = new HashMap<>();
    static int passengerId = 1;

    public static void main(String[] args) {
        flights.put("FlightA", new Flight("FlightA"));
        flights.put("FlightB", new Flight("FlightB"));

        while (true) {
            System.out.println("\n1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Print Flight Details");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    bookTicket();
                    break;
                case 2:
                    cancelTicket();
                    break;
                case 3:
                    printFlightDetails();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void bookTicket() {
        System.out.println("Enter Flight Name:");
        String flightName = scanner.next();
        Flight flight = flights.get(flightName);

        if (flight == null) {
            System.out.println("Flight not found.");
            return;
        }

        System.out.println("Enter Passenger Name:");
        String name = scanner.next();

        System.out.println("Enter Number of Seats Required:");
        int seatsRequired = scanner.nextInt();

        Passenger passenger = new Passenger(passengerId++, name, seatsRequired);
        flight.bookTicket(passenger, seatsRequired);
    }

    static void cancelTicket() {
        System.out.println("Enter Flight Name:");
        String flightName = scanner.next();
        Flight flight = flights.get(flightName);

        if (flight == null) {
            System.out.println("Flight not found.");
            return;
        }

        System.out.println("Enter Passenger ID for Cancellation:");
        int id = scanner.nextInt();

        flight.cancelTicket(id);
    }

    static void printFlightDetails() {
        System.out.println("Enter Flight Name:");
        String flightName = scanner.next();
        Flight flight = flights.get(flightName);

        if (flight == null) {
            System.out.println("Flight not found.");
            return;
        }

        flight.printFlightDetails();
    }
}

