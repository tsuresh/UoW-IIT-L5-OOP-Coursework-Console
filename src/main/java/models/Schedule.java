package models;

import java.util.Objects;

public class Schedule {
    private Date pickupDate;
    private Date dropoffDate;

    public Schedule(Date pickupDate, Date dropoffDate) {
        this.pickupDate = pickupDate;
        this.dropoffDate = dropoffDate;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Date getDropoffDate() {
        return dropoffDate;
    }

    public void setDropoffDate(Date dropoffDate) {
        this.dropoffDate = dropoffDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return Objects.equals(pickupDate, schedule.pickupDate) &&
                Objects.equals(dropoffDate, schedule.dropoffDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickupDate, dropoffDate);
    }

    @Override
    public String toString() {
        return "models.Schedule{" +
                "pickupDate=" + pickupDate +
                ", dropoffDate=" + dropoffDate +
                '}';
    }
}
