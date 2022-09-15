package com.woowacourse.teatime.teatime.fixture;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Question;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.Schedule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DomainFixture {

    public static final Coach COACH_BROWN = new Coach("브라운", "브라운", "brown@email.com", "i am legend", "image");
    public static final Coach COACH_JASON = new Coach("제이슨", "제이슨", "jason@email.com", "i am legend", "image");

    public static final LocalDate LOCAL_DATE = LocalDate.now();
    public static final LocalTime LOCAL_TIME = LocalTime.MIN;
    public static final LocalDateTime DATE_TIME = LocalDateTime.of(LOCAL_DATE, LOCAL_TIME);

    public static final Schedule SCHEDULE1 = new Schedule(COACH_BROWN, DATE_TIME);
    public static final Schedule SCHEDULE2 = new Schedule(COACH_BROWN, DATE_TIME.plusDays(1));
    public static final Schedule SCHEDULE3 = new Schedule(COACH_BROWN, DATE_TIME.plusDays(2));

    public static final Crew CREW1 = new Crew("마루", "마루", "maru@email.com", "image");
    public static final Crew CREW2 = new Crew("아키", "아키", "aki@email.com", "image");
    public static final Crew CREW3 = new Crew("야호", "야호", "yaho@email.com", "image");

    public static final Reservation RESERVATION1 = new Reservation(SCHEDULE1, CREW1);
    public static final Reservation RESERVATION2 = new Reservation(SCHEDULE2, CREW2);
    public static final Reservation RESERVATION3 = new Reservation(SCHEDULE3, CREW3);

    public static Question getQuestion1(Coach coach) {
        return new Question(coach, 1, "이름이 뭔가요?");
    }

    public static Question getQuestion2(Coach coach) {
        return new Question(coach, 2, "별자리가 뭔가요?");
    }

    public static Question getQuestion3(Coach coach) {
        return new Question(coach, 3, "mbti는 뭔가요?");
    }

    public static Coach getCoachJason() {
        return new Coach("제이슨", "제이슨", "jason@email.com", "i am legend", "image");
    }

    public static Crew getCrew() {
        return new Crew("마루", "마루", "maru@email.com", "image");
    }
}
