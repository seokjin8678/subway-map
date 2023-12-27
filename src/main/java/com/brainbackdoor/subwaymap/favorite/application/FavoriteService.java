package com.brainbackdoor.subwaymap.favorite.application;

import com.brainbackdoor.subwaymap.auth.domain.LoginMember;
import com.brainbackdoor.subwaymap.favorite.domain.Favorite;
import com.brainbackdoor.subwaymap.favorite.domain.FavoriteRepository;
import com.brainbackdoor.subwaymap.favorite.domain.HasNotPermissionException;
import com.brainbackdoor.subwaymap.favorite.dto.FavoriteRequest;
import com.brainbackdoor.subwaymap.favorite.dto.FavoriteResponse;
import com.brainbackdoor.subwaymap.station.domain.Station;
import com.brainbackdoor.subwaymap.station.domain.StationRepository;
import com.brainbackdoor.subwaymap.station.dto.StationResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final StationRepository stationRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, StationRepository stationRepository) {
        this.favoriteRepository = favoriteRepository;
        this.stationRepository = stationRepository;
    }

    public void createFavorite(LoginMember loginMember, FavoriteRequest request) {
        Favorite favorite = new Favorite(loginMember.id(), request.source(), request.target());
        favoriteRepository.save(favorite);
    }

    public List<FavoriteResponse> findFavorites(LoginMember loginMember) {
        List<Favorite> favorites = favoriteRepository.findByMemberId(loginMember.id());
        Map<Long, Station> stations = extractStations(favorites);

        return favorites.stream()
            .map(it -> FavoriteResponse.of(
                it,
                StationResponse.of(stations.get(it.getSourceStationId())),
                StationResponse.of(stations.get(it.getTargetStationId()))))
            .toList();
    }

    public void deleteFavorite(LoginMember loginMember, Long id) {
        Favorite favorite = favoriteRepository.findById(id).orElseThrow(RuntimeException::new);
        if (!favorite.isCreatedBy(loginMember.id())) {
            throw new HasNotPermissionException(loginMember.id() + "는 삭제할 권한이 없습니다.");
        }
        favoriteRepository.deleteById(id);
    }

    private Map<Long, Station> extractStations(List<Favorite> favorites) {
        Set<Long> stationIds = extractStationIds(favorites);
        return stationRepository.findAllById(stationIds).stream()
            .collect(Collectors.toMap(Station::getId, Function.identity()));
    }

    private Set<Long> extractStationIds(List<Favorite> favorites) {
        Set<Long> stationIds = new HashSet<>();
        for (Favorite favorite : favorites) {
            stationIds.add(favorite.getSourceStationId());
            stationIds.add(favorite.getTargetStationId());
        }
        return stationIds;
    }
}
