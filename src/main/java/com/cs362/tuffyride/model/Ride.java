package com.cs362.tuffyride.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ride")
@JsonDeserialize(builder = Ride.Builder.class)
public class Ride implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String pickUp;
    private String dropOff;
    @JsonProperty("guest_number")
    private int guestNumber;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User driver;

    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<RideImage> images;
    public Ride() {}

    private Ride(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.pickUp = builder.pickUp;
        this.dropOff = builder.dropOff;
        this.guestNumber = builder.guestNumber;
        this.driver = builder.driver;
        this.images = builder.images;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPickUp() {
        return pickUp;
    }
    public String getDropOff() {
        return dropOff;
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public User getDriver() {
        return driver;
    }

    public List<RideImage> getImages() {
        return images;
    }

    public Ride setImages(List<RideImage> images) {
        this.images = images;
        return this;
    }


    public static class Builder {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("pick_up")
        private String pickUp;

        @JsonProperty("drop_off")
        private String dropOff;

        @JsonProperty("guest_number")
        private int guestNumber;

        @JsonProperty("driver")
        private User driver;

        @JsonProperty("images")
        private List<RideImage> images;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setPickUp(String pickUp) {
            this.pickUp = pickUp;
            return this;
        }
        public Builder setDropOff(String dropOff) {
            this.dropOff = dropOff;
            return this;
        }
        public Builder setGuestNumber(int guestNumber) {
            this.guestNumber = guestNumber;
            return this;
        }

        public Builder setDriver(User driver) {
            this.driver = driver;
            return this;
        }

        public Builder setImages(List<RideImage> images) {
            this.images = images;
            return this;
        }


        public Ride build() {
            return new Ride(this);
        }
    }

}
