package com.brainbackdoor.subwaymap.line.application;

import com.brainbackdoor.subwaymap.line.domain.Line;
import com.brainbackdoor.subwaymap.line.domain.LineRepository;
import com.brainbackdoor.subwaymap.line.dto.LineRequest;
import com.brainbackdoor.subwaymap.line.dto.LineResponse;
import com.brainbackdoor.subwaymap.line.dto.SectionRequest;
import com.brainbackdoor.subwaymap.station.application.StationService;
import com.brainbackdoor.subwaymap.station.domain.Station;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LineService {

    private final LineRepository lineRepository;
    private final StationService stationService;

    public LineService(LineRepository lineRepository, StationService stationService) {
        this.lineRepository = lineRepository;
        this.stationService = stationService;
    }

    public LineResponse saveLine(LineRequest request) {
        Station upStation = stationService.findById(request.upStationId());
        Station downStation = stationService.findById(request.downStationId());
        Line persistLine = lineRepository.save(
            new Line(request.name(), request.color(), upStation, downStation, request.distance()));
        return LineResponse.of(persistLine);
    }

    public List<LineResponse> findLineResponses() {
        List<Line> persistLines = lineRepository.findAll();
        return persistLines.stream()
            .map(LineResponse::of)
            .toList();
    }

    public List<Line> findLines() {
        return lineRepository.findAll();
    }

    public Line findLineById(Long id) {
        return lineRepository.findById(id).orElseThrow(RuntimeException::new);
    }


    public LineResponse findLineResponseById(Long id) {
        Line persistLine = findLineById(id);
        return LineResponse.of(persistLine);
    }

    public void updateLine(Long id, LineRequest lineUpdateRequest) {
        Line persistLine = lineRepository.findById(id).orElseThrow(RuntimeException::new);
        persistLine.update(new Line(lineUpdateRequest.name(), lineUpdateRequest.color()));
    }

    public void deleteLineById(Long id) {
        lineRepository.deleteById(id);
    }

    public void addLineStation(Long lineId, SectionRequest request) {
        Line line = findLineById(lineId);
        Station upStation = stationService.findStationById(request.upStationId());
        Station downStation = stationService.findStationById(request.downStationId());
        line.addLineSection(upStation, downStation, request.distance());
    }

    public void removeLineStation(Long lineId, Long stationId) {
        Line line = findLineById(lineId);
        line.removeStation(stationId);
    }

}
