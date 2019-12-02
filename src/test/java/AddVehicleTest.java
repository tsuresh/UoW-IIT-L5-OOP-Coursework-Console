import controllers.WestminsterRentalVehicleManager;
import models.Car;
import models.MotorBike;
import models.Vehicle;
import models.VehicleType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

public class AddVehicleTest {

    @InjectMocks
    WestminsterRentalVehicleManager manager = new WestminsterRentalVehicleManager();

    @Test
    public void addCarOfCarType() {
        Vehicle vehicle = new Car("0000", 1500, "Test_Audi", "Test_A3", "Black", "Diesel", VehicleType.CAR, 4, 4, true, "Car");
        assertTrue(manager.addvehicle(vehicle));
    }

    @Test
    public void addCarOfNonType() {
        Vehicle vehicle = new Car("0000", 1500, "Test_Audi", "Test_A3", "Black", "Diesel", VehicleType.MOTORBIKE, 4, 4, true, "Car");
        assertFalse(manager.addvehicle(vehicle));
    }

    @Test
    public void addMotorBikeofMotorbikeType() {
        Vehicle vehicle = new MotorBike("0000", 1000, "Honda", "CT100", "Red", "Petrol", VehicleType.MOTORBIKE, true, true);
        assertTrue(manager.addvehicle(vehicle));
    }

    @Test
    public void addMotorBikeofNonType() {
        Vehicle vehicle = new MotorBike("0000", 1000, "Honda", "CT100", "Red", "Petrol", VehicleType.CAR, true, true);
        assertFalse(manager.addvehicle(vehicle));
    }

    @Test
    public void addVehicleNegativeRental() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vehicle vehicle = new MotorBike("0000", -1000, "Honda", "CT100", "Red", "Petrol", VehicleType.MOTORBIKE, true, true);
            manager.addvehicle(vehicle);
        });
    }

    @Test
    public void addVehicleInvalidPlateNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vehicle vehicle = new MotorBike("-0000#$", 1000, "Honda", "CT100", "Red", "Petrol", VehicleType.MOTORBIKE, true, true);
            manager.addvehicle(vehicle);
        });
    }

    @Test
    public void addVehicleBlankPlateNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vehicle vehicle = new MotorBike("", 1000, "Honda", "CT100", "Red", "Petrol", VehicleType.MOTORBIKE, true, true);
            manager.addvehicle(vehicle);
        });
    }

    @Test
    public void addVehicleBlankMake() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vehicle vehicle = new MotorBike("0000", 1000, "", "CT100", "Red", "Petrol", VehicleType.MOTORBIKE, true, true);
            manager.addvehicle(vehicle);
        });
    }

    @Test
    public void addVehicleBlankModel() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vehicle vehicle = new MotorBike("0000", 1000, "Honda", "", "Red", "Petrol", VehicleType.MOTORBIKE, true, true);
            manager.addvehicle(vehicle);
        });
    }

    @Test
    public void addVehicleBlankColor() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vehicle vehicle = new MotorBike("0000", 1000, "Honda", "CT100", "", "Petrol", VehicleType.MOTORBIKE, true, true);
            manager.addvehicle(vehicle);
        });
    }

    @Test
    public void addVehicleBlankFuelType() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vehicle vehicle = new MotorBike("0000", 1000, "Honda", "CT100", "Black", "", VehicleType.MOTORBIKE, true, true);
            manager.addvehicle(vehicle);
        });
    }

    @Test
    public void addCarInvalidDoorCount() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vehicle vehicle = new Car("0000", 1500, "Test_Audi", "Test_A3", "Black", "Diesel", VehicleType.CAR, -4, 4, true, "Car");
            assertTrue(manager.addvehicle(vehicle));
        });
    }

    @Test
    public void addCarInvalidWindowCount() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vehicle vehicle = new Car("0000", 1500, "Test_Audi", "Test_A3", "Black", "Diesel", VehicleType.CAR, 4, -4, true, "Car");
            assertTrue(manager.addvehicle(vehicle));
        });
    }

    @Test
    public void addCarBlankBodyType() {
        assertThrows(IllegalArgumentException.class, () -> {
            Vehicle vehicle = new Car("0000", 1500, "Test_Audi", "Test_A3", "Black", "Diesel", VehicleType.CAR, 4, 4, true, "");
            assertTrue(manager.addvehicle(vehicle));
        });
    }

}
