import ChallengeClasses.Register;
import java.util.Scanner;

/* (C)2024 */
public class Main {
    public static void main(String[] args) {
        String filePath = "WeatherStations.json";
        Register weatherRegister = new Register(filePath);

        printTitle();
        printRegister(weatherRegister);
        printOptionMenu();

        Integer option, year, month, day, hour, minute;
        Scanner scanner = new Scanner(System.in);
        System.out.print(" > Select one option: ");
        option = scanner.nextInt();
    }

    private static void printTitle() {
        System.out.println("=".repeat(30));
        System.out.println(" ".repeat(5).concat("WEATHER MENU"));
    }

    private static void printRegister(Register weatherRegister) {
        System.out.println(weatherRegister.printResumeRegister());
    }

    private static void printOptionMenu() {
        String options = "=".repeat(30);
        options += "\n 1) Filter by date\n 2) Filter by location\n 3) Print Metrics Resume";
        System.out.println(options);
    }


}
