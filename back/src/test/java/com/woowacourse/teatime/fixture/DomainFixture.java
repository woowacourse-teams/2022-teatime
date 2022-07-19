package com.woowacourse.teatime.fixture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.woowacourse.teatime.domain.Coach;
import com.woowacourse.teatime.domain.Crew;

public class DomainFixture {

    public static final Coach COACH_BROWN = new Coach("브라운", "i am legend", "image");

    public static final LocalDate LOCAL_DATE = LocalDate.of(2022, 7, 18);
    public static final LocalTime LOCAL_TIME = LocalTime.MIN;
    public static final LocalDateTime DATE_TIME = LocalDateTime.of(LOCAL_DATE, LOCAL_TIME);

    public static final Crew CREW = new Crew();
}
