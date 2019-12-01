package models;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Car extends Vehicle {
    @NotNull(message = "Number of doors are required")
    private int noOfDoors;
    @NotNull(message = "Number of windows are required")
    private int noOfWindows;
    @NotNull(message = "AC availability is required")
    private boolean hasAC;
    @NotNull(message = "Body type is required")
    private String bodyType;

    public Car() {
    }

    public Car(String plateNo, double dayRental, String make, String model, String color, String fuelType, VehicleType type, int noOfDoors, int noOfWindows, boolean hasAC, String bodyType) {
        super(plateNo, dayRental, make, model, color, fuelType, type);
        this.noOfDoors = noOfDoors;
        this.noOfWindows = noOfWindows;
        this.hasAC = hasAC;
        this.bodyType = bodyType;
    }

    public int getNoOfDoors() {
        return noOfDoors;
    }

    public void setNoOfDoors(int noOfDoors) {
        this.noOfDoors = noOfDoors;
    }

    public int getNoOfWindows() {
        return noOfWindows;
    }

    public void setNoOfWindows(int noOfWindows) {
        this.noOfWindows = noOfWindows;
    }

    public boolean isHasAC() {
        return hasAC;
    }

    public void setHasAC(boolean hasAC) {
        this.hasAC = hasAC;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return noOfDoors == car.noOfDoors &&
                noOfWindows == car.noOfWindows &&
                hasAC == car.hasAC &&
                bodyType.equals(car.bodyType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), noOfDoors, noOfWindows, hasAC, bodyType);
    }

    @Override
    public String toString() {
        return "Car{" +
                "noOfDoors=" + noOfDoors +
                ", noOfWindows=" + noOfWindows +
                ", hasAC=" + hasAC +
                ", bodyType='" + bodyType + '\'' +
                ", plateNo='" + plateNo + '\'' +
                ", dayRental=" + dayRental +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", scheduleList=" + scheduleList +
                ", type=" + type +
                '}';
    }
}
