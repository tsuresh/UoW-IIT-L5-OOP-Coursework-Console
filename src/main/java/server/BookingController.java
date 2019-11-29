package server;

import com.google.cloud.firestore.DocumentSnapshot;
import controllers.Constants;
import models.Schedule;
import org.springframework.web.bind.annotation.*;
import utils.DatabaseUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    @PostMapping("/isBooked")
    public Response isBooked(@RequestBody BookingBody bookingBody) {
        Response validateBodyResponse = validateBookingInput(bookingBody);
        if (validateBodyResponse.getMessage().equals(Constants.SUCCESS)) {
            return getAvailability(bookingBody.getPlateNumber(), bookingBody.getDateFrom(), bookingBody.getDateTo());
        }
        return validateBodyResponse;
    }

    @GetMapping("/{vehicleID}")
    public List<Schedule> getBookings(@PathVariable String vehicleID) {
        List<Schedule> schedules = new ArrayList<>();
        for (DocumentSnapshot document : DatabaseUtil.getCollection(Constants.VEHICLES + "/" + vehicleID + "/scheduleList")) {
            Schedule schedule = document.toObject(Schedule.class);
            schedules.add(schedule);
        }
        return schedules;
    }

    @PostMapping("")
    public Response makeBooking(@RequestBody BookingBody bookingBody) {
        return manageBooking(bookingBody, "");
    }

    @PutMapping("")
    public Response updateBooking(@RequestBody BookingBody bookingBody) {
        if (bookingBody.getBookingId() == null || bookingBody.getBookingId().isEmpty()) {
            return new Response(Constants.ERROR, "THe booking ID must not be empty!");
        }
        return manageBooking(bookingBody, bookingBody.getBookingId());
    }

    @DeleteMapping("")
    public Response deleteBooking(@RequestBody BookingBody bookingBody) {
        if (bookingBody.getBookingId() == null || bookingBody.getBookingId().isEmpty()) {
            return new Response(Constants.ERROR, "THe booking ID must not be empty!");
        }
        if (DatabaseUtil.deleteData(Constants.VEHICLES + "/" + bookingBody.getPlateNumber() + "/scheduleList", bookingBody.getBookingId())) {
            return new Response("SUCCESS", "Successfully deleted");
        } else {
            return new Response("FAILED", "Unable to delete booking");
        }
    }

    private Response manageBooking(BookingBody bookingBody, String bookingID) {
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
                if (bookingID.isEmpty()) {
                    if (DatabaseUtil.addData(schedule, Constants.VEHICLES + "/" + bookingBody.getPlateNumber() + "/scheduleList")) {
                        return new Response(Constants.SUCCESS, "Successfully made the booking");
                    } else {
                        return new Response(Constants.ERROR, "Failed to add new booking");
                    }
                } else {
                    if (DatabaseUtil.addData(schedule, Constants.VEHICLES + "/" + bookingBody.getPlateNumber() + "/scheduleList", bookingID)) {
                        return new Response(Constants.SUCCESS, "Successfully updated the booking");
                    } else {
                        return new Response(Constants.ERROR, "Failed to update the booking");
                    }
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
