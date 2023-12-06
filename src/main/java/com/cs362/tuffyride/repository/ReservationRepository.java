package com.cs362.tuffyride.repository;

import com.cs362.tuffyride.model.Reservation;
import com.cs362.tuffyride.model.Ride;
import com.cs362.tuffyride.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByGuest(User guest);

    List<Reservation> findByRide(Ride ride);

    Reservation findByIdAndGuest(Long id, User guest); // for deletion
    List<Reservation> findByRideAndCheckoutDateAfter(Ride ride, LocalDate date);

}
