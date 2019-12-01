package models;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class Schedule implements Serializable {

    @NotNull(message = "Address is required")
    private String address;
    private boolean isVerified;
    @NotNull(message = "Contact number is required")
    private String contactNumber;
    @NotNull(message = "Drop date is required")
    private long dateTo;
    @NotNull(message = "Your name is required")
    private String fullName;
    @NotNull(message = "Pickup date is required")
    private long dateFrom;

    public Schedule() {
    }

    public Schedule(String address, boolean isVerified, String contactNumber, long dateTo, String fullName, long dateFrom) {
        this.address = address;
        this.isVerified = isVerified;
        this.contactNumber = contactNumber;
        this.dateTo = dateTo;
        this.fullName = fullName;
        this.dateFrom = dateFrom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean verified) {
        this.isVerified = verified;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public long getDateTo() {
        return dateTo;
    }

    public void setDateTo(long dateTo) {
        this.dateTo = dateTo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(long dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return isVerified == schedule.isVerified &&
                dateTo == schedule.dateTo &&
                dateFrom == schedule.dateFrom &&
                Objects.equals(address, schedule.address) &&
                Objects.equals(contactNumber, schedule.contactNumber) &&
                Objects.equals(fullName, schedule.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, isVerified, contactNumber, dateTo, fullName, dateFrom);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "address='" + address + '\'' +
                ", isVerified=" + isVerified +
                ", contactNumber='" + contactNumber + '\'' +
                ", dateTo=" + dateTo +
                ", fullName='" + fullName + '\'' +
                ", dateFrom=" + dateFrom +
                '}';
    }
}
