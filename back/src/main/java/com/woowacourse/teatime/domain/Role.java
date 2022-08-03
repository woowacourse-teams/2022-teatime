package com.woowacourse.teatime.domain;

import com.woowacourse.teatime.exception.NotFoundRoleException;
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

    public static boolean isCoach(Role role) {
        return COACH.equals(role);
    }

    public boolean isCoach() {
        return this == COACH;
    }

    public boolean isCrew() {
        return this == CREW;
    }

    public static boolean isCrew(Role role) {
        return CREW.equals(role);
    }
}
