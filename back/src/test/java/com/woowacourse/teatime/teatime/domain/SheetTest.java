package com.woowacourse.teatime.teatime.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SheetTest {

    @DisplayName("질문번호가 일치하면 답변을 수정한다.")
    @Test
    void modifyAnswer_correctNumber() {
        Schedule schedule = new Schedule(DomainFixture.COACH_BROWN, DomainFixture.DATE_TIME);
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW1);
        Sheet sheet = new Sheet(reservation, 1, "이름이 뭔가요?");

        String expected = "야호입니다.";
        sheet.modifyAnswer(1, expected);

        assertThat(sheet.getAnswerContent()).isEqualTo(expected);
    }

    @DisplayName("질문번호가 일치하지 않으면 답변을 수정하지 않는다.")
    @Test
    void modifyAnswer_incorrectNumber() {
        Schedule schedule = new Schedule(DomainFixture.COACH_BROWN, DomainFixture.DATE_TIME);
        Reservation reservation = new Reservation(schedule, DomainFixture.CREW1);
        Sheet sheet = new Sheet(reservation, 1, "이름이 뭔가요?");

        String expected = "야호입니다.";
        sheet.modifyAnswer(2, expected);

        assertThat(sheet.getAnswerContent()).isNotEqualTo(expected);
    }
}
