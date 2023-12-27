package com.brainbackdoor.subwaymap.station.dto;


import com.brainbackdoor.subwaymap.station.domain.Station;
import java.time.LocalDateTime;

public record StationResponse(Long id, String name, LocalDateTime createdDate, LocalDateTime modifiedDate) {

    public static StationResponse of(Station station) {
        return new StationResponse(
            station.getId(),
            station.getName(),
            station.getCreatedDate(),
            station.getModifiedDate()
        );
    }
}
