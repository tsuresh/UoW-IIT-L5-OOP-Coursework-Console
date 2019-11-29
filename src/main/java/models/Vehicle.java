package models;

import controllers.ScheduleList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public abstract class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected String plateNo;
    protected double dayRental;
    protected String make;
    protected String model;
    protected String color;
    protected String fuelType;
    protected ScheduleList scheduleList;
    protected VehicleType type;

    public Vehicle() {
    }

    public Vehicle(String plateNo, double dayRental, String make, String model, String color, String fuelType, VehicleType type) {
        this.plateNo = plateNo;
        this.dayRental = dayRental;
        this.make = make;
        this.model = model;
        this.color = color;
        this.fuelType = fuelType;
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