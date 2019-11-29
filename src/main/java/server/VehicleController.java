package server;

import com.google.cloud.firestore.DocumentSnapshot;
import controllers.Constants;
import models.Car;
import models.MotorBike;
import models.Vehicle;
import models.VehicleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.DatabaseUtil;

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

    @GetMapping("/{vehicleID}")
    public static Vehicle getVehicle(@PathVariable String vehicleID) {
        DocumentSnapshot vehicleSnapsot = DatabaseUtil.getDocument(Constants.VEHICLES, vehicleID);
        if (vehicleSnapsot.getData().get(Constants.TYPE).equals(VehicleType.CAR.toString())) {
            return vehicleSnapsot.toObject(Car.class);
        } else {
            return vehicleSnapsot.toObject(MotorBike.class);
        }
    }

}
