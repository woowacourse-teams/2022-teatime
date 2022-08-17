package com.woowacourse.teatime.auth.support.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRoleDto {

    private final Long id;
    private final String role;
}
