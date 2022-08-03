package com.woowacourse.teatime.fixture;

import com.woowacourse.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.controller.dto.request.CrewSaveRequest;

public class DtoFixture {

    public static CoachSaveRequest COACH_BROWN_SAVE_REQUEST
            = new CoachSaveRequest("brown", "i am legend", "image");
    public static CoachSaveRequest COACH_JUNE_SAVE_REQUEST
            = new CoachSaveRequest("june", "i am legend", "image");

    public static CrewSaveRequest CREW_SAVE_REQUEST = new CrewSaveRequest("maru", "image");
}
