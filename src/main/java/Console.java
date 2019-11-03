import controllers.WestminsterRentalVehicleManager;
import interfaces.RentalVehicleManager;
import models.Vehicle;

public class Console implements RentalVehicleManager {
    private WestminsterRentalVehicleManager westminsterRentalVehicleManager;

    public Console() {
        westminsterRentalVehicleManager = new WestminsterRentalVehicleManager();
    }

    public WestminsterRentalVehicleManager getManager() {
        return westminsterRentalVehicleManager;
    }

    public void displayGUI(){

    }

    public void displayMenu(){

    }

    @Override
    public boolean addvehicle(Vehicle vehicle) {
        return false;
    }

    @Override
    public boolean deleteVehicle(Vehicle vehicle) {
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
