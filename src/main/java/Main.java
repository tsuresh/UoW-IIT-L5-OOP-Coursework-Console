import controllers.WestminsterRentalVehicleManager;
import models.MotorBike;
import models.Vehicle;
import models.VehicleType;

public class Main {

    public static void main(String[] args) {
	    //Console console = new Console();
	    //console.displayMenu();


        WestminsterRentalVehicleManager manager = new WestminsterRentalVehicleManager();

        Vehicle vehicle = new MotorBike("1234",1000,"Honda", "JuloJulo","Red","Diesel", VehicleType.MOTORBIKE,false,false);
        manager.deleteVehicle(vehicle);

        //for(Vehicle veh : manager.getVehicleList()){
            System.out.println(manager.getVehicleList());
        //}

    }
}