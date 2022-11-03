package com.woowacourse.teatime.support;

import com.woowacourse.teatime.teatime.repository.CanceledReservationRepository;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.teatime.repository.SheetRepository;
import com.woowacourse.teatime.teatime.repository.jdbc.QuestionDao;
import com.woowacourse.teatime.teatime.support.RepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

@RepositoryTest
public class RepositoryTestSupporter {

    @Autowired
    protected CanceledReservationRepository canceledReservationRepository;

    @Autowired
    protected CrewRepository crewRepository;

    @Autowired
    protected CoachRepository coachRepository;

    @Autowired
    protected ScheduleRepository scheduleRepository;

    @Autowired
    protected ReservationRepository reservationRepository;

    @Autowired
    protected SheetRepository sheetRepository;

    @Autowired
    protected QuestionDao questionDao;
}
