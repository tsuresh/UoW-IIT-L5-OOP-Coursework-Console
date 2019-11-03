package controllers;

import interfaces.RentalVehicleManager;
import models.Vehicle;
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
            setVehicleList(DatabaseUtil.getCollection(Constants.VEHICLES));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addvehicle(Vehicle vehicle) {
        if (DatabaseUtil.addData(vehicle, Constants.VEHICLES, vehicle.getPlateNo())) {
            vehicleList.add(vehicle);
            System.out.println("Successfully added vehicle");
            return true;
        } else {
            System.out.println("Failed adding a new vehicle to the database");
        }
        return false;
    }

    @Override
    public boolean deleteVehicle(String plateNo) {
        Vehicle vehicle = searchVehicle(plateNo);
        if (DatabaseUtil.deleteData(Constants.VEHICLES, vehicle.getPlateNo())) {
            vehicleList.remove(vehicle);
            System.out.println("Successfully deleted vehicle");
            return true;
        } else {
            System.out.println("Unable to delete vehicle");
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