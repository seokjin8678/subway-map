package com.brainbackdoor.subwaymap.map.dto;


import com.brainbackdoor.subwaymap.station.dto.StationResponse;
import java.util.List;

public record PathResponse(List<StationResponse> stations, int distance) {

}
