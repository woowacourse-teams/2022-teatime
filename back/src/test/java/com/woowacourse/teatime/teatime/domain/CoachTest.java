package com.woowacourse.teatime.teatime.domain;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        String expectedName = "뉴이슨";
        String expectedDescription = "안녕하세요 뉴이슨입니다.";
        coach.modifyProfile(expectedName, expectedDescription);

        //then
        assertAll(
                () -> assertThat(coach.getName()).isEqualTo(expectedName),
                () -> assertThat(coach.getDescription()).isEqualTo(expectedDescription)
        );
    }

    @DisplayName("자신의 이름을 수정한다. - 앞, 뒤 공백 제거")
    @Test
    void modifyName_trim() {
        //given
        Coach coach = getCoachJason();

        //when
        String expectedName = "   뉴이슨   ";
        String expectedDescription = "   안녕하세요 뉴이슨입니다.   ";
        coach.modifyProfile(expectedName, expectedDescription);

        //then
        assertAll(
                () -> assertThat(coach.getName()).isEqualTo(expectedName.trim()),
                () -> assertThat(coach.getDescription()).isEqualTo(expectedDescription.trim())
        );
    }

    @DisplayName("자신의 이름을 수정한다. - 이름이 빈 값이면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void modifyName_invalidProfile(String name) {
        //given
        Coach coach = getCoachJason();

        //when, then
        assertThatThrownBy(() -> coach.modifyProfile(name, "안녕하세요"))
                .isInstanceOf(InvalidProfileInfoException.class);
    }
}
