package study;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class StringSplitTest {

    @DisplayName("이메일의 도메인을 가져온다.")
    @ParameterizedTest
    @ValueSource(strings = {"brown@woowahan.com", "bro.wn@woowahan.com"})
    void splitEmail(String email) {
        String result = StringUtils.substringBetween(email, "@", ".");

        assertThat(result).isEqualTo("woowahan");
    }
}
