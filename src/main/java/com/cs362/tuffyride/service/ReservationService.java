package com.cs362.tuffyride.service;

import com.cs362.tuffyride.exception.ReservationCollisionException;
import com.cs362.tuffyride.exception.ReservationNotFoundException;
import com.cs362.tuffyride.model.*;
import com.cs362.tuffyride.repository.ReservationRepository;
import com.cs362.tuffyride.repository.RideReservationDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private RideReservationDateRepository rideReservationDateRepository;
    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RideReservationDateRepository rideReservationDateRepository) {
        this.reservationRepository = reservationRepository;
        this.rideReservationDateRepository = rideReservationDateRepository;
    }
    public List<Reservation> listByGuest(String username) {
        return reservationRepository.findByGuest(new User.Builder().setUsername(username).build());
    }
    public List<Reservation> listByRide(Long rideId) {
        return reservationRepository.findByRide(new Ride.Builder().setId(rideId).build());
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(Reservation reservation) throws ReservationCollisionException {
        Set<Long> rideIds = rideReservationDateRepository.findByIdInAndDateBetween(Arrays.asList(reservation.getRide().getId()), reservation.getCheckinDate(), reservation.getCheckoutDate().minusDays(1));
        if (!rideIds.isEmpty()) {
            throw new ReservationCollisionException("Duplicate reservation");
        }
        List<RideReservedDate> reservedDates = new ArrayList<>();
        for (LocalDate date = reservation.getCheckinDate(); date.isBefore(reservation.getCheckoutDate()); date = date.plusDays(1)) {
            reservedDates.add(new RideReservedDate(new RideReservedDateKey(reservation.getRide().getId(), date), reservation.getRide()));
        }
        rideReservationDateRepository.saveAll(reservedDates);
        reservationRepository.save(reservation);
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(Long reservationId, String username) {
        Reservation reservation = reservationRepository.findByIdAndGuest(reservationId, new User.Builder().setUsername(username).build());
        if (reservation == null) {
            throw new ReservationNotFoundException("Reservation is not available");
        }
        for (LocalDate date = reservation.getCheckinDate(); date.isBefore(reservation.getCheckoutDate()); date = date.plusDays(1)) {
            rideReservationDateRepository.deleteById(new RideReservedDateKey(reservation.getRide().getId(), date));
        }
        reservationRepository.deleteById(reservationId);
    }
}
