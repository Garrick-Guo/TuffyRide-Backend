package com.cs362.tuffyride.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ride_reserved_date")
public class RideReservedDate implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private RideReservedDateKey id;
    @MapsId("ride_id")
    @ManyToOne
    private Ride ride;

    public RideReservedDate() {}

    public RideReservedDate(RideReservedDateKey id, Ride ride) {
        this.id = id;
        this.ride = ride;
    }

    public RideReservedDateKey getId() {
        return id;
    }

    public Ride getRide() {
        return ride;
    }

}
