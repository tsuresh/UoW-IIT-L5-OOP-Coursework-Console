import controllers.WestminsterRentalVehicleManager;
import models.Date;
import models.Vehicle;

import java.util.List;

public class GUI {
    private WestminsterRentalVehicleManager westminsterRentalVehicleManager;
    private List<Vehicle> vehicleList;

    public GUI() {
        westminsterRentalVehicleManager = new WestminsterRentalVehicleManager();
        vehicleList = westminsterRentalVehicleManager.getVehicleList();
    }

    public WestminsterRentalVehicleManager getManager() {
        return westminsterRentalVehicleManager;
    }

    public void displayList(){

    }

    public void displayFiltered(String flag){

    }

    public boolean getAvailability(Vehicle vehicle, Date date1, Date date2){
        return false;
    }

    public void makeReservation(Vehicle vehicle, Date date1, Date date2){
        if(getAvailability(vehicle,date1,date2)){
            //make reservation
        }
    }
}
