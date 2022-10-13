package com.woowacourse.teatime.teatime.fixture;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Question;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DomainFixture {

    public static final Coach COACH_BROWN = new Coach("브라운", "브라운", "brown@email.com", "i am legend", "image");
    public static final Coach COACH_JASON = new Coach("제이슨", "제이슨", "jason@email.com", "i am legend", "image");

    public static final LocalDate LOCAL_DATE = LocalDate.now();
    public static final LocalTime LOCAL_TIME = LocalTime.MIN;
    public static final LocalDateTime DATE_TIME = LocalDateTime.of(LOCAL_DATE, LOCAL_TIME);

    public static final Crew CREW1 = new Crew("마루", "마루", "maru@email.com", "image");

    public static Question getQuestion1(Coach coach) {
        return new Question(coach, 1, "이름이 뭔가요?", true);
    }

    public static Question getQuestion2(Coach coach) {
        return new Question(coach, 2, "별자리가 뭔가요?", true);
    }

    public static Question getQuestion3(Coach coach) {
        return new Question(coach, 3, "mbti는 뭔가요?", true);
    }

    public static Question getQuestionIsRequiredFalse(Coach coach) {
        return new Question(coach, 4, "부모님 직업은 뭔가요?", false);
    }

    public static Coach getCoachJason() {
        return new Coach("제이슨", "제이슨", "jason@email.com", "i am legend", "image");
    }

    public static Crew getCrew() {
        return new Crew("마루", "마루", "maru@email.com", "image");
    }
}
