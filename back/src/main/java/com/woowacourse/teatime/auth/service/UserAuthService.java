package com.woowacourse.teatime.auth.service;

import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import com.woowacourse.teatime.auth.repository.UserAuthInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAuthService {

    private final UserAuthInfoRepository userAuthInfoRepository;

    public void save(UserAuthInfo userAuthInfo) {
        userAuthInfoRepository.save(userAuthInfo);
    }
}
