package com.cs362.tuffyride.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ride_image")

public class RideImage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String url;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    @JsonIgnore
    private Ride ride;

    public RideImage() {}

    public RideImage(String url, Ride ride) {
        this.url = url;
        this.ride = ride;
    }

    public String getUrl() {
        return url;
    }

    public RideImage setUrl(String url) {
        this.url = url;
        return this;
    }
    public Ride getRide() {
        return ride;
    }

    public RideImage setStay(Ride ride) {
        this.ride = ride;
        return this;
    }


}
