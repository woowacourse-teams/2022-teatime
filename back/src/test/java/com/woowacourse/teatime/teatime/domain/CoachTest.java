package com.woowacourse.teatime.teatime.domain;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.teatime.exception.InvalidProfileInfoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CoachTest {

    @DisplayName("자신의 이름을 수정한다.")
    @Test
    void modifyName() {
        //given
        Coach coach = getCoachJason();

        //when
        String expected = "뉴이슨";
        coach.modifyProfile(expected);

        //then
        assertThat(coach.getName()).isEqualTo(expected);
    }

    @DisplayName("자신의 이름을 수정한다. - 앞, 뒤 공백 제거")
    @Test
    void modifyName_trim() {
        //given
        Coach coach = getCoachJason();

        //when
        String expected = " 뉴이슨   ";
        coach.modifyProfile(expected);

        //then
        assertThat(coach.getName()).isEqualTo(expected.trim());
    }

    @DisplayName("자신의 이름을 수정한다. - 빈 값이면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void modifyName_invalidProfile(String name) {
        //given
        Coach coach = getCoachJason();

        //when, then
        assertThatThrownBy(() -> coach.modifyProfile(name))
                .isInstanceOf(InvalidProfileInfoException.class);
    }
}
