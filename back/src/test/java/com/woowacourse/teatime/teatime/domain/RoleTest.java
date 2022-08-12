package com.woowacourse.teatime.teatime.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.teatime.exception.NotFoundRoleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RoleTest {

    @DisplayName("코치를 찾을 수 있다.")
    @Test
    void search_coach() {
        String role = "COACH";

        assertThat(Role.search(role)).isEqualTo(Role.COACH);
    }

    @DisplayName("크루를 찾을 수 있다.")
    @Test
    void search_crew() {
        String role = "CREW";

        assertThat(Role.search(role)).isEqualTo(Role.CREW);
    }

    @DisplayName("크루, 코치가 아닌 경우 에러가 발생한다.")
    @Test
    void search_NotFoundRoleException() {
        String role = "DOCTOR";

        assertThatThrownBy(() -> Role.search(role))
                .isInstanceOf(NotFoundRoleException.class);
    }
}
