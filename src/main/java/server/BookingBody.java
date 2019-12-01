package server;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class BookingBody {

    @Id
    private String bookingId;
    @NotNull(message = "Plate number required")
    private String plateNumber;
    @NotNull(message = "Pickup date is required")
    private String dateFrom;
    @NotNull(message = "Drop date is required")
    private String dateTo;
    private String fullName;
    private String contactNumber;
    private String address;
    private boolean isVerified;

    public BookingBody() {
    }

    public BookingBody(String bookingId, String plateNumber, String dateFrom, String dateTo, String fullName, String contactNumber, String address, boolean isVerified) {
        this.bookingId = bookingId;
        this.plateNumber = plateNumber;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.address = address;
        this.isVerified = isVerified;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean verified) {
        this.isVerified = verified;
    }
}
