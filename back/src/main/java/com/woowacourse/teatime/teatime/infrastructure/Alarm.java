package com.woowacourse.teatime.teatime.infrastructure;

import java.util.List;

public interface Alarm {

    void sendMessage(String userId, String title, String message);

    void sendMessages(List<String> userId, String title, String message);
}
