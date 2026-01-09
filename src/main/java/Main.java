import ChallengeClasses.Register;
import java.util.Scanner;

/* (C)2024 */
public class Main {
    private static Register baseRegister;
    private static Register currentRegister;
    private static Scanner scanner;
    private static boolean running = true;

    public static void main(String[] args) {
        String filePath = "WeatherStations.json";
        baseRegister = new Register(filePath);
        currentRegister = baseRegister;
        scanner = new Scanner(System.in);

        while (running) {
            printTitle();
            printRegister(currentRegister);
            printOptionMenu();

            try {
                System.out.print(" > Select one option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                handleMenuOption(option);
            } catch (Exception e) {
                System.out.println("\nInvalid input. Please enter a valid option.\n");
                scanner.nextLine();
            }
        }

        scanner.close();
        System.out.println("\nThank you for using Weather Menu. Goodbye!");
    }

    private static void handleMenuOption(int option) {
        switch (option) {
            case 1:
                filterByDate();
                break;
            case 2:
                filterByLocation();
                break;
            case 3:
                displayMetricsResume();
                break;
            case 4:
                restartToOriginal();
                break;
            case 5:
                running = false;
                break;
            default:
                System.out.println("\nInvalid option. Please select a valid menu option.\n");
        }
    }

    private static void filterByDate() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" FILTER BY DATE");
        System.out.println("=".repeat(60));
        System.out.println("Leave any field as 0 to ignore that filter criterion.\n");

        try {
            System.out.print("Enter year (0 to ignore): ");
            int year = scanner.nextInt();

            System.out.print("Enter month (1-12, 0 to ignore): ");
            int month = scanner.nextInt();

            System.out.print("Enter day (1-31, 0 to ignore): ");
            int day = scanner.nextInt();

            System.out.print("Enter hour (0-23, 0 to ignore): ");
            int hour = scanner.nextInt();

            System.out.print("Enter minute (0-59, 0 to ignore): ");
            int minute = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            currentRegister = currentRegister.filterByDate(year, month, day, hour, minute);

            System.out.println("\n" + "=".repeat(60));
            System.out.println(" FILTERED RESULTS BY DATE");
            System.out.println("=".repeat(60));
            System.out.println(currentRegister.printResumeRegister());
            displayFilteredMetrics();

        } catch (Exception e) {
            System.out.println("\nInvalid input. Please enter valid numbers.\n");
            scanner.nextLine();
        }
    }

    private static void filterByLocation() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" FILTER BY LOCATION");
        System.out.println("=".repeat(60));
        System.out.println(currentRegister.printUniqueLocations());
        System.out.println();

        try {
            System.out.print("Enter location name: ");
            String location = scanner.nextLine().trim();

            Register filteredRegister = currentRegister.filterByLocationName(location);

            if (filteredRegister.getRecordList().isEmpty()) {
                System.out.println("\nNo records found for location: " + location + "\n");
            } else {
                currentRegister = filteredRegister;

                System.out.println("\n" + "=".repeat(60));
                System.out.println(" FILTERED RESULTS BY LOCATION: " + location);
                System.out.println("=".repeat(60));
                System.out.println(currentRegister.printResumeRegister());
                displayFilteredMetrics();
            }

        } catch (Exception e) {
            System.out.println("\nAn error occurred while filtering by location.\n");
            scanner.nextLine();
        }
    }

    private static void displayMetricsResume() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" METRICS RESUME");
        System.out.println("=".repeat(60));
        System.out.println(currentRegister.printResumeRegister());
        displayFilteredMetrics();
    }

    private static void displayFilteredMetrics() {
        System.out.println("\nMINIMUM METRICS:");
        System.out.println(currentRegister.getMinMetrics().toString());
        System.out.println("\nMAXIMUM METRICS:");
        System.out.println(currentRegister.getMaxMetrics().toString());
        System.out.println("\nAVERAGE METRICS:");
        System.out.println(currentRegister.getAvgMetrics().toString());
    }

    private static void restartToOriginal() {
        currentRegister = baseRegister;
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" RESTARTED TO ORIGINAL REGISTER");
        System.out.println("=".repeat(60));
        System.out.println(currentRegister.printResumeRegister());
    }

    private static void printTitle() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" ".repeat(15) + "WEATHER MENU");
        System.out.println("=".repeat(60));
    }

    private static void printRegister(Register weatherRegister) {
        System.out.println(weatherRegister.printResumeRegister());
    }

    private static void printOptionMenu() {
        System.out.println("=".repeat(60));
        System.out.println(" 1) Filter by date");
        System.out.println(" 2) Filter by location");
        System.out.println(" 3) Print Metrics Resume");
        System.out.println(" 4) Restart to original register");
        System.out.println(" 5) Exit");
        System.out.println("=".repeat(60));
    }
}