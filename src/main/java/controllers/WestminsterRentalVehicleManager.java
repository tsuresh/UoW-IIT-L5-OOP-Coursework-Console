package controllers;

import interfaces.RentalVehicleManager;
import models.Car;
import models.MotorBike;
import models.Vehicle;
import models.VehicleType;
import utils.DatabaseUtil;

import javax.xml.crypto.Data;
import java.util.ArrayList;
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
            for(Object object : DatabaseUtil.getCollection(Constants.VEHICLES)){
                if(object.getClass().getField(Constants.TYPE).equals(VehicleType.CAR)){
                    vehicleList.add((Car) object);
                } else if(object.getClass().getField(Constants.TYPE).equals(VehicleType.MOTORBIKE)){
                    vehicleList.add((MotorBike) object);
                }
            }
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
    public boolean deleteVehicle(Vehicle vehicle) {
        if(DatabaseUtil.deleteData(Constants.VEHICLES, vehicle.getPlateNo())){
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

    }

    @Override
    public boolean save() {
        return false;
    }
}
