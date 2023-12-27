package com.brainbackdoor.subwaymap.station.dto;


import com.brainbackdoor.subwaymap.station.domain.Station;

public record StationRequest(String name) {

    public Station toStation() {
        return new Station(name);
    }
}
