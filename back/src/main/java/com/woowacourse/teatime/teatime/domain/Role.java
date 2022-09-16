package com.woowacourse.teatime.teatime.domain;

import com.woowacourse.teatime.teatime.exception.NotFoundRoleException;
import java.util.Arrays;

public enum Role {

    COACH,
    CREW;

    public static Role search(String input) {
        return Arrays.stream(Role.values())
                .filter(role -> role.name().equals(input))
                .findAny()
                .orElseThrow(NotFoundRoleException::new);
    }

    public boolean isCoach() {
        return this == COACH;
    }

    public boolean isCrew() {
        return this == CREW;
    }
}
