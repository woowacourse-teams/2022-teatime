package com.woowacourse.teatime.teatime.infrastructure;

import java.util.List;

public interface Alarm {

    void sendMessage(String userId, String title, String message, String barColor);

    void sendGroupMessage(List<String> userId, String title, String message, String barColor);
}
