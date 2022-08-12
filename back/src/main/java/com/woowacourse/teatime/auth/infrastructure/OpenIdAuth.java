package com.woowacourse.teatime.auth.infrastructure;

import com.woowacourse.teatime.auth.service.UserInfoDto;

public interface OpenIdAuth {

    String getAccessToken(String code);

    UserInfoDto getUserInfo(String accessToken);
}
