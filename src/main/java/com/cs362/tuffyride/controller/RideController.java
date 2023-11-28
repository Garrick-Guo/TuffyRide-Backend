package com.cs362.tuffyride.controller;

import com.cs362.tuffyride.model.Ride;
import com.cs362.tuffyride.model.User;
import com.cs362.tuffyride.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
public class RideController {
    private RideService rideService;

    @Autowired
    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @GetMapping(value = "/rides")
    public List<Ride> listRides(@RequestParam(name = "driver") String driverName) {
        return rideService.listByUser(driverName);
    }

    @GetMapping(value = "/rides/id")
    public Ride getStay(
            @RequestParam(name = "ride_id") Long rideId,
            @RequestParam(name = "driver") String driverName) {
        return rideService.findByIdAndDriver(rideId, driverName);
    }

    @PostMapping("/rides")
    public void addRide (
        @RequestParam("name") String name,
        @RequestParam("pick_up") String pickUp,
        @RequestParam("drop_off") String dropOff,
        @RequestParam("description") String description,
        @RequestParam("driver") String driver,
        @RequestParam("guest_number") int guestNumber,
        @RequestParam("images") MultipartFile[] images){
            Ride ride = new Ride.Builder().setName(name)
                    .setPickUp(pickUp)
                    .setDropOff(dropOff)
                    .setDescription(description)
                    .setGuestNumber(guestNumber)
                    .setDriver(new User.Builder().setUsername(driver).build())
                    .build();
            rideService.add(ride, images);
    }

    @DeleteMapping("/rides")
    public void deleteStay(
            @RequestParam(name = "ride_id") Long rideId,
            @RequestParam(name = "driver") String driverName) {
        rideService.delete(rideId, driverName);
    }
}
