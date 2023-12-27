package com.brainbackdoor.subwaymap.map.domain;


import com.brainbackdoor.subwaymap.station.domain.Station;
import java.util.List;

public record SubwayPath(List<SectionEdge> sectionEdges, List<Station> stations) {

    public int calculateDistance() {
        return sectionEdges.stream()
            .mapToInt(it -> it.getSection().getDistance())
            .sum();
    }
}
