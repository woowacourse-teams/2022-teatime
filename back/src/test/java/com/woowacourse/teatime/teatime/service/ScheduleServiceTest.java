package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.CREW1;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.DATE_TIME;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCrew;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.teatime.teatime.controller.dto.request.ReservationReserveRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ScheduleFindRequest;
import com.woowacourse.teatime.teatime.controller.dto.request.ScheduleUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.ScheduleDto;
import com.woowacourse.teatime.teatime.controller.dto.response.ScheduleFindResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Crew;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.exception.UnableToUpdateScheduleException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.ScheduleRepository;
import com.woowacourse.teatime.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@TestConstructor(autowireMode = AutowireMode.ALL)
class ScheduleServiceTest {

    private static final LocalDate NOW = LocalDate.now();
    private static final LocalDate LAST_DATE_OF_MONTH = NOW.withDayOfMonth(NOW.lengthOfMonth());
    private static final int YEAR = NOW.getYear();
    private static final int MONTH = NOW.getMonthValue();
    private static final ScheduleFindRequest REQUEST = new ScheduleFindRequest(YEAR, MONTH);

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private CrewRepository crewRepository;

    @DisplayName("코치의 오늘 이후 한달 스케줄 목록을 조회한다.")
    @Test
    void find_past() {
        Coach coach = coachRepository.save(COACH_BROWN);
        scheduleRepository.save(new Schedule(coach, LocalDateTime.now().minusDays(1L)));
        List<ScheduleFindResponse> responses = scheduleService.find(coach.getId(), REQUEST);

        assertThat(responses).hasSize(0);
    }

    @DisplayName("코치의 오늘 이후 한달 스케줄 목록을 조회한다.")
    @Test
    void find_future() {
        Coach coach = coachRepository.save(COACH_BROWN);
        scheduleRepository.save(new Schedule(coach, LocalDateTime.of(NOW, LocalTime.of(23, 59))));
        List<ScheduleFindResponse> responses = scheduleService.find(coach.getId(), REQUEST);

        assertThat(responses).hasSize(1);
    }

    @DisplayName("코치의 스케쥴 목록 조회할 때 스케쥴이 시간 순으로 정렬된다.")
    @Test
    void find_order() {
        //given
        Coach coach = coachRepository.save(getCoachJason());
        Crew crew = crewRepository.save(getCrew());
        LocalDate tomorrow = NOW.plusDays(1);
        Schedule 스케쥴1 = scheduleRepository.save(new Schedule(coach, LocalDateTime.of(tomorrow, LocalTime.of(10, 30))));
        Schedule 스케쥴2 = scheduleRepository.save(new Schedule(coach, LocalDateTime.of(tomorrow, LocalTime.of(11, 0))));
        Schedule 예약될_스케쥴 = scheduleRepository
                .save(new Schedule(coach, LocalDateTime.of(tomorrow, LocalTime.of(12, 0))));
        reservationService.save(crew.getId(), new ReservationReserveRequest(예약될_스케쥴.getId()));

        //when
        ScheduleFindRequest REQUEST = new ScheduleFindRequest(tomorrow.getYear(), tomorrow.getMonthValue());
        List<ScheduleFindResponse> responses = scheduleService.find(coach.getId(), REQUEST);
        ScheduleFindResponse response = responses.get(0);
        List<ScheduleDto> schedules = response.getSchedules();
        List<LocalDateTime> list = schedules.stream()
                .map(ScheduleDto::getDateTime)
                .collect(Collectors.toList());

        //then
        assertThat(list).containsExactly(
                스케쥴1.getLocalDateTime(),
                스케쥴2.getLocalDateTime(),
                예약될_스케쥴.getLocalDateTime()
        );
    }

    @DisplayName("코치 아이디가 존재하지 않는 다면 예외를 발생시킨다,")
    @Test
    void find_NotExistedCoachException() {
        Coach coach = coachRepository.save(COACH_BROWN);
        Schedule schedule = scheduleRepository.save(new Schedule(coach, DATE_TIME));
        Long notExistedId = schedule.getId() + 100L;

        assertThatThrownBy(() -> scheduleService.find(notExistedId, REQUEST))
                .isInstanceOf(NotFoundCoachException.class);
    }

