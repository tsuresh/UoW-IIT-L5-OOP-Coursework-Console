package models;

import controllers.ScheduleList;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public abstract class Vehicle {

    @Id
    @NotNull(message = "Plate number is required")
    protected String plateNo;
    @NotNull(message = "Day rental is required")
    protected double dayRental;
    @NotNull(message = "Vehicle make is mandatory")
    protected String make;
    @NotNull(message = "Vehicle model is mandatory")
    protected String model;
    @NotNull(message = "Vehicle color is mandatory")
    protected String color;
    @NotNull(message = "Fuel type is mandatory")
    protected String fuelType;
    protected ScheduleList scheduleList;
    @NotNull(message = "Vehicle type is mandatory")
    protected VehicleType type;

    public Vehicle() {
    }

    public Vehicle(String plateNo, double dayRental, String make, String model, String color, String fuelType, VehicleType type) {
        this.plateNo = plateNo;

        if (this.plateNo == null || this.plateNo.equals("")) {
            throw new IllegalArgumentException("Plate number cannot be blank");
        }

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(this.plateNo);
        if (m.find()) {
            throw new IllegalArgumentException("Invalid plate number");
        }

        this.dayRental = dayRental;

        if (this.dayRental < 0) {
            throw new IllegalArgumentException("Invalid day rental");
        }

        this.make = make;

        if (this.make == null || this.make.equals("")) {
            throw new IllegalArgumentException("Make cannot be blank");
        }

        this.model = model;

        if (this.model == null || this.model.equals("")) {
            throw new IllegalArgumentException("Model cannot be blank");
        }

        this.color = color;

        if (this.color == null || this.color.equals("")) {
            throw new IllegalArgumentException("Color cannot be blank");
        }

        this.fuelType = fuelType;

        if (this.fuelType == null || this.fuelType.equals("")) {
            throw new IllegalArgumentException("Fuel type cannot be blank");
        }

        this.type = type;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public double getDayRental() {
        return dayRental;
    }

    public void setDayRental(double dayRental) {
        this.dayRental = dayRental;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuelType() {
        return fuelType;
    }

    public ScheduleList getScheduleList() {
        return scheduleList;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public double calculateRent(){
        return 0;
    }

    public boolean isAvailable(Date date1, Date date2){
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Double.compare(vehicle.dayRental, dayRental) == 0 &&
                Objects.equals(plateNo, vehicle.plateNo) &&
                Objects.equals(make, vehicle.make) &&
                Objects.equals(model, vehicle.model) &&
                Objects.equals(color, vehicle.color) &&
                Objects.equals(fuelType, vehicle.fuelType) &&
                Objects.equals(scheduleList, vehicle.scheduleList) &&
                type == vehicle.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(plateNo, dayRental, make, model, color, fuelType, scheduleList, type);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "plateNo='" + plateNo + '\'' +
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