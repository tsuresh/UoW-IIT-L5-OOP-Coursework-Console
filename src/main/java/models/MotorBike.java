package models;

import java.util.Objects;

public class MotorBike extends Vehicle{
    private boolean hasKickStart;
    private boolean hasBumpyTires;

    public MotorBike(String plateNo, double dayRental, String make, String model, String color, String fuelType, VehicleType type, boolean hasKickStart, boolean hasBumpyTires) {
        super(plateNo, dayRental, make, model, color, fuelType, type);
        this.hasKickStart = hasKickStart;
        this.hasBumpyTires = hasBumpyTires;
    }

    public boolean isHasKickStart() {
        return hasKickStart;
    }

    public void setHasKickStart(boolean hasKickStart) {
        this.hasKickStart = hasKickStart;
    }

    public boolean isHasBumpyTires() {
        return hasBumpyTires;
    }

    public void setHasBumpyTires(boolean hasBumpyTires) {
        this.hasBumpyTires = hasBumpyTires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MotorBike motorBike = (MotorBike) o;
        return hasKickStart == motorBike.hasKickStart &&
                hasBumpyTires == motorBike.hasBumpyTires;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hasKickStart, hasBumpyTires);
    }

    @Override
    public String toString() {
        return "MotorBike{" +
                "hasKickStart=" + hasKickStart +
                ", hasBumpyTires=" + hasBumpyTires +
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
