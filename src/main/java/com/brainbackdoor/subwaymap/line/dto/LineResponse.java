package com.brainbackdoor.subwaymap.line.dto;


import com.brainbackdoor.subwaymap.line.domain.Line;
import com.brainbackdoor.subwaymap.station.dto.StationResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record LineResponse(Long id, String name, String color, List<StationResponse> stations,
                           LocalDateTime createdDate, LocalDateTime modifiedDate) {

    public static LineResponse of(Line line) {
        if (isEmpty(line)) {
            return new LineResponse(line.getId(), line.getName(), line.getColor(), new ArrayList<>(),
                line.getCreatedDate(), line.getModifiedDate());
        }
        return new LineResponse(line.getId(), line.getName(), line.getColor(), assembleStations(line),
            line.getCreatedDate(), line.getModifiedDate());
    }

    private static boolean isEmpty(Line line) {
        return line.getStations().isEmpty();
    }

    private static List<StationResponse> assembleStations(Line line) {
        return line.getStations().stream()
            .map(StationResponse::of)
            .toList();
    }
}
