package com.cs362.tuffyride.repository;

import com.cs362.tuffyride.model.Ride;
import com.cs362.tuffyride.model.RideReservedDate;
import com.cs362.tuffyride.model.RideReservedDateKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface RideReservationDateRepository extends JpaRepository<RideReservedDate, RideReservedDateKey> {
    @Query(value = "SELECT srd.id.ride_id FROM RideReservedDate srd WHERE srd.id.ride_id IN ?1 AND srd.id.date BETWEEN ?2 AND ?3 GROUP BY srd.id.ride_id")
    Set<Long> findByIdInAndDateBetween(List<Long> rideIds, LocalDate startDate, LocalDate endDate);
    List<RideReservedDate> findByRide(Ride ride);
}
