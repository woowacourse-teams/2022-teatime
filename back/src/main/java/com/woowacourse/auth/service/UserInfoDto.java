package com.woowacourse.auth.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserInfoDto {

    private final String name;
    private final String email;
    private final String image;
}
