package com.brainbackdoor.subwaymap.map.domain;

import com.brainbackdoor.subwaymap.line.domain.Section;
import org.jgrapht.graph.DefaultWeightedEdge;

public class SectionEdge extends DefaultWeightedEdge {

    private final Section section;
    private final Long lineId;

    public SectionEdge(Section section, Long lineId) {
        this.section = section;
        this.lineId = lineId;
    }

    public Section getSection() {
        return section;
    }

    public Long getLineId() {
        return lineId;
    }

    @Override
    protected Object getSource() {
        return this.section.getUpStation();
    }

    @Override
    protected Object getTarget() {
        return this.section.getDownStation();
    }

    @Override
    protected double getWeight() {
        return this.section.getDistance();
    }
}