    @DisplayName("코치의 날짜에 해당하는 하루 스케줄을 업데이트한다.")
    @Test
    void update_one() {
        // given
        Coach coach = coachRepository.save(COACH_BROWN);
        LocalDate date = LocalDate.now();

        // when
        ScheduleUpdateRequest updateRequest = new ScheduleUpdateRequest(date,
                List.of(
                        LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(23, 59)),
                        LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(13, 30))
                ));
        scheduleService.update(coach.getId(), List.of(updateRequest));

        // then
        ScheduleFindRequest request = new ScheduleFindRequest(date.getYear(), date.getMonthValue());
        List<ScheduleFindResponse> responses = scheduleService.find(coach.getId(), request);
        final List<ScheduleDto> totalSchedules = responses.stream()
                .map(ScheduleFindResponse::getSchedules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertThat(totalSchedules).hasSize(2);
    }

    @DisplayName("코치의 날짜에 해당하는 스케줄을 일괄 업데이트한다.")
    @Test
    void update_total() {
        // given
        Coach coach = coachRepository.save(COACH_BROWN);
        LocalDate date = LocalDate.now();

        // when
        final LocalTime time1 = LocalTime.of(13, 30);
        final LocalTime time2 = LocalTime.of(23, 59);
        ScheduleUpdateRequest updateRequest = new ScheduleUpdateRequest(date,
                List.of(
                        LocalDateTime.of(LAST_DATE_OF_MONTH, time1),
                        LocalDateTime.of(LAST_DATE_OF_MONTH, time2),
                        LocalDateTime.of(LAST_DATE_OF_MONTH.minusDays(1), time1),
                        LocalDateTime.of(LAST_DATE_OF_MONTH.minusDays(1), time2),
                        LocalDateTime.of(LAST_DATE_OF_MONTH.minusDays(2), time1),
                        LocalDateTime.of(LAST_DATE_OF_MONTH.minusDays(2), time2)
                ));
        scheduleService.update(coach.getId(), List.of(updateRequest));

        // then
        ScheduleFindRequest request = new ScheduleFindRequest(date.getYear(), date.getMonthValue());
        List<ScheduleFindResponse> responses = scheduleService.find(coach.getId(), request);
        final List<ScheduleDto> totalSchedules = responses.stream()
                .map(ScheduleFindResponse::getSchedules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertThat(totalSchedules).hasSize(6);
    }

    @DisplayName("전 날짜가 포함되지 않은 스케쥴들을 일괄 업데이트해도 기존 날짜가 삭제되지 않는다.")
    @Test
    void update_total2() {
        // given
        Coach coach = coachRepository.save(COACH_BROWN);
        LocalDate nextMonthDate = LocalDate.now().plusMonths(1);
        int year = nextMonthDate.plusMonths(1).getYear();
        Month month = nextMonthDate.plusMonths(1).getMonth();
        LocalDateTime day2 = LocalDateTime.of(year, month, 2, 13, 30);
        LocalDateTime day3 = LocalDateTime.of(year, month, 3, 13, 30);
        ScheduleUpdateRequest updateRequest1 = new ScheduleUpdateRequest(nextMonthDate, List.of(day2, day3));
        scheduleService.update(coach.getId(), List.of(updateRequest1));

        // when
        LocalDateTime day1 = LocalDateTime.of(year, month, 1, 13, 30);
        LocalDateTime day8 = LocalDateTime.of(year, month, 8, 13, 30);
        LocalDateTime day9 = LocalDateTime.of(year, month, 9, 13, 30);
        ScheduleUpdateRequest updateRequest2 = new ScheduleUpdateRequest(nextMonthDate, List.of(day1, day8, day9));
        scheduleService.update(coach.getId(), List.of(updateRequest2));

        // then
        ScheduleFindRequest request = new ScheduleFindRequest(year, month.getValue());
        List<ScheduleFindResponse> responses = scheduleService.find(coach.getId(), request);
        final List<ScheduleDto> totalSchedules = responses.stream()
                .map(ScheduleFindResponse::getSchedules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertThat(totalSchedules).hasSize(5);
    }

    @DisplayName("코치가 예약되어 있는 스케줄을 삭제하면 예외를 발생시킨다.")
    @Test
    void update_InCaseOfAlreadyReservedSchedule() {
        Coach coach = coachRepository.save(COACH_BROWN);
        LocalDate date = LocalDate.now();
        Schedule schedule = scheduleRepository.save(new Schedule(coach, Date.findFirstTime(date)));
        Crew crew = crewRepository.save(CREW1);
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(schedule.getId());
        reservationService.save(crew.getId(), reservationReserveRequest);

        ScheduleUpdateRequest scheduleUpdateRequest = new ScheduleUpdateRequest(date,
                List.of(Date.findFirstTime(date)));
        assertThatThrownBy(() -> scheduleService.update(coach.getId(), List.of(scheduleUpdateRequest)))
                .isInstanceOf(UnableToUpdateScheduleException.class);
    }

    @DisplayName("코치의 스케쥴을 업데이트할 때 해당 날짜에 이미 예약이 존재하면 그 예약을 삭제하지 않는다.")
    @Test
    void update_if_reservation_exist() {
        // given
        Coach coach = coachRepository.save(COACH_BROWN);
        LocalDateTime reservedTime = LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(23, 58));
        LocalDateTime notReservedTime = LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(23, 50));

        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, reservedTime));
        scheduleRepository.save(new Schedule(coach, notReservedTime));

        Crew crew = crewRepository.save(CREW1);
        ReservationReserveRequest reservationReserveRequest = new ReservationReserveRequest(schedule1.getId());
        reservationService.save(crew.getId(), reservationReserveRequest);

        // when
        ScheduleUpdateRequest scheduleUpdateRequest
                = new ScheduleUpdateRequest(LAST_DATE_OF_MONTH,
                List.of(reservedTime.minusMinutes(1), reservedTime.minusMinutes(2), reservedTime.minusDays(2)));
        scheduleService.update(coach.getId(), List.of(scheduleUpdateRequest));

        // then
        List<ScheduleFindResponse> responses
                = scheduleService.find(coach.getId(),
                new ScheduleFindRequest(reservedTime.getYear(), reservedTime.getMonthValue()));
        List<ScheduleDto> totalSchedules = responses.stream()
                .map(ScheduleFindResponse::getSchedules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertThat(totalSchedules).hasSize(4);
    }

    @DisplayName("코치의 스케쥴을 수정할 때, 빈 스케쥴이 들어오면 모두 삭제한다.")
    @Test
    void update_allDeleteIfEmptyDates() {
        // given
        Coach coach = coachRepository.save(COACH_BROWN);
        LocalDateTime time1 = LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(13, 30));
        LocalDateTime time2 = LocalDateTime.of(LAST_DATE_OF_MONTH, LocalTime.of(14, 30));

        scheduleRepository.save(new Schedule(coach, time1));
        scheduleRepository.save(new Schedule(coach, time2));

        // when
        ScheduleUpdateRequest scheduleUpdateRequest = new ScheduleUpdateRequest(LAST_DATE_OF_MONTH, List.of());
        scheduleService.update(coach.getId(), List.of(scheduleUpdateRequest));

        // then
        List<ScheduleFindResponse> responses = scheduleService.find(coach.getId(),
                new ScheduleFindRequest(LAST_DATE_OF_MONTH.getYear(), LAST_DATE_OF_MONTH.getMonthValue()));
        List<ScheduleDto> totalSchedules = responses.stream()
                .map(ScheduleFindResponse::getSchedules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        assertThat(totalSchedules).isEmpty();
    }
}
