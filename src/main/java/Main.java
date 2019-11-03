import controllers.WestminsterRentalVehicleManager;
import models.MotorBike;
import models.Vehicle;
import models.VehicleType;

public class Main {

    public static void main(String[] args) {
	    //Console console = new Console();
	    //console.displayMenu();


        WestminsterRentalVehicleManager manager = new WestminsterRentalVehicleManager();

        Vehicle vehicle = new MotorBike("9999", 1000, "Audi", "Audi", "Red", "Diesel", VehicleType.MOTORBIKE, false, false);
        manager.addvehicle(vehicle);

        manager.printList();
        manager.save();

    }
}