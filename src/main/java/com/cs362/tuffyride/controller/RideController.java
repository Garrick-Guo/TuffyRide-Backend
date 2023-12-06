package com.cs362.tuffyride.controller;

import com.cs362.tuffyride.model.Reservation;
import com.cs362.tuffyride.model.Ride;
import com.cs362.tuffyride.model.User;
import com.cs362.tuffyride.service.ReservationService;
import com.cs362.tuffyride.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;


@RestController
public class RideController {
    private RideService rideService;
    private ReservationService reservationService;

    @Autowired
    public RideController(RideService rideService, ReservationService reservationService) {
        this.rideService = rideService;
        this.reservationService = reservationService;
    }

    @GetMapping(value = "/rides")
    public List<Ride> listRides(Principal principal) {
        return rideService.listByUser(principal.getName());
    }

    @GetMapping(value = "/rides/{rideId}")
    public Ride getRide(
            @PathVariable Long rideId, Principal principal) {
        return rideService.findByIdAndDriver(rideId, principal.getName());
    }

    @PostMapping("/rides")
    public void addRide (
        @RequestParam("name") String name,
        @RequestParam("pick_up") String pickUp,
        @RequestParam("drop_off") String dropOff,
        @RequestParam("description") String description,
        @RequestParam("driver") String driver,
        @RequestParam("guest_number") int guestNumber,
        @RequestParam("images") MultipartFile[] images,
        Principal principal){
            Ride ride = new Ride.Builder().setName(name)
                    .setPickUp(pickUp)
                    .setDropOff(dropOff)
                    .setDescription(description)
                    .setGuestNumber(guestNumber)
                    .setDriver(new User.Builder().setUsername(principal.getName()).build())
                    .build();
            rideService.add(ride, images);
    }

    @DeleteMapping("/rides/{rideId}")
    public void deleteRide(
            @PathVariable Long rideId, Principal principal) {
        rideService.delete(rideId, principal.getName());
    }
    @GetMapping(value = "/rides/reservations/{rideId}")
    public List<Reservation> listReservations(@PathVariable Long rideId) {
        return reservationService.listByRide(rideId);
    }

}
