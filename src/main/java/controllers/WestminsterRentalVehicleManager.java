package controllers;

import interfaces.RentalVehicleManager;
import models.Car;
import models.MotorBike;
import models.Vehicle;
import models.VehicleType;
import server.Response;
import server.VehicleController;
import utils.DatabaseUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WestminsterRentalVehicleManager implements RentalVehicleManager {

    private List<Vehicle> vehicleList = new ArrayList<>();

    public WestminsterRentalVehicleManager() {
        DatabaseUtil.connect();
        syncDB();
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    private void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    private boolean syncDB() {
        try {
            setVehicleList(VehicleController.getVehiclesList());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addvehicle(Vehicle vehicle) {
        if (vehicle.getType().equals(VehicleType.CAR)) {
            Response addResp = VehicleController.addCar((Car) vehicle);
            System.out.println(addResp.getDetail());
            if (addResp.getMessage().equals(Constants.SUCCESS)) {
                vehicleList.add(vehicle);
                return true;
            }
        } else if (vehicle.getType().equals(VehicleType.MOTORBIKE)) {
            Response addResp = VehicleController.addMotorbike((MotorBike) vehicle);
            System.out.println(addResp.getDetail());
            if (addResp.getMessage().equals(Constants.SUCCESS)) {
                vehicleList.add(vehicle);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteVehicle(String plateNo) {
        Vehicle vehicle = searchVehicle(plateNo);
        Response deleteResp = VehicleController.deleteVehicle(plateNo);
        System.out.println(deleteResp.getDetail());
        if (deleteResp.getMessage().equals(Constants.SUCCESS)) {
            vehicleList.remove(vehicle);
            return true;
        }
        return false;
    }

    @Override
    public void printList() {
        Collections.sort(vehicleList, new MakeComparator());
        System.out.printf("%-30s %-15s %n", "Plate number", "Type");
        System.out.println("-------------------------------------------------------------");
        for (Vehicle item : getVehicleList()) {
            System.out.printf("%-30s %-15s %n", item.getPlateNo(), item.getType());
        }
    }

    @Override
    public boolean save() {
        try {
            FileWriter fw = new FileWriter("output.csv");
            fw.write("Plate number,Vehicle type,Make,Model,Daily rental\n");
            for (Vehicle item : getVehicleList()) {
                fw.write(item.getPlateNo() + "," + item.getType() + "," + item.getMake() + "," + item.getModel() + "," + item.getDayRental() + "\n");
            }
            fw.close();
            System.out.println("Successfully saved to output.csv");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Vehicle searchVehicle(String plateNo) {
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getPlateNo().equalsIgnoreCase(plateNo)) {
                return vehicle;
            }
        }
        return null;
    }
}

class MakeComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return o1.getMake().compareTo(o2.getMake());
    }
}