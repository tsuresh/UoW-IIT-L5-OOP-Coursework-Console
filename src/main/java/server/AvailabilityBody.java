package server;

import javax.validation.constraints.NotNull;

public class AvailabilityBody {

    private String plateNumber;
    @NotNull(message = "Pickup date is required")
    private String dateFrom;
    @NotNull(message = "Drop date is required")
    private String dateTo;

    public AvailabilityBody() {
    }

    public AvailabilityBody(String plateNumber, @NotNull(message = "Pickup date is required") String dateFrom, @NotNull(message = "Drop date is required") String dateTo) {
        this.plateNumber = plateNumber;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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
}
