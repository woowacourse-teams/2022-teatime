package study;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class LocalDateTimeTest {

    @DisplayName("올바르지 않은 year, month, day를 입력할 경우 예외를 반환한다.")
    @ParameterizedTest
    @CsvSource({"2022, 13, 1", "2022, 1, 500"})
    void validate(int year, int month, int dayOfMonth) {
        assertThatThrownBy(() -> LocalDateTime.of(year, month, dayOfMonth, 1, 1))
                .isInstanceOf(DateTimeException.class);
    }
}
