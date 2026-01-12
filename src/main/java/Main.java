import ChallengeClasses.Register;
import java.util.Scanner;

/* (C)2024 - Enhanced Menu System */
public class Main {
    private static Register baseRegister;
    private static Register currentRegister;
    private static Scanner scanner;
    private static boolean running = true;

    // Filter state tracking
    private static boolean filteredByDate = false;
    private static boolean filteredByLocation = false;

    public static void main(String[] args) {
        String filePath = "WeatherStations.json";
        baseRegister = new Register(filePath);
        currentRegister = baseRegister;
        scanner = new Scanner(System.in);

        printTitle();
        while (running) {
            printRegisterMetadataResume(currentRegister);
            displayContextualMenu();

            try {
                System.out.print(" > Select one option: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                handleContextualMenuOption(option);
            } catch (Exception e) {
                System.out.println("\nInvalid input. Please enter a valid option.\n");
                scanner.nextLine();
            }
        }

        scanner.close();
        System.out.println("\nThank you for using Weather Menu. Goodbye!");
    }

    private static void displayContextualMenu() {
        System.out.println("=".repeat(60));

        if (!filteredByDate && !filteredByLocation) {
            // Initial menu - no filters applied
            System.out.println(" 1) Filter by date");
            System.out.println(" 2) Filter by location");
            System.out.println(" 3) Show metrics resume");
            System.out.println(" 4) Exit");
        }
        else if (filteredByDate && !filteredByLocation) {
            // After date filter - allow location filter or show metrics
            System.out.println(" 1) Apply additional location filter");
            System.out.println(" 2) Show metrics resume");
            System.out.println(" 3) Reset filters and start over");
            System.out.println(" 4) Exit");
        }
        else if (!filteredByDate && filteredByLocation) {
            // After location filter - allow date filter or show metrics
            System.out.println(" 1) Apply additional date filter");
            System.out.println(" 2) Show metrics resume");
            System.out.println(" 3) Reset filters and start over");
            System.out.println(" 4) Exit");
        }
        else {
            // After both filters - only allow reset or metrics
            System.out.println(" FILTERS APPLIED: Date and Location");
            System.out.println(" 1) Show metrics resume");
            System.out.println(" 2) Reset filters and start over");
            System.out.println(" 3) Exit");
        }

        System.out.println("=".repeat(60));
    }

    private static void handleContextualMenuOption(int option) {
        if (!filteredByDate && !filteredByLocation) {
            // Initial menu
            switch (option) {
                case 1:
                    filterByDate();
                    break;
                case 2:
                    filterByLocation();
                    break;
                case 3:
                    displayMetricsMenu();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid option. Please select a valid menu option.\n");
            }
        }
        else if (filteredByDate && !filteredByLocation) {
            // After date filter
            switch (option) {
                case 1:
                    filterByLocation();
                    break;
                case 2:
                    displayMetricsMenu();
                    break;
                case 3:
                    resetFilters();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid option. Please select a valid menu option.\n");
            }
        }
        else if (!filteredByDate && filteredByLocation) {
            // After location filter
            switch (option) {
                case 1:
                    filterByDate();
                    break;
                case 2:
                    displayMetricsMenu();
                    break;
                case 3:
                    resetFilters();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid option. Please select a valid menu option.\n");
            }
        }
        else {
            // Both filters applied
            switch (option) {
                case 1:
                    displayMetricsMenu();
                    break;
                case 2:
                    resetFilters();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("\nInvalid option. Please select a valid menu option.\n");
            }
        }
    }

    private static void filterByDate() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" FILTER BY DATE");
        System.out.println("=".repeat(60));
        System.out.println("Leave any field as 0 to skip that filter criterion.\n");
        System.out.println("Note: If you skip a field (enter 0), all following fields will also be skipped.\n");

