package server;

import com.google.cloud.firestore.DocumentSnapshot;
import controllers.Constants;
import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class VehicleController {

    private static final String TAG = "VehicleController";
    Logger logger = LoggerFactory.getLogger(Application.class);

    @GetMapping("/vehicles")
    public static List<Vehicle> getVehiclesList() {
        List<Vehicle> vehicleList = new ArrayList<>();
        for (DocumentSnapshot document : DatabaseUtil.getCollection(Constants.VEHICLES)) {
            if (document.getData().get(Constants.TYPE).equals(VehicleType.CAR.toString())) {
                vehicleList.add(document.toObject(Car.class));
            } else if (document.getData().get(Constants.TYPE).equals(VehicleType.MOTORBIKE.toString())) {
                vehicleList.add(document.toObject(MotorBike.class));
            }
        }
        return vehicleList;
    }

    @PostMapping("/bookings/isBooked")
    public Response isBooked(@RequestBody BookingBody bookingBody) {
        Response validateBodyResponse = validateBookingInput(bookingBody);
        if (validateBodyResponse.getMessage().equals(Constants.SUCCESS)) {
            return getAvailability(bookingBody.getPlateNumber(), bookingBody.getDateFrom(), bookingBody.getDateTo());
        }
        return validateBodyResponse;
    }

    @PostMapping("/bookings/makeBooking")
    public Response makeBooking(@RequestBody BookingBody bookingBody) {
        if (bookingBody.getAddress() == null || bookingBody.getAddress().isEmpty()) {
            return new Response(Constants.ERROR, "Your address must be there");
        }
        if (bookingBody.getFullName() == null || bookingBody.getFullName().isEmpty()) {
            return new Response(Constants.ERROR, "Your full name must be there");
        }
        if (bookingBody.getContactNumber() == null || bookingBody.getContactNumber().isEmpty()) {
            return new Response(Constants.ERROR, "Your contact number must be there");
        }
        Response validateBodyResponse = validateBookingInput(bookingBody);
        if (validateBodyResponse.getMessage().equals(Constants.SUCCESS)) {
            Response availabilityResponse = getAvailability(bookingBody.getPlateNumber(), bookingBody.getDateFrom(), bookingBody.getDateTo());
            if (availabilityResponse.getMessage().equals(Constants.SUCCESS)) {
                Schedule schedule = new Schedule(
                        bookingBody.getAddress(),
                        bookingBody.getIsVerified(),
                        bookingBody.getContactNumber(),
                        new Date(bookingBody.getDateTo()).getTime(),
                        bookingBody.getFullName(),
                        new Date(bookingBody.getDateFrom()).getTime()
                );
                if (DatabaseUtil.addData(schedule, Constants.VEHICLES + "/" + bookingBody.getPlateNumber() + "/scheduleList")) {
                    return new Response(Constants.SUCCESS, "Successfully made the booking");
                } else {
                    return new Response(Constants.ERROR, "Failed to add new booking");
                }
            } else {
                return availabilityResponse;
            }
        } else {
            return validateBodyResponse;
        }
    }

    private Response validateBookingInput(BookingBody bookingBody) {
        if (bookingBody.getPlateNumber() == null || bookingBody.getPlateNumber().isEmpty()) {
            return new Response(Constants.ERROR, "Plate number must be there");
        }
        if (bookingBody.getDateFrom() == null || bookingBody.getDateFrom().isEmpty()) {
            return new Response(Constants.ERROR, "Starting date must be there");
        }
        if (bookingBody.getDateTo() == null || bookingBody.getDateTo().isEmpty()) {
            return new Response(Constants.ERROR, "Returning date must be there");
        }
        return new Response(Constants.SUCCESS, "Validated");
    }

    private Response getAvailability(String plateNumber, String fromDate, String toDate) {
        Date dateFrom = null, dateTo = null;
        try {
            dateFrom = new Date(fromDate);
            dateTo = new Date(toDate);
        } catch (Exception e) {
            return new Response(Constants.ERROR, e.getMessage());
        }
        long fromDateTimestamp = dateFrom.getTime();
        long toDateTimestamp = dateTo.getTime();
        for (DocumentSnapshot document : DatabaseUtil.getCollection(Constants.VEHICLES + "/" + plateNumber + "/scheduleList")) {
            Schedule schedule = document.toObject(Schedule.class);
            if (fromDateTimestamp <= schedule.getDateFrom() && schedule.getDateFrom() <= toDateTimestamp) {
                return new Response(Constants.ERROR, "An existing booking clases with your date range");
            }
            if (fromDateTimestamp <= schedule.getDateTo() && schedule.getDateTo() <= toDateTimestamp) {
                return new Response(Constants.ERROR, "An existing booking clases with your date range");
            }
        }
        return new Response(Constants.SUCCESS, "The vehicle is available for booking");
    }


}
