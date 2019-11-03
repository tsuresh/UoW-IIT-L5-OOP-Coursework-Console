import controllers.WestminsterRentalVehicleManager;
import models.Car;
import models.MotorBike;
import models.Vehicle;
import models.VehicleType;

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

    public WestminsterRentalVehicleManager getManager() {
        return manager;
    }

    public void displayMenu() {
        int option = 0;
        do {
            launchMenu();
            option = (int) inputNumber(1, 8);
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
        System.out.println("Enter the plate number: ");
        String plateNo = inputText();

        System.out.println("Enter the day rental: ");
        double rental = inputNumber();

        System.out.println("Enter the vehicle make: ");
        String make = inputText();

        System.out.println("Enter the vehicle model: ");
        String model = inputText();

        System.out.println("Enter the vehicle color: ");
        String color = inputText();

        System.out.println("Enter the fuel type: ");
        String fuelType = inputText();

        System.out.println("Enter the vehicle type: MOTORBIKE/CAR");
        String type = inputText();

        if (type.equalsIgnoreCase("car")) {

            System.out.println("Number of doors: ");
            int doors = (int) inputNumber();

            System.out.println("Number of windows: ");
            int windows = (int) inputNumber();

            System.out.println("Has AC? yes/no: ");
            String ac = inputText();
            boolean hasAC = false;
            if (ac.equalsIgnoreCase("yes")) {
                hasAC = true;
            }

            System.out.println("Body type: ");
            String bodyType = inputText();

            Vehicle vehicle = new Car(
                    plateNo,
                    rental,
                    make,
                    model,
                    color,
                    fuelType,
                    VehicleType.valueOf(type.toUpperCase()),
                    doors,
                    windows,
                    hasAC,
                    bodyType
            );

            manager.addvehicle(vehicle);

        } else if (type.equalsIgnoreCase("motorbike")) {
            System.out.println("Has Kick Start? yes/no: ");
            String kick = inputText();
            boolean hasKickStart = false;
            if (kick.equalsIgnoreCase("yes")) {
                hasKickStart = true;
            }

            System.out.println("Has Bumpy Tires? yes/no: ");
            String bumpy = inputText();
            boolean hasBumpy = false;
            if (bumpy.equalsIgnoreCase("yes")) {
                hasBumpy = true;
            }

            Vehicle vehicle = new MotorBike(
                    plateNo,
                    rental,
                    make,
                    model,
                    color,
                    fuelType,
                    VehicleType.valueOf(type.toUpperCase()),
                    hasKickStart,
                    hasBumpy
            );

            manager.addvehicle(vehicle);

        } else {
            System.out.println("Invalid vehicle type");
        }
    }

    private void deleteVehicle() {
        System.out.println("Enter the plate number to delete: ");
        String plateNo = inputText();
        manager.deleteVehicle(plateNo);
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

    private double inputNumber(int range1, int range2) {
        while (true) {
            try {
                int option = Integer.parseInt(scanner.nextLine());
                if (option >= range1 && option <= range2) {
                    return option;
                } else {
                    System.out.println("Please type a number between: " + range1 + "-" + range2);
                }
            } catch (NumberFormatException e) {
                System.out.println("Given input is not a number.");
            }
        }
    }

    private double inputNumber() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("You must enter a number");
            }
        }
    }

    private String inputText() {
        String input = scanner.nextLine();
        if (input.length() == 0) {
            System.out.println("Your input cannot be blank");
            inputText();
        }
        return input;
    }
}
