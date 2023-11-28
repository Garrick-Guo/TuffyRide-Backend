package com.cs362.tuffyride.repository;

import com.cs362.tuffyride.model.Ride;
import com.cs362.tuffyride.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {
    List<Ride> findByDriver(User user);

    Ride findByIdAndDriver(Long id, User driver);
}
