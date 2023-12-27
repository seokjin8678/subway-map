package com.brainbackdoor.subwaymap.line.dto;


public record LineRequest(String name, String color, Long upStationId, Long downStationId, int distance) {

}
