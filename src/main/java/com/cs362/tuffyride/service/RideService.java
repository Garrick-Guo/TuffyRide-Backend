package com.cs362.tuffyride.service;

import com.cs362.tuffyride.exception.RideNotExistException;
import com.cs362.tuffyride.model.Ride;
import com.cs362.tuffyride.model.RideImage;
import com.cs362.tuffyride.model.User;
import com.cs362.tuffyride.repository.RideRepository;
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

    @Autowired
    public RideService(RideRepository rideRepository, ImageStorageService imageStorageService) {
        this.rideRepository = rideRepository;
        this.imageStorageService = imageStorageService;
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
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(Long rideId, String username) throws RideNotExistException {
        Ride ride = rideRepository.findByIdAndDriver(rideId, new User.Builder().setUsername(username).build());
        if (ride == null) {
            throw new RideNotExistException("Stay doesn't exist");
        }
        rideRepository.deleteById(rideId);
    }
}

