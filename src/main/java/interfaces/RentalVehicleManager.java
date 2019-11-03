package interfaces;

import models.Vehicle;

public interface RentalVehicleManager {

    int VEHICLE_COUNT = 50;

    boolean addvehicle(Vehicle vehicle);

    boolean deleteVehicle(String plateNo);

    void printList();

    boolean save();
}
