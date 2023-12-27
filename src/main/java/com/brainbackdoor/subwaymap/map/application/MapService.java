package com.brainbackdoor.subwaymap.map.application;

import com.brainbackdoor.subwaymap.line.application.LineService;
import com.brainbackdoor.subwaymap.line.domain.Line;
import com.brainbackdoor.subwaymap.map.domain.SubwayPath;
import com.brainbackdoor.subwaymap.map.dto.PathResponse;
import com.brainbackdoor.subwaymap.map.dto.PathResponseAssembler;
import com.brainbackdoor.subwaymap.station.application.StationService;
import com.brainbackdoor.subwaymap.station.domain.Station;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MapService {

    private final LineService lineService;
    private final StationService stationService;
    private final PathService pathService;

    public MapService(LineService lineService, StationService stationService, PathService pathService) {
        this.lineService = lineService;
        this.stationService = stationService;
        this.pathService = pathService;
    }

    public PathResponse findPath(Long source, Long target) {
        List<Line> lines = lineService.findLines();
        Station sourceStation = stationService.findById(source);
        Station targetStation = stationService.findById(target);
        SubwayPath subwayPath = pathService.findPath(lines, sourceStation, targetStation);

        return PathResponseAssembler.assemble(subwayPath);
    }
}
