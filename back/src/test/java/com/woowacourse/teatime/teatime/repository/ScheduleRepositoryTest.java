package com.woowacourse.teatime.teatime.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CoachRepository coachRepository;

    @DisplayName("스케줄 전체 목록을 조회한다.")
    @Test
    void findAll() {
        LocalDateTime now = LocalDateTime.now();
        Coach coach = coachRepository.save(DomainFixture.COACH_JASON);
        scheduleRepository.save(new Schedule(coach, now));

        List<Schedule> schedules = scheduleRepository.findAll();

        assertThat(schedules).hasSize(1);
    }

    @DisplayName("해당 코치와, 년, 월에 해당하는 스케줄 전체 목록을 조회한다.")
    @Test
    void findAllByCoachIdBetween() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime dateTime1 = start.plusDays(1);
        LocalDateTime dateTime2 = start.plusDays(2);
        LocalDateTime end = start.plusDays(3);

        Coach coach = coachRepository.save(DomainFixture.COACH_JASON);
        Schedule schedule1 = new Schedule(coach, dateTime1);
        Schedule schedule2 = new Schedule(coach, dateTime2);
        scheduleRepository.save(schedule1);
        scheduleRepository.save(schedule2);

        List<Schedule> schedules = scheduleRepository.findAllByCoachIdBetween(
                coach.getId(), start, end);

        assertAll(
                () -> assertThat(schedules).hasSize(2),
                () -> assertThat(schedules.get(0)).isEqualTo(schedule1)
        );
    }

//    @DisplayName("해당 코치와, 날짜에 해당하는 하루 스케줄을 모두 삭제한다.")
//    @Test
//    void deleteAllByCoachIdAndLocalDateTimeBetween() {
//        // given
//        LocalDateTime july1_1 = LocalDateTime.of(2022, 7, 1, 1, 0, 0);
//        LocalDateTime july1_2 = LocalDateTime.of(2022, 7, 1, 2, 0, 0);
//
//        Coach coach = coachRepository.save(DomainFixture.COACH_JASON);
//        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, july1_1));
//        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, july1_2));
//        schedule2.reserve();
//        List<String> localDates = Stream.of(july1_1, july1_2)
//                .map(LocalDateTime::toLocalDate)
//                .distinct()
//                .map(String::valueOf)
//                .collect(Collectors.toList());
//
//        // when
//        scheduleRepository.deleteAllReservableByCoachIdBetween(coach.getId(), localDates);
//
//        // then
//        List<Schedule> schedules = scheduleRepository.findAll();
//        assertThat(schedules).hasSize(1);
//    }

    @DisplayName("들어온 코치 스케줄들 중 예약이 되어 있는 날짜가 있는지 확인한다. - 예약된 날짜가 하나 존재하면 true를 반환한다.")
    @Test
    void isExistReservedSchedules_hasOneReservedSchedule() {
        //given
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime dateTime1 = start.plusDays(1);
        LocalDateTime dateTime2 = start.plusDays(2);
        Coach coach = coachRepository.save(DomainFixture.COACH_JASON);
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, dateTime1));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, dateTime2));
        schedule1.reserve();

        //when
        List<LocalDateTime> localDateTimes = List.of(dateTime1, dateTime2);
        boolean actual = scheduleRepository.isExistReservedSchedules(coach.getId(), localDateTimes);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("들어온 코치 스케줄들 중 예약이 되어 있는 날짜가 있는지 확인한다. - 모두 예약된 날짜라면 true를 반환한다.")
    @Test
    void isExistReservedSchedules_hasReservedSchedules() {
        //given
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime dateTime1 = start.plusDays(1);
        LocalDateTime dateTime2 = start.plusDays(2);
        Coach coach = coachRepository.save(DomainFixture.COACH_JASON);
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, dateTime1));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, dateTime2));
        schedule1.reserve();
        schedule2.reserve();

        //when
        List<LocalDateTime> localDateTimes = List.of(dateTime1, dateTime2);
        boolean actual = scheduleRepository.isExistReservedSchedules(coach.getId(), localDateTimes);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("들어온 코치 스케줄들 중 예약이 되어 있는 날짜가 있는지 확인한다. - 예약된 날짜가 존재하지 않으면 false를 반환한다.")
    @Test
    void isExistReservedSchedules_false() {
        //given
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime dateTime1 = start.plusDays(1);
        LocalDateTime dateTime2 = start.plusDays(2);
        Coach coach = coachRepository.save(DomainFixture.COACH_JASON);
        scheduleRepository.save(new Schedule(coach, dateTime1));
        scheduleRepository.save(new Schedule(coach, dateTime2));

        //when
        List<LocalDateTime> localDateTimes = List.of(dateTime1, dateTime2);
        boolean actual = scheduleRepository.isExistReservedSchedules(coach.getId(), localDateTimes);

        //then
        assertThat(actual).isFalse();
    }
}
