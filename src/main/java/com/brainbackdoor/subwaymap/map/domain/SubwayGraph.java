package com.brainbackdoor.subwaymap.map.domain;

import com.brainbackdoor.subwaymap.line.domain.Line;
import com.brainbackdoor.subwaymap.line.domain.Section;
import com.brainbackdoor.subwaymap.station.domain.Station;
import java.util.List;
import org.jgrapht.graph.WeightedMultigraph;

public class SubwayGraph extends WeightedMultigraph<Station, SectionEdge> {

    public SubwayGraph(Class<? extends SectionEdge> edgeClass) {
        super(edgeClass);
    }

    public void addVertexWith(List<Line> lines) {
        // 지하철 역(정점)을 등록
        lines.stream()
            .flatMap(it -> it.getStations().stream())
            .distinct()
            .forEach(this::addVertex);
    }

    public void addEdge(List<Line> lines) {
        // 지하철 역의 연결 정보(간선)을 등록
        for (Line line : lines) {
            line.getSections()
                .forEach(it -> addEdge(it, line));
        }
    }

    private void addEdge(Section section, Line line) {
        SectionEdge sectionEdge = new SectionEdge(section, line.getId());
        addEdge(section.getUpStation(), section.getDownStation(), sectionEdge);
        setEdgeWeight(sectionEdge, section.getDistance());
    }
}
