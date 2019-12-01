package server;

import com.google.cloud.firestore.DocumentSnapshot;
import controllers.Constants;
import models.Car;
import models.MotorBike;
import models.Vehicle;
import models.VehicleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import utils.DatabaseUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private static final String TAG = "VehicleController";
    Logger logger = LoggerFactory.getLogger(Application.class);

    @GetMapping("")
    public static List<Vehicle> getVehiclesList() {
        List<Vehicle> vehicleList = new ArrayList<>();
        for (DocumentSnapshot document : DatabaseUtil.getCollection(Constants.VEHICLES)) {
            if (document.getData().get(Constants.TYPE).equals(VehicleType.CAR.toString())) {
                vehicleList.add(document.toObject(Car.class));
            } else if (document.getData().get(Constants.TYPE).equals(VehicleType.MOTORBIKE.toString())) {
                vehicleList.add(document.toObject(MotorBike.class));
            }
        }
        return vehicleList;
    }

    @GetMapping("/available")
    public static List<Vehicle> getAvailableVehiclesList(@Valid @RequestBody AvailabilityBody availabilityBody) {
        List<Vehicle> vehicleList = new ArrayList<>();
        for (Vehicle vehicle : getVehiclesList()) {
            Response availabilityResponse = BookingController.getAvailability(vehicle.getPlateNo(), availabilityBody.getDateFrom(), availabilityBody.getDateTo());
            if (availabilityResponse.getMessage().equals(Constants.SUCCESS)) {
                vehicleList.add(vehicle);
            }
        }
        return vehicleList;
    }

    @PostMapping("/addCar")
    public static Response addCar(@Valid @RequestBody Car car) {
        if (DatabaseUtil.addData(car, Constants.VEHICLES, car.getPlateNo())) {
            return new Response("SUCCESS", "Successfully added the vehicle");
        } else {
            return new Response("ERROR", "Failed adding the vehicle");
        }
    }

    @PostMapping("/addMotorbike")
    public static Response addMotorbike(@Valid @RequestBody MotorBike motorBike) {
        if (DatabaseUtil.addData(motorBike, Constants.VEHICLES, motorBike.getPlateNo())) {
            return new Response("SUCCESS", "Successfully added the vehicle");
        } else {
            return new Response("ERROR", "Failed adding the vehicle");
        }
    }

    @GetMapping("/{vehicleID}")
    public static Vehicle getVehicle(@PathVariable String vehicleID) {
        DocumentSnapshot vehicleSnapsot = DatabaseUtil.getDocument(Constants.VEHICLES, vehicleID);
        if (vehicleSnapsot.getData().get(Constants.TYPE).equals(VehicleType.CAR.toString())) {
            return vehicleSnapsot.toObject(Car.class);
        } else {
            return vehicleSnapsot.toObject(MotorBike.class);
        }
    }

    @DeleteMapping("/{plateNumber}")
    public static Response deleteVehicle(@PathVariable String plateNumber) {
        if (DatabaseUtil.deleteData(Constants.VEHICLES, plateNumber)) {
            return new Response("SUCCESS", "Successfully deleted");
        } else {
            return new Response("FAILED", "Unable to delete booking");
        }
    }

}
