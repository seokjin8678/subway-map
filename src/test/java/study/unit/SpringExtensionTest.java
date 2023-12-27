package study.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.brainbackdoor.subwaymap.line.application.LineService;
import com.brainbackdoor.subwaymap.line.domain.Line;
import com.brainbackdoor.subwaymap.line.domain.LineRepository;
import com.brainbackdoor.subwaymap.line.dto.LineResponse;
import com.brainbackdoor.subwaymap.station.application.StationService;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;

@DisplayName("단위 테스트 - SpringExtension을 활용한 가짜 협력 객체 사용")
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
class SpringExtensionTest {

    @MockBean
    private LineRepository lineRepository;
    @MockBean
    private StationService stationService;

    @Test
    void findAllLines() {
        // given
        when(lineRepository.findAll()).thenReturn(Lists.newArrayList(new Line()));
        LineService lineService = new LineService(lineRepository, stationService);

        // when
        List<LineResponse> responses = lineService.findLineResponses();

        // then
        assertThat(responses).hasSize(1);
    }
}
