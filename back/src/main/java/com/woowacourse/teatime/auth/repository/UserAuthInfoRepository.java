package com.woowacourse.teatime.auth.repository;

import com.woowacourse.teatime.auth.domain.UserAuthInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserAuthInfoRepository extends CrudRepository<UserAuthInfo, String> {
}
