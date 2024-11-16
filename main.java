package AdvanceCode;
import java.util.*;

class Taxi {
    int taxiId;
    char currentPoint;
    int freeTime;
    int totalEarnings;
    List<Booking> bookings;

    Taxi(int id) {
        this.taxiId = id;
        this.currentPoint = 'A'; // All taxis initially start at point A
        this.freeTime = 0; // Taxi is free from time 0 (starting state)
        this.totalEarnings = 0;
        this.bookings = new ArrayList<>();
    }

    public String toString() {
        return "Taxi-" + taxiId + " Total Earnings: Rs. " + totalEarnings;
    }
}

class Booking {
    int bookingId;
    int customerId;
    char fromPoint;
    char toPoint;
    int pickupTime;
    int dropTime;
    int fare;

    Booking(int bookingId, int customerId, char fromPoint, char toPoint, int pickupTime, int dropTime, int fare) {
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.pickupTime = pickupTime;
        this.dropTime = dropTime;
        this.fare = fare;
    }

    public String toString() {
        return bookingId + "\t" + customerId + "\t" + fromPoint + "\t" + toPoint + "\t" + pickupTime + "\t" + dropTime + "\t" + fare;
    }
}

class TaxiBookingSystem {
    List<Taxi> taxis;
    int bookingId;

    TaxiBookingSystem(int numTaxis) {
        taxis = new ArrayList<>();
        for (int i = 1; i <= numTaxis; i++) {
            taxis.add(new Taxi(i));
        }
        bookingId = 1;
    }

    public Taxi findNearestFreeTaxi(char pickupPoint, int pickupTime) {
        Taxi nearestTaxi = null;
        int minDistance = Integer.MAX_VALUE;

        for (Taxi taxi : taxis) {
            int distance = Math.abs(taxi.currentPoint - pickupPoint);
            if (taxi.freeTime <= pickupTime && distance < minDistance) {
                nearestTaxi = taxi;
                minDistance = distance;
            } else if (taxi.freeTime <= pickupTime && distance == minDistance && taxi.totalEarnings < (nearestTaxi != null ? nearestTaxi.totalEarnings : Integer.MAX_VALUE)) {
                nearestTaxi = taxi;
            }
        }
        return nearestTaxi;
    }

    public int calculateFare(char fromPoint, char toPoint) {
        int distance = Math.abs(toPoint - fromPoint) * 15; // Each point is 15km away
        int fare = 100; // Base fare for first 5km
        if (distance > 5) {
            fare += (distance - 5) * 10; // Rs.10 for each additional km
        }
        return fare;
    }

    public void bookTaxi(int customerId, char fromPoint, char toPoint, int pickupTime) {
        Taxi taxi = findNearestFreeTaxi(fromPoint, pickupTime);
        if (taxi == null) {
            System.out.println("No taxis available for booking at the moment.");
            return;
        }

        // Calculate drop time and fare
        int travelTime = Math.abs(toPoint - fromPoint) * 1; // 1 hour to travel between adjacent points
        int dropTime = pickupTime + travelTime;
        int fare = calculateFare(fromPoint, toPoint);

        // Add the booking details to the taxi's history
        Booking newBooking = new Booking(bookingId++, customerId, fromPoint, toPoint, pickupTime, dropTime, fare);
        taxi.bookings.add(newBooking);

        // Update the taxi's details
        taxi.currentPoint = toPoint;
        taxi.freeTime = dropTime;
        taxi.totalEarnings += fare;

        System.out.println("Taxi can be allotted.");
        System.out.println("Taxi-" + taxi.taxiId + " is allotted.");
    }

    public void displayTaxiDetails() {
        for (Taxi taxi : taxis) {
            System.out.println(taxi);
            System.out.println("BookingID\tCustomerID\tFrom\tTo\tPickupTime\tDropTime\tAmount");
            for (Booking booking : taxi.bookings) {
                System.out.println(booking);
            }
        }
    }
}

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaxiBookingSystem system = new TaxiBookingSystem(4); // Assume 4 taxis

        while (true) {
            System.out.println("\nEnter 0 to Book Taxi, 1 to Display Taxi Details, 2 to Exit:");
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Enter Customer ID: ");
                int customerId = scanner.nextInt();
                System.out.println("Enter Pickup Point (A-F): ");
                char fromPoint = scanner.next().charAt(0);
                System.out.println("Enter Drop Point (A-F): ");
                char toPoint = scanner.next().charAt(0);
                System.out.println("Enter Pickup Time (in hours, e.g., 9, 10, etc.): ");
                int pickupTime = scanner.nextInt();

                system.bookTaxi(customerId, fromPoint, toPoint, pickupTime);
            } else if (choice == 1) {
                system.displayTaxiDetails();
            } else if (choice == 2) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}


