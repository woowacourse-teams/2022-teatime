package study;

import static com.woowacourse.teatime.teatime.service.AlarmTitle.REMIND_CREW;
import static org.assertj.core.api.Assertions.assertThat;

import com.slack.api.methods.impl.MethodsClientImpl;
import com.slack.api.util.http.SlackHttpClient;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.infrastructure.Alarm;
import com.woowacourse.teatime.teatime.infrastructure.SlackAlarm;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class StringSplitTest {

    private final Alarm alarm = new SlackAlarm(
            new MethodsClientImpl(new SlackHttpClient(), "xoxb-3853132979991-3902030818182-vKFtd4z1zauT6KxCrd2eahR9"));

    @DisplayName("이메일의 도메인을 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {"brown@woowahan.com", "bro.wn@woowahan.com"})
    void splitEmail(String email) {
        String result = StringUtils.substringBetween(email, "@", ".");

        assertThat(result).isEqualTo("woowahan");
    }

    @DisplayName("이메일의 도메인을 가져온다.")
    @Test
    void alarm() {
        Crew crew = new Crew("U03S7DCHNM6", "야호", "email", "image");
        Coach coach = new Coach("U03TRCESYA2", "티타임", "굿", "굿");
        String message = getMessage(crew.getName(), coach.getName(), LocalDateTime.now());
        alarm.sendMessages(List.of(crew.getSlackId()),REMIND_CREW.getTitle(), message);
    }

    private String getMessage(String crewName, String coachName, LocalDateTime dateTime) {
        return String.join("\r\n",
                "크루: " + crewName,
                "코치: " + coachName,
                "티타임: " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(dateTime)
        );
    }
}
