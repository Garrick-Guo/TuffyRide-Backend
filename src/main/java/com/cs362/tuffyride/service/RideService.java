package com.cs362.tuffyride.service;

import com.cs362.tuffyride.exception.RideDeleteException;
import com.cs362.tuffyride.exception.RideNotExistException;
import com.cs362.tuffyride.model.*;
import com.cs362.tuffyride.repository.LocationRepository;
import com.cs362.tuffyride.repository.ReservationRepository;
import com.cs362.tuffyride.repository.RideRepository;
import com.cs362.tuffyride.repository.RideReservationDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RideService {
    private RideRepository rideRepository;
    private ImageStorageService imageStorageService;
    private LocationRepository locationRepository;
    private GeoCodingService geoCodingService;
    private ReservationRepository reservationRepository;
    private RideReservationDateRepository rideReservationDateRepository;


    @Autowired
    public RideService(RideRepository rideRepository, LocationRepository locationRepository, ReservationRepository reservationRepository, ImageStorageService imageStorageService, GeoCodingService geoCodingService, RideReservationDateRepository rideReservationDateRepository) {
        this.rideRepository = rideRepository;
        this.locationRepository = locationRepository;
        this.reservationRepository = reservationRepository;
        this.imageStorageService = imageStorageService;
        this.geoCodingService = geoCodingService;
        this.rideReservationDateRepository = rideReservationDateRepository;
    }


    public List<Ride> listByUser(String username) {
        return rideRepository.findByDriver(new User.Builder().setUsername(username).build());
    }

    public Ride findByIdAndDriver(Long rideId, String username) throws RideNotExistException {
        Ride ride = rideRepository.findByIdAndDriver(rideId, new User.Builder().setUsername(username).build());
        if (ride == null) {
            throw new RideNotExistException("ride doesn't exist");
        }
        return ride;
    }
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(Ride ride, MultipartFile[] images) {
        List<String> mediaLinks = Arrays.stream(images).parallel().map(image -> imageStorageService.save(image)).collect(Collectors.toList());
        List<RideImage> rideImages = new ArrayList<>();
        for (String mediaLink : mediaLinks) {
            rideImages.add(new RideImage(mediaLink, ride));
        }
        ride.setImages(rideImages);
        rideRepository.save(ride);

        Location location = geoCodingService.getLatLng(ride.getId(), ride.getPickUp());
        locationRepository.save(location);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(Long rideId, String username) throws RideNotExistException {
        Ride ride = rideRepository.findByIdAndDriver(rideId, new User.Builder().setUsername(username).build());
        if (ride == null) {
            throw new RideNotExistException("Stay doesn't exist");
        }
        List<Reservation> reservations = reservationRepository.findByRideAndCheckoutDateAfter(ride, LocalDate.now());
        if (reservations != null && reservations.size() > 0) {
            throw new RideDeleteException("Cannot delete stay with active reservation");
        }

        List<RideReservedDate> rideReservedDates = rideReservationDateRepository.findByRide(ride);

        for(RideReservedDate date : rideReservedDates) {
            rideReservationDateRepository.deleteById(date.getId());
        }

        rideRepository.deleteById(rideId);
    }
}

