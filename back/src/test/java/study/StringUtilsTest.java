package study;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @DisplayName("isBlank 앞의 값이 null이면 예외가 발생한다.")
    @Test
    void isBlank() {
        String value = null;
        assertThatThrownBy(value::isBlank)
                .isInstanceOf(NullPointerException.class);
    }
}
