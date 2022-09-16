package com.woowacourse.teatime.teatime.domain;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCrew;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.teatime.exception.InvalidProfileInfoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CrewTest {

    @DisplayName("id를 지정하지 않으면 null로 초기화된다.")
    @Test
    void createNewCrew_withoutId() {
        Crew crew = new Crew("야호", "야호", "yaho@email.com", "image");

        assertThat(crew.getId()).isNull();
    }

    @DisplayName("자신의 이름을 수정한다.")
    @Test
    void modifyName() {
        //given
        Crew crew = getCrew();

        //when
        String expected = "호두마루";
        crew.modifyName(expected);

        //then
        assertThat(crew.getName()).isEqualTo(expected);
    }

    @DisplayName("자신의 이름을 수정한다. - 앞, 뒤 공백 제거")
    @Test
    void modifyName_trim() {
        //given
        Crew crew = getCrew();

        //when
        String expected = " 호두마루   ";
        crew.modifyName(expected);

        //then
        assertThat(crew.getName()).isEqualTo(expected.trim());
    }

    @DisplayName("자신의 이름을 수정한다. - 빈 값이면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void modifyName_invalidProfile(String name) {
        //given
        Crew crew = getCrew();

        //when, then
        assertThatThrownBy(() -> crew.modifyName(name))
                .isInstanceOf(InvalidProfileInfoException.class);
    }
}
