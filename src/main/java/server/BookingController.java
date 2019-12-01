package server;

import com.google.cloud.firestore.DocumentSnapshot;
import controllers.Constants;
import models.Schedule;
import org.springframework.web.bind.annotation.*;
import utils.DatabaseUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    public static Response getAvailability(String plateNumber, String fromDate, String toDate) {
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

    @GetMapping("/{vehicleID}")
    public List<Schedule> getBookings(@PathVariable String vehicleID) {
        List<Schedule> schedules = new ArrayList<>();
        for (DocumentSnapshot document : DatabaseUtil.getCollection(Constants.VEHICLES + "/" + vehicleID + "/scheduleList")) {
            Schedule schedule = document.toObject(Schedule.class);
            schedules.add(schedule);
        }
        return schedules;
    }

    @PostMapping("/isBooked")
    public Response isBooked(@Valid @RequestBody AvailabilityBody availabilityBody) {
        if (availabilityBody.getPlateNumber() == null || availabilityBody.getPlateNumber().isEmpty()) {
            return new Response(Constants.ERROR, "Plate number must be there");
        }
        return getAvailability(availabilityBody.getPlateNumber(), availabilityBody.getDateFrom(), availabilityBody.getDateTo());
    }

    @PostMapping("")
    public Response makeBooking(@Valid @RequestBody BookingBody bookingBody) {
        return manageBooking(bookingBody, "");
    }

    @PutMapping("")
    public Response updateBooking(@Valid @RequestBody BookingBody bookingBody) {
        if (bookingBody.getBookingId() == null || bookingBody.getBookingId().isEmpty()) {
            return new Response(Constants.ERROR, "THe booking ID must not be empty!");
        }
        return manageBooking(bookingBody, bookingBody.getBookingId());
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
    }

    @DeleteMapping("")
    public Response deleteBooking(@Valid @RequestBody BookingBody bookingBody) {
        if (bookingBody.getBookingId() == null || bookingBody.getBookingId().isEmpty()) {
            return new Response(Constants.ERROR, "THe booking ID must not be empty!");
        }
        if (DatabaseUtil.deleteData(Constants.VEHICLES + "/" + bookingBody.getPlateNumber() + "/scheduleList", bookingBody.getBookingId())) {
            return new Response("SUCCESS", "Successfully deleted");
        } else {
            return new Response("FAILED", "Unable to delete booking");
        }
    }
}
