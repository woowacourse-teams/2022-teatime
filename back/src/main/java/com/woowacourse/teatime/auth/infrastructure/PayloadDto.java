package com.woowacourse.teatime.auth.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PayloadDto {

    private final String role;
    private final Long id;
}
