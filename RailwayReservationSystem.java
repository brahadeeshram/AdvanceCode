package AdvanceCode;

import java.util.*;

class Passenger {
    String name;
    int age;
    String gender;
    String berthPreference;
    int id;
    String allottedBerth;

    Passenger(String name, int age, String gender, String berthPreference, int id, String allottedBerth) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.berthPreference = berthPreference;
        this.id = id;
        this.allottedBerth = allottedBerth;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Gender: " + gender + 
               ", Berth Preference: " + berthPreference + ", Allotted Berth: " + allottedBerth;
    }
}

class RailwayReservationSystem {
    int confirmedTickets = 63;
    int racTickets = 18;
    int waitingList = 10;
    int lowerBerths = 20;
    int middleBerths = 21;
    int upperBerths = 22;
    int racBerths = 9; // Side-lower for RAC

    Queue<Passenger> confirmedList = new LinkedList<>();
    Queue<Passenger> racList = new LinkedList<>();
    Queue<Passenger> waitingListQueue = new LinkedList<>();

    int passengerId = 1;

    public void bookTicket(String name, int age, String gender, String berthPreference) {
        Passenger passenger = new Passenger(name, age, gender, berthPreference, passengerId++, null);

        if (age < 5) {
            System.out.println("Ticket cannot be allocated for children below 5. Details stored for reference.");
            return;
        }

        String allocatedBerth = allocateBerth(passenger);
        if (allocatedBerth == null) {
            if (racTickets > 0) {
                allocatedBerth = "RAC";
                passenger.allottedBerth = "Side Lower";
                racList.add(passenger);
                racTickets--;
                System.out.println("RAC Ticket booked: " + passenger);
            } else if (waitingList > 0) {
                allocatedBerth = "Waiting List";
                waitingListQueue.add(passenger);
                waitingList--;
                System.out.println("Waiting list ticket booked: " + passenger);
            } else {
                System.out.println("No tickets available.");
            }
        } else {
            passenger.allottedBerth = allocatedBerth;
            confirmedList.add(passenger);
            confirmedTickets--;
            System.out.println("Ticket booked: " + passenger);
        }
    }

    private String allocateBerth(Passenger passenger) {
        if (confirmedTickets == 0) return null;

        // Special case: If passenger is over 60 years old or a lady with children, prioritize lower berth
        if ((passenger.age > 60 || (passenger.gender.equalsIgnoreCase("Female") && passenger.age <= 60)) && lowerBerths > 0) {
            lowerBerths--;
            return "Lower";
        }

        // Try allocating based on berth preference if available
        if (passenger.berthPreference.equalsIgnoreCase("Lower") && lowerBerths > 0) {
            lowerBerths--;
            return "Lower";
        } else if (passenger.berthPreference.equalsIgnoreCase("Middle") && middleBerths > 0) {
            middleBerths--;
            return "Middle";
        } else if (passenger.berthPreference.equalsIgnoreCase("Upper") && upperBerths > 0) {
            upperBerths--;
            return "Upper";
        }

        // If no preferred berth is available, allocate any available berth
        if (lowerBerths > 0) {
            lowerBerths--;
            return "Lower";
        } else if (middleBerths > 0) {
            middleBerths--;
            return "Middle";
        } else if (upperBerths > 0) {
            upperBerths--;
            return "Upper";
        }

        return null; // No confirmed berth available
    }

    public void cancelTicket(int id) {
        Passenger passenger = findAndRemovePassenger(confirmedList, id);
        if (passenger != null) {
            System.out.println("Cancelled confirmed ticket for Passenger ID: " + id);
            moveFromRacToConfirmed();
            return;
        }

        passenger = findAndRemovePassenger(racList, id);
        if (passenger != null) {
            System.out.println("Cancelled RAC ticket for Passenger ID: " + id);
            moveFromWaitlistToRac();
            return;
        }

        passenger = findAndRemovePassenger(waitingListQueue, id);
        if (passenger != null) {
            System.out.println("Cancelled Waiting List ticket for Passenger ID: " + id);
            return;
        }

        System.out.println("No booking found with Passenger ID: " + id);
    }

    private void moveFromRacToConfirmed() {
        if (!racList.isEmpty()) {
            Passenger movedPassenger = racList.poll();
            confirmedList.add(movedPassenger);
            confirmedTickets--;
            racTickets++;
            System.out.println("Moved Passenger from RAC to Confirmed: " + movedPassenger);
        }
        moveFromWaitlistToRac();
    }

    private void moveFromWaitlistToRac() {
        if (!waitingListQueue.isEmpty()) {
            Passenger movedPassenger = waitingListQueue.poll();
            racList.add(movedPassenger);
            racTickets--;
            waitingList++;
            System.out.println("Moved Passenger from Waiting List to RAC: " + movedPassenger);
        }
    }

    private Passenger findAndRemovePassenger(Queue<Passenger> queue, int id) {
        for (Passenger p : queue) {
            if (p.id == id) {
                queue.remove(p);
                if (queue == confirmedList) confirmedTickets++;
                else if (queue == racList) racTickets++;
                else waitingList++;
                return p;
            }
        }
        return null;
    }

    public void printBookedTickets() {
        if (confirmedList.isEmpty()) {
            System.out.println("No confirmed tickets.");
        } else {
            System.out.println("Confirmed Tickets:");
            confirmedList.forEach(System.out::println);
        }

        if (racList.isEmpty()) {
            System.out.println("No RAC tickets.");
        } else {
            System.out.println("RAC Tickets:");
            racList.forEach(System.out::println);
        }

        if (waitingListQueue.isEmpty()) {
            System.out.println("No Waiting List tickets.");
        } else {
            System.out.println("Waiting List Tickets:");
            waitingListQueue.forEach(System.out::println);
        }
    }

    public void printAvailableTickets() {
        System.out.println("Available Confirmed Tickets: " + confirmedTickets);
        System.out.println("Available RAC Tickets: " + racTickets);
        System.out.println("Available Waiting List Tickets: " + waitingList);
        System.out.println("Available Lower Berths: " + lowerBerths);
        System.out.println("Available Middle Berths: " + middleBerths);
        System.out.println("Available Upper Berths: " + upperBerths);
    }

    public static void main(String[] args) {
        RailwayReservationSystem system = new RailwayReservationSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\nRailway Reservation System");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Ticket");
            System.out.println("3. Print Booked Tickets");
            System.out.println("4. Print Available Tickets");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.next();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    System.out.print("Enter Gender (Male/Female): ");
                    String gender = sc.next();
                    System.out.print("Enter Berth Preference (Lower/Upper/Middle): ");
                    String berthPreference = sc.next();
                    system.bookTicket(name, age, gender, berthPreference);
                    break;

                case 2:
                    System.out.print("Enter Passenger ID to cancel: ");
                    int id = sc.nextInt();
                    system.cancelTicket(id);
                    break;

                case 3:
                    system.printBookedTickets();
                    break;

                case 4:
                    system.printAvailableTickets();
                    break;

                case 5:
                    System.out.println("Exiting system.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
