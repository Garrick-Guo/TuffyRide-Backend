package com.cs362.tuffyride.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class RideReservedDateKey implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long ride_id;
    private LocalDate date;

    public RideReservedDateKey() {
    }

    public RideReservedDateKey(Long ride_id, LocalDate date) {
        this.ride_id = ride_id;
        this.date = date;
    }

    public Long getRide_id() {
        return ride_id;
    }

    public RideReservedDateKey setRide_id(Long ride_id) {
        this.ride_id = ride_id;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public RideReservedDateKey setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RideReservedDateKey that = (RideReservedDateKey) o;
        return ride_id.equals(that.ride_id) && date.equals(that.date);
    }
    @Override
    public int hashCode() {
        return Objects.hash(ride_id, date);
    }

}
