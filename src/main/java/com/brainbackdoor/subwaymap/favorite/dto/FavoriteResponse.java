package com.brainbackdoor.subwaymap.favorite.dto;


import com.brainbackdoor.subwaymap.favorite.domain.Favorite;
import com.brainbackdoor.subwaymap.station.dto.StationResponse;

public record FavoriteResponse(Long id, StationResponse source, StationResponse target) {

    public static FavoriteResponse of(Favorite favorite, StationResponse source, StationResponse target) {
        return new FavoriteResponse(favorite.getId(), source, target);
    }
}
