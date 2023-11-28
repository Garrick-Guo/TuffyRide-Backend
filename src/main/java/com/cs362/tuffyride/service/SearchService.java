package com.cs362.tuffyride.service;

import com.cs362.tuffyride.model.Ride;
import com.cs362.tuffyride.repository.LocationRepository;
import com.cs362.tuffyride.repository.RideRepository;
import com.cs362.tuffyride.repository.RideReservationDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SearchService {
    private RideRepository rideRepository;
    private RideReservationDateRepository rideReservationDateRepository;
    private LocationRepository locationRepository;

    @Autowired
    public SearchService(RideRepository rideRepository, RideReservationDateRepository rideReservationDateRepository, LocationRepository locationRepository) {
        this.rideRepository = rideRepository;
        this.rideReservationDateRepository = rideReservationDateRepository;
        this.locationRepository = locationRepository;
    }

    public List<Ride> search(int guestNumber, LocalDate checkinDate, LocalDate checkoutDate, double lat, double lon, String distance) {
        List<Long> rideIds = locationRepository.searchByDistance(lat, lon, distance);
        if (rideIds == null || rideIds.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> reservedRideIds = rideReservationDateRepository.findByIdInAndDateBetween(rideIds, checkinDate, checkoutDate.minusDays(1));
        List<Long> filteredRideIds = new ArrayList<>();
        for (Long rideId : rideIds) {
            if (!reservedRideIds.contains(rideId)) {
                filteredRideIds.add(rideId);
            }
        }
        return rideRepository.findByIdInAndGuestNumberGreaterThanEqual(filteredRideIds, guestNumber);

    }
}
