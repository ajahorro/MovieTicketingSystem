package ticketsystem;

import java.util.*;

public class MovieTicketCLI {
    static Scanner scanner = new Scanner(System.in);

    static String[] movies = {
        "1. Avengers: Endgame (PG-13)",
        "2. Finding Nemo (G)",
        "3. Joker (R-18)"
    };

    static String[][] times = {
        {"1:00 PM", "3:00 PM", "7:00 PM"},
        {"10:00 AM", "1:00 PM", "4:00 PM"},
        {"6:00 PM", "9:00 PM", "11:00 PM"}
    };

    static String[] seats = {"A1", "A2", "A3", "B1", "B2", "B3"};
    static String[] wheelchairSeats = {"B1", "B2", "B3"};
    static String[] snacks = {"Popcorn", "Chips", "Candy"};

    public static void main(String[] args) {
        System.out.println("=== 🎟️ Welcome to Movie Ticketing System (CLI) ===");

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        // Movie selection
        System.out.println("\nSelect a movie:");
        for (String movie : movies) {
            System.out.println(movie);
        }
        int movieChoice = getIntInput("Enter number: ", 1, 3) - 1;

        // Show times
        System.out.println("\nAvailable Times:");
        for (int i = 0; i < times[movieChoice].length; i++) {
            System.out.println((i + 1) + ". " + times[movieChoice][i]);
        }
        int timeChoice = getIntInput("Select time: ", 1, 3);

        // PWD
        boolean isPWD = getYesNo("Are you a PWD? (Y/N): ");
        boolean hasWheelchair = false;
        String id = "";

        if (isPWD) {
            hasWheelchair = getYesNo("Do you have a wheelchair? (Y/N): ");
            while (true) {
                System.out.print("Enter 8-digit PWD ID: ");
                id = scanner.nextLine();
                if (id.matches("\\d{8}")) break;
                System.out.println("Invalid. Try again.");
            }
        }

        // Seat selection
        System.out.println("\nChoose your seat:");
        for (String seat : seats) System.out.print(seat + " ");
        System.out.println();
        String chosenSeat;

        while (true) {
            System.out.print("Enter seat: ");
            chosenSeat = scanner.nextLine().toUpperCase();
            if (Arrays.asList(seats).contains(chosenSeat)) {
                if (hasWheelchair && !Arrays.asList(wheelchairSeats).contains(chosenSeat)) {
                    System.out.println("Only wheelchair seats available: B1-B3.");
                    continue;
                }
                break;
            } else {
                System.out.println("Invalid seat.");
            }
        }

        // Free food
        System.out.println("\nFree Snack:");
        for (int i = 0; i < snacks.length; i++) {
            System.out.println((i + 1) + ". " + snacks[i]);
        }
        int snackChoice = getIntInput("Select snack: ", 1, 3);

        // Summary
        double price = 300;
        if (isPWD) price -= 50;

        System.out.println("\n=== Ticket Summary ===");
        System.out.println("Name: " + name);
        System.out.println("Movie: " + movies[movieChoice]);
        System.out.println("Time: " + times[movieChoice][timeChoice - 1]);
        System.out.println("Seat: " + chosenSeat);
        System.out.println("Free Drink: Ice Cold Water");
        System.out.println("Free Snack: " + snacks[snackChoice - 1]);
        System.out.println("PWD: " + (isPWD ? "Yes (₱50 discount)" : "No"));
        System.out.println("Total Price: ₱" + price);

        System.out.println("\n--- REMINDERS ---");
        System.out.println("- No refunds");
        System.out.println("- No food requiring utensils");
        System.out.println("- No loud talking");
        System.out.println("Enjoy your movie 🎬");
    }

    static int getIntInput(String prompt, int min, int max) {
        int val;
        while (true) {
            try {
                System.out.print(prompt);
                val = Integer.parseInt(scanner.nextLine());
                if (val >= min && val <= max) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid. Try again.");
        }
        return val;
    }

    static boolean getYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
            System.out.println("Please enter Y or N.");
        }
    }
}