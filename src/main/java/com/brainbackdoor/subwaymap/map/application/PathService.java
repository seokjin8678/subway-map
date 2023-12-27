package com.brainbackdoor.subwaymap.map.application;

import com.brainbackdoor.subwaymap.line.domain.Line;
import com.brainbackdoor.subwaymap.map.domain.SectionEdge;
import com.brainbackdoor.subwaymap.map.domain.SubwayGraph;
import com.brainbackdoor.subwaymap.map.domain.SubwayPath;
import com.brainbackdoor.subwaymap.station.domain.Station;
import java.util.ArrayList;
import java.util.List;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.springframework.stereotype.Service;

@Service
public class PathService {

    public SubwayPath findPath(List<Line> lines, Station source, Station target) {
        SubwayGraph graph = new SubwayGraph(SectionEdge.class);
        graph.addVertexWith(lines);
        graph.addEdge(lines);

        // 다익스트라 최단 경로 찾기
        DijkstraShortestPath<Station, SectionEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<Station, SectionEdge> path = dijkstraShortestPath.getPath(source, target);

        return convertSubwayPath(path);
    }

    private SubwayPath convertSubwayPath(GraphPath<Station, SectionEdge> graphPath) {
        List<SectionEdge> edges = new ArrayList<>(graphPath.getEdgeList());
        List<Station> stations = graphPath.getVertexList();
        return new SubwayPath(edges, stations);
    }
}
