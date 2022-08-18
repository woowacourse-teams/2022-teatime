package com.woowacourse.teatime.teatime.fixture;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.CrewSaveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateDto;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetQuestionUpdateDto;

public class DtoFixture {

    public static final CoachSaveRequest COACH_BROWN_SAVE_REQUEST
            = new CoachSaveRequest("brown", "brown@email.com", "i am legend", "image");
    public static final CoachSaveRequest COACH_JUNE_SAVE_REQUEST
            = new CoachSaveRequest("june", "june@email.com", "i am legend", "image");
    public static final CoachSaveRequest COACH_WOOWAHAN_SAVE_REQUEST
            = new CoachSaveRequest("john", "john@woowahan.com", "i am legend", "image");

    public static final CrewSaveRequest CREW_SAVE_REQUEST
            = new CrewSaveRequest("maru", "maru@email.com", "image");

    public static final SheetAnswerUpdateDto SHEET_ANSWER_UPDATE_REQUEST_ONE
            = new SheetAnswerUpdateDto(1, "당신의 혈액형은?", "B형");
    public static final SheetAnswerUpdateDto SHEET_ANSWER_UPDATE_REQUEST_TWO
            = new SheetAnswerUpdateDto(2, "당신의 별자리는?", "물고기 자리");
    public static final SheetAnswerUpdateDto SHEET_ANSWER_UPDATE_REQUEST_THREE
            = new SheetAnswerUpdateDto(3, "mbti는 뭔가요?", "entp");

    public static final String QUESTION_CONTENT_1 = "이번 면담을 통해 논의하고 싶은 내용";
    public static final String QUESTION_CONTENT_2 = "최근에 자신이 긍정적으로 보는 시도와 변화";
    public static final String QUESTION_CONTENT_3 = "이번 면담을 통해 생기기를 원하는 변화";

    public static final SheetQuestionUpdateDto DEFAULT_SHEET_QUESTION_1
            = new SheetQuestionUpdateDto(1, QUESTION_CONTENT_1);
    public static final SheetQuestionUpdateDto DEFAULT_SHEET_QUESTION_2
            = new SheetQuestionUpdateDto(2, QUESTION_CONTENT_2);
    public static final SheetQuestionUpdateDto DEFAULT_SHEET_QUESTION_3
            = new SheetQuestionUpdateDto(3, QUESTION_CONTENT_3);

    public static final String CUSTOM_QUESTION_CONTENT_1 = "첫번째 질문을 수정하였습니다:)";
    public static final String CUSTOM_QUESTION_CONTENT_2 = "두번째 질문을 수정하였습니다:)";
    public static final String CUSTOM_QUESTION_CONTENT_3 = "세번째 질문을 수정하였습니다:)";

    public static final SheetQuestionUpdateDto CUSTOM_SHEET_QUESTION_1
            = new SheetQuestionUpdateDto(1, CUSTOM_QUESTION_CONTENT_1);
    public static final SheetQuestionUpdateDto CUSTOM_SHEET_QUESTION_2
            = new SheetQuestionUpdateDto(2, CUSTOM_QUESTION_CONTENT_2);
    public static final SheetQuestionUpdateDto CUSTOM_SHEET_QUESTION_3
            = new SheetQuestionUpdateDto(3, CUSTOM_QUESTION_CONTENT_3);
}
