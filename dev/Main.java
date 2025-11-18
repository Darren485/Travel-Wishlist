    package dev;

    import java.util.ArrayList;
    import java.util.List;
    import java.math.BigDecimal;
    import java.util.List;
    import java.util.Scanner;
    import java.sql.SQLException;
    import java.sql.Connection;
    import java.util.Locale;

    import javax.crypto.SealedObject;

    public class Main {

        static Scanner input = new Scanner(System.in);
        public static TravelManager travelManager;
        public static TravelHandler travelHandler;

        public static void main(String[] args) {
            System.out.println("TRAVEL WISHLIST");
            travelHandler = new TravelHandler();
            travelManager = travelHandler.load();
            while (true) {
                showMenu();
                int choice = readInt();
                input.nextLine(); // Consume newline
                switch (choice) {
                    case 1 ->
                        addDestination();

                    case 2 ->
                        viewAllDestinations();

                    case 3 ->
                        updateDestination();

                    case 4 ->
                        deleteDestination();

                    case 5 ->
                        searchDestination();

                    case 6 ->
                        searchByPriority();

                    case 7 ->
                        searchBySeason();

                    case 8 ->
                        showTotalBudget();

                    case 9 ->
                        showTotalTrips();

                    case 10 ->
                        showSummaryOfTravels();

                    case 0 -> {
                        System.out.println("Exiting...");
                        travelHandler.save(travelManager);
                        System.exit(0);
                    }
                }
            }
        }

        public static void showMenu() {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("\nMenu:");
            System.out.println("1. Add Destination");
            System.out.println("2. View All Destinations");
            System.out.println("3. Update Destination");
            System.out.println("4. Delete Destination");
            System.out.println("-".repeat(40));
            System.out.println("5. Search Destination");
            System.out.println("6. Search by Priority");
            System.out.println("7. Search by Season");
            System.out.println("-".repeat(40));
            System.out.println("8. Total Travel Budget");
            System.out.println("9. Total number of trips");
            System.out.println("10. Summary of Travels");
            System.out.println("-".repeat(40));
            System.out.println("0. Exit");
            System.out.println("=".repeat(40));
            System.out.println("Choose an Option: ");
        }

        //Case 1 -> Add Destination
        public static void addDestination() {
            System.out.println("Enter Country: ");
            String country = input.nextLine();

            System.out.println("Enter Season (SPRING, SUMMER, FALL, WINTER): ");
            String seasonStr = input.nextLine();
            Season season = parseSeason(seasonStr);

            System.out.println("Enter Priority (LOW, MEDIUM, HIGH): ");
            String priorityStr = input.nextLine();
            Priority priority = parsePriority(priorityStr);

            System.out.println("Enter Name/Description: ");
            String name = input.nextLine();

            System.out.println("Enter Budget: ");
            BigDecimal budget = readBigDecimal();
            // Create Travel object and add to list or database
            travelManager.addTravel(country, season, priority, name, budget);
            travelHandler.save(travelManager);
            System.out.println("Destination added successfully!");

        }

        //Case 2 -> View All Destinations
        public static void viewAllDestinations() {
            List<Travel> travels = travelManager.getAllTravels();
            if (travels.isEmpty()) {
                System.out.println("No destinations found.");
            } else {
                System.out.println("All Destinations (" + travels.size() + " total):");
                System.out.println("| ID  | Country        | Season    | Priority  | Name           | Budget    |");
                System.out.println("-".repeat(80));
                for (Travel travel : travels) {
                    System.out.println(travel);
                }
                System.out.println("-".repeat(80));
            }
        }

        // Case 3 -> Update Destination
        public static void updateDestination() {
            if (!travelManager.hasTravels()) {
                System.out.println("No destinations to update.");
                return;
            }

            viewAllDestinations(); // Show current destinations
            System.out.print("Enter ID of destination to update: ");
            int id = Integer.parseInt(input.nextLine());
            input.nextLine(); // Consume newline

            Travel existingTravel = travelManager.getTravelById(id);
            if (existingTravel == null) {
                System.out.println("Destination with ID " + id + " not found.");
                return;
            }

            System.out.println("Updating destination: " + existingTravel.getName());
            System.out.println("Leave field blank to keep current value.");

            // Country
            System.out.print("Enter Country [" + existingTravel.getCountry() + "]: ");
            String country = input.nextLine();
            if (country.isEmpty()) {
                country = existingTravel.getCountry();
            }

            // Season
            System.out.print("Enter Season [" + existingTravel.getSeason() + "]: ");
            String seasonStr = input.nextLine();
            Season season = seasonStr.isEmpty() ? existingTravel.getSeason() : parseSeason(seasonStr);

            // Priority
            System.out.print("Enter Priority [" + existingTravel.getPriority() + "]: ");
            String priorityStr = input.nextLine();
            Priority priority = priorityStr.isEmpty() ? existingTravel.getPriority() : parsePriority(priorityStr);

            // Name
            System.out.print("Enter Name [" + existingTravel.getName() + "]: ");
            String name = input.nextLine();
            if (name.isEmpty()) {
                name = existingTravel.getName();
            }

            // Budget
            System.out.print("Enter Budget [" + existingTravel.getBudget() + "]: ");
            String budgetStr = input.nextLine();
            BigDecimal budget = budgetStr.isEmpty() ? existingTravel.getBudget() : readBigDecimalFromString(budgetStr);

            if (travelManager.updateTravelById(id, country, season, priority, name, budget)) {
                travelHandler.save(travelManager);
                System.out.println("Destination updated successfully!");
            } else {
                System.out.println("Failed to update destination.");
            }
        }

        // Case 4 -> Delete Destination
        public static void deleteDestination() {
            if (!travelManager.hasTravels()) {
                System.out.println("No destinations to delete.");
                return;
            }

            viewAllDestinations(); // Show current destinations
            System.out.print("Enter ID of destination to delete: ");
            int id = Integer.parseInt(input.nextLine());

            if (travelManager.removeTravelById(id)) {
                travelHandler.save(travelManager);
                System.out.println("Destination deleted successfully!");
            } else {
                System.out.println("Destination with ID " + id + " not found.");
            }
        }

        // Case 5 -> Search Destination (flexible search)
        public static void searchDestination() {
            System.out.print("Enter search term (country, name, or partial match): ");
            String searchTerm = input.nextLine().toLowerCase();

            List<Travel> allTravels = travelManager.getAllTravels();
            List<Travel> results = new ArrayList<>();

            for (Travel travel : allTravels) {
                if (travel.getCountry().toLowerCase().contains(searchTerm)
                        || travel.getName().toLowerCase().contains(searchTerm)
                        || travel.getSeason().name().toLowerCase().contains(searchTerm)
                        || travel.getPriority().name().toLowerCase().contains(searchTerm)) {
                    results.add(travel);
                }
            }

            if (results.isEmpty()) {
                System.out.println("No destinations found matching: " + searchTerm);
            } else {
                System.out.println("Search results for '" + searchTerm + "' (" + results.size() + " found):");
                System.out.println("| ID  | Country        | Season    | Priority  | Name           | Budget    |");
                System.out.println("-".repeat(80));
                for (Travel travel : results) {
                    System.out.println(travel);
                }
            }
        }

        // Case 6 -> Search by Priority
        public static void searchByPriority() {
            System.out.print("Enter Priority (LOW, MEDIUM, HIGH, URGENT): ");
            String priorityStr = input.nextLine();
            Priority priority = parsePriority(priorityStr);

            List<Travel> results = travelManager.findTravelsByPriority(priority);

            if (results.isEmpty()) {
                System.out.println("No destinations found with priority: " + priority);
            } else {
                System.out.println("Destinations with " + priority + " priority:");
                System.out.println("| ID  | Country        | Season    | Priority  | Name           | Budget    |");
                System.out.println("-".repeat(80));
                for (Travel travel : results) {
                    System.out.println(travel);
                }
            }
        }

        // Case 7 -> Search by Season
        public static void searchBySeason() {
            System.out.print("Enter Season (SPRING, SUMMER, FALL, WINTER, RAINY, DRY, MONSOON): ");
            String seasonStr = input.nextLine();
            Season season = parseSeason(seasonStr);

            List<Travel> results = travelManager.findTravelsBySeason(season);

            if (results.isEmpty()) {
                System.out.println("No destinations found for season: " + season);
            } else {
                System.out.println("Destinations for " + season + " season:");
                System.out.println("| ID  | Country        | Season    | Priority  | Name           | Budget    |");
                System.out.println("-".repeat(80));
                for (Travel travel : results) {
                    System.out.println(travel);
                }
            }
        }


        //Case 8 -> Total Budget
        public static void showTotalBudget() {
            System.out.println("Total Travel Budget: " + travelManager.getTotalBudget());
        }

        //Case 9 -> Total Number of Trips
        public static void showTotalTrips() {
            System.out.println("Total Number of Trips: " + travelManager.getNumberOfTravels());
        }

        //Case 10 -> Summary of Travels
        public static void showSummaryOfTravels() {
            System.out.println("Total Number of Trips: " + travelManager.getNumberOfTravels());
            System.out.println(travelManager.getTravelSummary());
        }

        //Method to parse Season
        private static Season parseSeason(String input) {
            if (input == null || input.isEmpty()) {
                return Season.SUMMER; //default
            }
            try {
                return Season.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid season. Defaulting to SUMMER.");
                return Season.SUMMER;
            }
        }

        //Method to parse Priority
        private static Priority parsePriority(String input) {
            if (input == null || input.isEmpty()) {
                return Priority.MEDIUM; //default
            }
            try {
                return Priority.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid priority. Defaulting to MEDIUM.");
                return Priority.MEDIUM;
            }
        }

        private static int readInt() {
            while (true) {
                try {
                    return input.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid number. Try again:");
                    input.nextLine(); // clear invalid input
                }
            }
        }

        // Helper method for reading BigDecimal from string
        private static BigDecimal readBigDecimalFromString(String inputStr) {
            try {
                return new BigDecimal(inputStr);
            } catch (Exception e) {
                System.out.println("Invalid amount. Using current budget.");
                return BigDecimal.ZERO; // This will be handled in update
            }
        }

        //Method to help read the BigDecimal
        private static BigDecimal readBigDecimal() {
            while (true) {
                try {
                    String line = input.nextLine();
                    return new BigDecimal(line);
                } catch (Exception e) {
                    System.out.println("Invalid amount. Try again");
                }
            }
        }

    }
