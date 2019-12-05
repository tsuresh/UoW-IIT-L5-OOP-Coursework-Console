package server;

import com.google.cloud.firestore.DocumentSnapshot;
import controllers.Constants;
import models.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.DatabaseUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "https://tsuresh.github.io", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private static final String TAG = "BookingController";

    public static ResponseEntity<Response> getAvailability(String plateNumber, String fromDate, String toDate) {
        Date dateFrom = null, dateTo = null;
        try {
            dateFrom = new Date(fromDate);
            dateTo = new Date(toDate);
        } catch (Exception e) {
            return new ResponseEntity(new Response(Constants.ERROR, "Unable to cast the given date"), HttpStatus.BAD_REQUEST);
        }
        long fromDateTimestamp = dateFrom.getTime();
        long toDateTimestamp = dateTo.getTime();
        for (DocumentSnapshot document : DatabaseUtil.getCollection(Constants.VEHICLES + "/" + plateNumber + "/scheduleList")) {
            Schedule schedule = document.toObject(Schedule.class);
            if (fromDateTimestamp <= schedule.getDateFrom() && schedule.getDateFrom() <= toDateTimestamp) {
                return new ResponseEntity(new Response(Constants.ERROR, "An existing booking clases with your date range"), HttpStatus.CONFLICT);
            }
            if (fromDateTimestamp <= schedule.getDateTo() && schedule.getDateTo() <= toDateTimestamp) {
                return new ResponseEntity(new Response(Constants.ERROR, "An existing booking clases with your date range"), HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity(new Response(Constants.SUCCESS, "The vehicle is available for booking"), HttpStatus.OK);
    }

    @GetMapping("/{vehicleID}")
    public List<Schedule> getBookings(@PathVariable String vehicleID) {
        List<Schedule> schedules = new ArrayList<>();
        for (DocumentSnapshot document : DatabaseUtil.getCollection(Constants.VEHICLES + "/" + vehicleID + "/scheduleList")) {
            Schedule schedule = document.toObject(Schedule.class);
            schedule.setBookingId(document.getId());
            schedules.add(schedule);
        }
        return schedules;
    }

    @PostMapping("/isBooked")
    public ResponseEntity<Response> isBooked(@Valid @RequestBody AvailabilityBody availabilityBody) {
        if (availabilityBody.getPlateNumber() == null || availabilityBody.getPlateNumber().isEmpty()) {
            return new ResponseEntity(new Response(Constants.ERROR, "Plate number must be there"), HttpStatus.BAD_REQUEST);
        }
        return getAvailability(availabilityBody.getPlateNumber(), availabilityBody.getDateFrom(), availabilityBody.getDateTo());
    }

    @PostMapping("")
    public ResponseEntity<Response> makeBooking(@Valid @RequestBody BookingBody bookingBody) {
        ResponseEntity<Response> availabilityResponse = getAvailability(bookingBody.getPlateNumber(), bookingBody.getDateFrom(), bookingBody.getDateTo());
        if (availabilityResponse.getBody().getMessage().equals(Constants.SUCCESS)) {
            return manageBooking(bookingBody, "");
        } else {
            return availabilityResponse;
        }
    }

    @PutMapping("")
    public ResponseEntity<Response> updateBooking(@Valid @RequestBody BookingBody bookingBody) {
        if (bookingBody.getBookingId() == null || bookingBody.getBookingId().isEmpty()) {
            return new ResponseEntity(new Response(Constants.ERROR, "THe booking ID must not be empty!"), HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Response> availabilityResponse = getAvailability(bookingBody.getPlateNumber(), bookingBody.getDateFrom(), bookingBody.getDateTo());
        if (availabilityResponse.getBody().getMessage().equals(Constants.SUCCESS)) {
            return manageBooking(bookingBody, bookingBody.getBookingId());
        } else {
            return availabilityResponse;
        }
    }

    @DeleteMapping("/{plateNumber}/{bookingId}")
    public ResponseEntity<Response> deleteBooking(@PathVariable String plateNumber, @PathVariable String bookingId) {
        if (DatabaseUtil.deleteData(Constants.VEHICLES + "/" + plateNumber + "/scheduleList", bookingId)) {
            return new ResponseEntity(new Response("SUCCESS", "Successfully deleted"), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(new Response("FAILED", "Unable to delete booking"), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<Response> manageBooking(BookingBody bookingBody, String bookingID) {
        if (bookingBody.getAddress() == null || bookingBody.getAddress().isEmpty()) {
            return new ResponseEntity(new Response(Constants.ERROR, "Your address must be there"), HttpStatus.BAD_REQUEST);
        }
        if (bookingBody.getFullName() == null || bookingBody.getFullName().isEmpty()) {
            return new ResponseEntity(new Response(Constants.ERROR, "Your full name must be there"), HttpStatus.BAD_REQUEST);
        }
        if (bookingBody.getContactNumber() == null || bookingBody.getContactNumber().isEmpty()) {
            return new ResponseEntity(new Response(Constants.ERROR, "Your contact number must be there"), HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity(new Response(Constants.SUCCESS, "Successfully made the booking"), HttpStatus.CREATED);
            } else {
                return new ResponseEntity(new Response(Constants.ERROR, "Failed to add new booking"), HttpStatus.BAD_REQUEST);
            }
        } else {
            if (DatabaseUtil.addData(schedule, Constants.VEHICLES + "/" + bookingBody.getPlateNumber() + "/scheduleList", bookingID)) {
                return new ResponseEntity(new Response(Constants.SUCCESS, "Successfully updated the booking"), HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(new Response(Constants.ERROR, "Failed to update the booking"), HttpStatus.BAD_REQUEST);
            }
        }
    }
}
