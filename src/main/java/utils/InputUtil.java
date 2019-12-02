package utils;

import java.util.Scanner;

public class InputUtil {

    public static double inputNumber(Scanner scanner, int range1, int range2) {
        while (true) {
            try {
                int option = Integer.parseInt(scanner.nextLine());
                if (option >= range1 && option <= range2) {
                    return option;
                } else {
                    System.out.println("Please type a number between: " + range1 + "-" + range2);
                }
            } catch (NumberFormatException e) {
                System.out.println("Given input is not a valid number.");
            }
        }
    }

    public static double inputNumber(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Given input is not a valid number.");
            }
        }
    }

    public static String inputText(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.length() == 0) {
            System.out.println("Your input cannot be blank");
            inputText(scanner);
        }
        return input;
    }

}
