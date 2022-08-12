package com.woowacourse.teatime.teatime.fixture;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.CrewSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateDto;

public class DtoFixture {

    public static CoachSaveRequest COACH_BROWN_SAVE_REQUEST
            = new CoachSaveRequest("brown", "brown@email.com", "i am legend", "image");
    public static CoachSaveRequest COACH_JUNE_SAVE_REQUEST
            = new CoachSaveRequest("june", "june@email.com", "i am legend", "image");

    public static CrewSaveRequest CREW_SAVE_REQUEST
            = new CrewSaveRequest("maru", "maru@email.com", "image");

    public static SheetAnswerUpdateDto SHEET_ANSWER_UPDATE_REQUEST_ONE
            = new SheetAnswerUpdateDto(1, "당신의 혈액형은?", "B형");
    public static SheetAnswerUpdateDto SHEET_ANSWER_UPDATE_REQUEST_TWO
            = new SheetAnswerUpdateDto(2, "당신의 별자리는?", "물고기 자리");
    public static SheetAnswerUpdateDto SHEET_ANSWER_UPDATE_REQUEST_THREE
            = new SheetAnswerUpdateDto(3, "mbti는 뭔가요?", "entp");
}
