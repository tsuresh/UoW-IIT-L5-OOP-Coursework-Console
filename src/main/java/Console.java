import controllers.WestminsterRentalVehicleManager;

import java.util.Scanner;

public class Console {

    private WestminsterRentalVehicleManager manager;
    private Scanner scanner;

    public Console() {
        manager = new WestminsterRentalVehicleManager();
        scanner = new Scanner(System.in);
    }

    private static void launchMenu() {
        System.out.println("Please choose an option from the given options:\n");
        System.out.println("1)\tAdd a new vehicle");
        System.out.println("2)\tDelete a vehicle");
        System.out.println("3)\tPrint the list of vehicles");
        System.out.println("4)\tWrite/Save");
        System.out.println("5)\tLaunch GUI");
        System.out.println("6)\tExit program");
    }

    private static int inputNumber(Scanner scanner, int range1, int range2) {
        while (true) {
            try {
                int option = Integer.parseInt(scanner.nextLine());
                if (option >= range1 && option <= range2) {
                    return option;
                } else {
                    System.out.println("Your input has to be within range: " + range1 + "-" + range2);
                }
            } catch (NumberFormatException e) {
                System.out.println("Given input is not a number.");
            }
        }
    }

    private static int inputNumber(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Given input is not a number.");
            }
        }
    }

    private static String inputText(Scanner scanner) {
        String input = scanner.nextLine();
        if (input.length() == 0) {
            System.out.println("Your input cannot be blank.");
            inputText(scanner);
        }
        return input;
    }

    public WestminsterRentalVehicleManager getManager() {
        return manager;
    }

    public void displayMenu(){
        int option = 0;
        do {
            launchMenu();
            option = inputNumber(scanner, 1, 8);
            switch (option) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    deleteVehicle();
                    break;
                case 3:
                    printList();
                    break;
                case 4:
                    saveList();
                    break;
                case 5:
                    displayGUI();
                    break;
            }
            System.out.println();
        } while (option != 6);
    }

    private void addVehicle() {

    }

    private void deleteVehicle() {

    }

    private void printList() {
        manager.printList();
    }

    private void saveList() {
        manager.save();
    }

    public void displayGUI() {
        //Launch GUI here
    }
}
