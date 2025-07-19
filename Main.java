import java.util.ArrayList;
import java.util.Scanner;

class Train {
    private String trainNumber;
    private String trainName;
    private String departureTime;
    private String arrivalTime;
    private int availableSeats;

    public Train(String trainNumber, String trainName, String departureTime, String arrivalTime, int availableSeats) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void displayTrainDetails() {
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Train Name: " + trainName);
        System.out.println("Departure Time: " + departureTime);
        System.out.println("Arrival Time: " + arrivalTime);
        System.out.println("Available Seats: " + availableSeats);
    }

    public boolean checkAvailability() {
        return availableSeats > 0;
    }

    public void bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
        }
    }

    public void cancelSeat() {
        availableSeats++;
    }
}

class User {
    private String userId;
    private String name;
    private String email;

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}

class Ticket {
    private String ticketId;
    Train train;
        private User user;
        private int seatNumber;
    
        public Ticket(String ticketId, Train train, User user, int seatNumber) {
            this.ticketId = ticketId;
            this.train = train;
            this.user = user;
            this.seatNumber = seatNumber;
        }
    
        public String getTicketId() {
            return ticketId;
        }
    
        public void displayTicketDetails() {
            System.out.println("Ticket ID: " + ticketId);
            System.out.println("Train: " + train.getTrainName());
            System.out.println(":User  " + user.getName());
            System.out.println("Seat Number: " + seatNumber);
        }
    }
    
    class Reservation {
        private ArrayList<Ticket> reservations = new ArrayList<>();
        private int ticketCounter = 1;
    
        public void bookTicket(User user, Train train) {
            if (train.checkAvailability()) {
                int seatNumber = train.getAvailableSeats(); // Assign the last available seat
                String ticketId = "TICKET" + ticketCounter++;
                Ticket ticket = new Ticket(ticketId, train, user, seatNumber);
                reservations.add(ticket);
                train.bookSeat();
                System.out.println("Ticket booked successfully! Ticket ID: " + ticketId);
            } else {
                System.out.println("No available seats on this train.");
            }
        }
    
        public void cancelTicket(String ticketId) {
            for (Ticket ticket : reservations) {
                if (ticket.getTicketId().equals(ticketId)) {
                    reservations.remove(ticket);
                    ticket.train.cancelSeat(); // Free up the seat
                System.out.println("Ticket cancelled successfully!");
                return;
            }
        }
        System.out.println("Ticket ID not found.");
    }

    public void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Ticket ticket : reservations) {
                ticket.displayTicketDetails();
                System.out.println();
            }
        }
    }
}

public class TrainReservationSystem {
    private static ArrayList<Train> trains = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    private static Reservation reservation = new Reservation();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sample trains
        trains.add(new Train("101", "Express Train", "10:00 AM", "2:00 PM", 5));
        trains.add(new Train("102", "Local Train", "11:00 AM", "3:00 PM", 3));

        while (true) {
            System.out.println("Welcome to the Train Reservation System");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System .out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser ();
                    break;
                case 2:
                    loginUser ();
                    break;
                case 3:
                    System.out.println("Thank you for using the Train Reservation System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void registerUser () {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        users.add(new User(userId, name, email));
        System.out.println("User  registered successfully!");
    }

    private static void loginUser () {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        for (User  user : users) {
            if (user.getUserId().equals(userId)) {
                userMenu(user);
                return;
            }
        }
        System.out.println("User  ID not found. Please register first.");
    }

    private static void userMenu(User user) {
        while (true) {
            System.out.println("Welcome, " + user.getName());
            System.out.println("1. View Available Trains");
            System.out.println("2. Book Ticket");
            System.out.println("3. Cancel Ticket");
            System.out.println("4. View Reservations");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAvailableTrains();
                    break;
                case 2:
                    reservation.bookTicket(user, selectTrain());
                    break;
                case 3:
                    System.out.print("Enter Ticket ID to cancel: ");
                    String ticketId = scanner.nextLine();
                    reservation.cancelTicket(ticketId);
                    break;
                case 4:
                    reservation.viewReservations();
                    break;
                case 5:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void viewAvailableTrains() {
        System.out.println("Available Trains:");
        for (Train train : trains) {
            train.displayTrainDetails();
            System.out.println();
        }
    }

    private static Train selectTrain() {
        System.out.print("Enter Train Number to book: ");
        String trainNumber = scanner.nextLine();
        for (Train train : trains) {
            if (train.getTrainNumber().equals(trainNumber)) {
                return train;
            }
        }
        System.out.println("Train not found.");
        return null;
    }
}
