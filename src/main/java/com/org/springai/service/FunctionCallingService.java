package com.org.springai.service;

import com.org.springai.model.BookingRequest;
import com.org.springai.model.BookingResponse;
import com.org.springai.model.Status;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class FunctionCallingService implements Function<BookingRequest, BookingResponse> {

    private static final Map<String, Status> BOOKINGS = Map.of(
            "H001", Status.PENDING,
            "H002", Status.CONFIRMED,
            "H003", Status.CANCELLED
    );

    @Override
    public BookingResponse apply(BookingRequest bookingRequest) {
        Status status = BOOKINGS.getOrDefault(bookingRequest.bookingId(), Status.PENDING);
        return new BookingResponse(bookingRequest.bookingId(), status.name());
    }
}
