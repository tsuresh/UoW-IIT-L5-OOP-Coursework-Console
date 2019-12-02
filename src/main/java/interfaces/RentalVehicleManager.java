package interfaces;

import models.Vehicle;

public interface RentalVehicleManager {

    boolean addvehicle(Vehicle vehicle);

    boolean deleteVehicle(String plateNo);

    void printList();

    boolean save();
}
