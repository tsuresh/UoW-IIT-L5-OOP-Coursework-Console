package models;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Schedule implements Serializable {

    private String bookingId;
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

    public Schedule(@NotNull(message = "Address is required") String address, boolean isVerified, @NotNull(message = "Contact number is required") String contactNumber, @NotNull(message = "Drop date is required") long dateTo, @NotNull(message = "Your name is required") String fullName, @NotNull(message = "Pickup date is required") long dateFrom) {
        this.address = address;
        this.isVerified = isVerified;
        this.contactNumber = contactNumber;
        this.dateTo = dateTo;
        this.fullName = fullName;
        this.dateFrom = dateFrom;
    }

    public Schedule(String bookingId, @NotNull(message = "Address is required") String address, boolean isVerified, @NotNull(message = "Contact number is required") String contactNumber, @NotNull(message = "Drop date is required") long dateTo, @NotNull(message = "Your name is required") String fullName, @NotNull(message = "Pickup date is required") long dateFrom) {
        this.bookingId = bookingId;
        this.address = address;
        this.isVerified = isVerified;
        this.contactNumber = contactNumber;
        this.dateTo = dateTo;
        this.fullName = fullName;
        this.dateFrom = dateFrom;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
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
}
