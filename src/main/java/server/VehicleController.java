package server;

import com.google.cloud.firestore.DocumentSnapshot;
import controllers.Constants;
import models.Car;
import models.MotorBike;
import models.Vehicle;
import models.VehicleType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.DatabaseUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private static final String TAG = "VehicleController";

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

    @PostMapping("/available")
    public static List<Vehicle> getAvailableVehiclesList(@Valid @RequestBody AvailabilityBody availabilityBody) {
        List<Vehicle> vehicleList = new ArrayList<>();
        for (Vehicle vehicle : getVehiclesList()) {
            ResponseEntity<Response> availabilityResponse = BookingController.getAvailability(vehicle.getPlateNo(), availabilityBody.getDateFrom(), availabilityBody.getDateTo());
            if (availabilityResponse.getBody().getMessage().equals(Constants.SUCCESS)) {
                vehicleList.add(vehicle);
            }
        }
        return vehicleList;
    }

    @PostMapping("/addCar")
    public static ResponseEntity<Response> addCar(@Valid @RequestBody Car car) {
        if (DatabaseUtil.addData(car, Constants.VEHICLES, car.getPlateNo())) {
            return new ResponseEntity(new Response("SUCCESS", "Successfully added the vehicle"), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Response("ERROR", "Failed adding the vehicle"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addMotorbike")
    public static ResponseEntity<Response> addMotorbike(@Valid @RequestBody MotorBike motorBike) {
        if (DatabaseUtil.addData(motorBike, Constants.VEHICLES, motorBike.getPlateNo())) {
            return new ResponseEntity(new Response("SUCCESS", "Successfully added the vehicle"), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Response("ERROR", "Failed adding the vehicle"), HttpStatus.BAD_REQUEST);
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
    public static ResponseEntity<Response> deleteVehicle(@PathVariable String plateNumber) {
        if (DatabaseUtil.deleteData(Constants.VEHICLES, plateNumber)) {
            return new ResponseEntity(new Response("SUCCESS", "Successfully deleted"), HttpStatus.OK);
        } else {
            return new ResponseEntity(new Response("FAILED", "Unable to delete booking"), HttpStatus.BAD_REQUEST);
        }
    }

}
