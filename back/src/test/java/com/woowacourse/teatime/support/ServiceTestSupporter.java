package com.woowacourse.teatime.support;

import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.QuestionRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.teatime.service.AlarmService;
import com.woowacourse.teatime.teatime.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ServiceTestSupporter {

    @Autowired
    protected ReservationService reservationService;

    @Autowired
    protected CoachRepository coachRepository;

    @Autowired
    protected CrewRepository crewRepository;

    @Autowired
    protected ScheduleRepository scheduleRepository;

    @Autowired
    protected ReservationRepository reservationRepository;

    @Autowired
    protected QuestionRepository questionRepository;

    @MockBean
    protected AlarmService alarmService;
}