        try {
            int year = 0;
            int month = 0;
            int day = 0;
            int hour = -1;

            while (true) {
                System.out.print("Enter year (0 to skip, " + currentRegister.minDate().getYear() +
                        "-" + currentRegister.maxDate().getYear() + "): ");
                year = scanner.nextInt();

                if (year == 0) {
                    System.out.println("Year skipped. Returning to menu.");
                    return;
                }

                if (isValidYear(year)) {
                    break;
                } else {
                    System.out.println("Invalid year. Please enter a year between " +
                            currentRegister.minDate().getYear() + " and " +
                            currentRegister.maxDate().getYear() + ", or 0 to skip.");
                }
            }

            while (true) {
                System.out.print("Enter month (1-12, 0 to skip month, day, and hour filters): ");
                month = scanner.nextInt();

                if (month == 0) {
                    break;
                }

                if (isValidMonth(month)) {
                    break;
                } else {
                    System.out.println("Invalid month. Please enter a month between 1 and 12, or 0 to skip.");
                }
            }

            if (month == 0) {
                currentRegister = currentRegister.filterByDate(year, 0, 0, -1);
            }
            else {
                while (true) {
                    System.out.print("Enter day (1-31, 0 to skip day and hour filters): ");
                    day = scanner.nextInt();

                    if (day == 0) {
                        break;
                    }

                    if (isValidDay(day)) {
                        break;
                    } else {
                        System.out.println("Invalid day. Please enter a day between 1 and 31, or 0 to skip.");
                    }
                }

                if (day == 0) {
                    currentRegister = currentRegister.filterByDate(year, month, 0, -1);
                }
                else {
                    while (true) {
                        System.out.print("Enter hour (0-23, -1 to skip hour filter): ");
                        hour = scanner.nextInt();

                        if (hour == -1) {
                            break;
                        }

                        if (isValidHour(hour)) {
                            break;
                        } else {
                            System.out.println("Invalid hour. Please enter an hour between 0 and 23, or -1 to skip.");
                        }
                    }

                    currentRegister = currentRegister.filterByDate(year, month, day, hour);
                }
            }

            filteredByDate = true;
            System.out.println("\n" + "=".repeat(60));
            System.out.println(" FILTERED RESULTS BY DATE");
            System.out.println("=".repeat(60));
            System.out.println(currentRegister.printResumeRegister());

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
            System.out.print("Enter location index: ");
            int index = scanner.nextInt();
            String location = currentRegister.getUniqueLocationsList().get(index-1);
            Register filteredRegister = currentRegister.filterByLocationName(location);

            if (filteredRegister.getRecordList().isEmpty()) {
                System.out.println("\nNo records found for location: " + location + "\n");
            } else {
                currentRegister = filteredRegister;
                filteredByLocation = true;

                System.out.println("\n" + "=".repeat(60));
                System.out.println(" FILTERED RESULTS BY LOCATION: " + location);
                System.out.println("=".repeat(60));
                System.out.println(currentRegister.printResumeRegister());
            }

        } catch (Exception e) {
            System.out.println("\nAn error occurred while filtering by location.\n");
            scanner.nextLine();
        }
    }

    private static void displayMetricsMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" METRICS MENU");
        System.out.println("=".repeat(60));
        System.out.println(" 1) Print minimum metric");
        System.out.println(" 2) Print maximum metric");
        System.out.println(" 3) Print average metric");
        System.out.println(" 4) Print all metrics");
        System.out.println(" 5) Print metrics resume");
        System.out.println(" 6) Print complete register");
        System.out.println(" 7) Return to main menu");
        System.out.println("=".repeat(60));

        try {
            System.out.print(" > Select one option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    printMinimumMetric();
                    break;
                case 2:
                    printMaximumMetric();
                    break;
                case 3:
                    printAverageMetric();
                    break;
                case 4:
                    displayAllMetrics();
                    break;
                case 5:
                    displayMetricsResume();
                    break;
                case 6:
                    printCompleteRegister();
                    break;
                case 7:
                    // Return to main menu
                    break;
                default:
                    System.out.println("\nInvalid option. Please try again.\n");
            }
        } catch (Exception e) {
            System.out.println("\nInvalid option, please try again.\n");
            scanner.nextLine();
        }
    }

    private static void displayMetricsResume() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" METRICS RESUME");
        System.out.println("=".repeat(60));
        System.out.println(currentRegister.printResume());
        System.out.println("=".repeat(60) + "\n");
    }

    private static void displayAllMetrics() {
        printMinimumMetric();
        printMaximumMetric();
        printAverageMetric();
    }

    private static void printMinimumMetric() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" MINIMUM METRICS:");
        System.out.println("=".repeat(60));
        System.out.println(currentRegister.getMinMetrics().toString());
    }

    private static void printMaximumMetric() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" MAXIMUM METRICS:");
        System.out.println("=".repeat(60));
        System.out.println(currentRegister.getMaxMetrics().toString());
    }

    private static void printAverageMetric() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" AVERAGE METRICS:");
        System.out.println("=".repeat(60));
        System.out.println(currentRegister.getAvgMetrics().toString());
    }

    private static void resetFilters() {
        currentRegister = baseRegister;
        filteredByDate = false;
        filteredByLocation = false;

        System.out.println("\n" + "=".repeat(60));
        System.out.println(" FILTERS RESET - BACK TO ORIGINAL REGISTER");
        System.out.println("=".repeat(60));
        System.out.println(currentRegister.printResumeRegister());
    }

    private static void printTitle() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println(" ".repeat(15) + "WEATHER MENU");
        System.out.println("=".repeat(60));
    }

    private static void printRegisterMetadataResume(Register weatherRegister) {
        System.out.println(weatherRegister.printResumeRegister());
    }

    private static void printCompleteRegister(){
        currentRegister.printCompleteRegister();
    }

    private static boolean isValidYear(int year) {
        int minYear = currentRegister.minDate().getYear();
        int maxYear = currentRegister.maxDate().getYear();
        return year <= maxYear && year >= minYear;
    }

    private static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    private static boolean isValidDay(int day) {
        return day >= 1 && day <= 31;
    }

    private static boolean isValidHour(int hour) {
        return hour >= 0 && hour <= 23;
    }
}