package com.woowacourse.teatime.teatime.fixture;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.CrewSaveRequest;

public class DtoFixture {

    public static CoachSaveRequest COACH_BROWN_SAVE_REQUEST
            = new CoachSaveRequest("brown", "brown@email.com", "i am legend", "image");
    public static CoachSaveRequest COACH_JUNE_SAVE_REQUEST
            = new CoachSaveRequest("june",  "june@email.com", "i am legend", "image");

    public static CrewSaveRequest CREW_SAVE_REQUEST
            = new CrewSaveRequest("maru", "maru@email.com", "image");
}
