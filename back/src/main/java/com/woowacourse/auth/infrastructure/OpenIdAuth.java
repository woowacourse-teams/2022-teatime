package com.woowacourse.auth.infrastructure;

import com.woowacourse.auth.service.UserInfoDto;

public interface OpenIdAuth {

    String getAccessToken(String code);

    UserInfoDto getUserInfo(String accessToken);
}
