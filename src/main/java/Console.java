import controllers.WestminsterRentalVehicleManager;
import models.Car;
import models.MotorBike;
import models.Vehicle;
import models.VehicleType;
import utils.InputUtil;

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
            option = (int) InputUtil.inputNumber(scanner, 1, 8);
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
        String plateNo = InputUtil.inputText(scanner);

        System.out.println("Enter the day rental: ");
        double rental = InputUtil.inputNumber(scanner);

        System.out.println("Enter the vehicle make: ");
        String make = InputUtil.inputText(scanner);

        System.out.println("Enter the vehicle model: ");
        String model = InputUtil.inputText(scanner);

        System.out.println("Enter the vehicle color: ");
        String color = InputUtil.inputText(scanner);

        System.out.println("Enter the fuel type: ");
        String fuelType = InputUtil.inputText(scanner);

        System.out.println("Enter the vehicle type: MOTORBIKE/CAR");
        String type = InputUtil.inputText(scanner);

        if (type.equalsIgnoreCase("car")) {

            System.out.println("Number of doors: ");
            int doors = (int) InputUtil.inputNumber(scanner);

            System.out.println("Number of windows: ");
            int windows = (int) InputUtil.inputNumber(scanner);

            System.out.println("Has AC? yes/no: ");
            String ac = InputUtil.inputText(scanner);
            boolean hasAC = false;
            if (ac.equalsIgnoreCase("yes")) {
                hasAC = true;
            }

            System.out.println("Body type: ");
            String bodyType = InputUtil.inputText(scanner);

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
            String kick = InputUtil.inputText(scanner);
            boolean hasKickStart = false;
            if (kick.equalsIgnoreCase("yes")) {
                hasKickStart = true;
            }

            System.out.println("Has Bumpy Tires? yes/no: ");
            String bumpy = InputUtil.inputText(scanner);
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
        String plateNo = InputUtil.inputText(scanner);
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


}
