package com.woowacourse.teatime.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {

    @DisplayName("id를 지정하지 않으면 null로 초기화된다.")
    @Test
    void createNewCrew_withoutId() {
        Crew crew = new Crew("야호", "yaho@email.com", "image");

        assertThat(crew.getId()).isNull();
    }

}
