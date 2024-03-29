package com.woowacourse.teatime.teatime.repository;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Schedule;
import com.woowacourse.teatime.teatime.fixture.DomainFixture;
import com.woowacourse.teatime.teatime.repository.dto.CoachWithPossible;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CoachRepositoryTest {

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @DisplayName("전체 코치 목록을 조회한다.")
    @Test
    void findAll_size3() {
        coachRepository.save(DomainFixture.COACH_BROWN);

        assertThat(coachRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("전체 코치 목록을 조회한다.")
    @Test
    void findAll_size4() {
        coachRepository.save(DomainFixture.COACH_JASON);
        coachRepository.save(DomainFixture.COACH_BROWN);

        assertThat(coachRepository.findAll().size()).isEqualTo(2);
    }

    @DisplayName("이메일로 크루를 조회한다.")
    @Test
    void findByEmail() {
        coachRepository.save(DomainFixture.COACH_BROWN);

        Optional<Coach> coach = coachRepository.findByEmail(DomainFixture.COACH_BROWN.getEmail());

        assertThat(coach).isNotEmpty();
    }

    @DisplayName("이메일로 크루를 조회한다. - 이메일이 존재하지 않는 경우.")
    @Test
    void findByEmail_notFound() {
        coachRepository.save(DomainFixture.COACH_BROWN);

        Optional<Coach> coach = coachRepository.findByEmail(DomainFixture.COACH_JASON.getEmail());

        assertThat(coach).isEmpty();
    }

    @DisplayName("코치의 등록된 스케줄이 하나도 없을 경우 isPossible이 false이다.")
    @Test
    void findCoaches1() {
        // given
        Coach coach = coachRepository.save(getCoachJason());

        // when
        List<CoachWithPossible> coaches = coachRepository.findCoaches();

        // then
        assertAll(
                () -> assertThat(coaches).hasSize(1),
                () -> assertThat(coaches.get(0).getId()).isEqualTo(coach.getId()),
                () -> assertThat(coaches.get(0).getName()).isEqualTo(coach.getName()),
                () -> assertThat(coaches.get(0).getDescription()).isEqualTo(coach.getDescription()),
                () -> assertThat(coaches.get(0).getImage()).isEqualTo(coach.getImage()),
                () -> assertThat(coaches.get(0).getIsPossible()).isFalse()
        );
    }

    @DisplayName("코치의 스케줄이 예약된 경우 isPossible이 false이다.")
    @Test
    void findCoaches2() {
        // given
        Coach coach = coachRepository.save(getCoachJason());
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        Schedule schedule = scheduleRepository.save(new Schedule(coach, tomorrow));
        schedule.reserve();

        // when
        List<CoachWithPossible> coaches = coachRepository.findCoaches();

        // then
        assertAll(
                () -> assertThat(coaches).hasSize(1),
                () -> assertThat(coaches.get(0).getName()).isEqualTo(coach.getName()),
                () -> assertThat(coaches.get(0).getIsPossible()).isFalse()
        );
    }

    @DisplayName("코치의 등록된 스케줄 중 예약가능한 일정이 하나라도 있다면 isPossible이 true이다.")
    @Test
    void findCoaches3() {
        // given
        Coach coach = coachRepository.save(getCoachJason());
        LocalDateTime tomorrow = LocalDateTime.now().plusMonths(1);
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, tomorrow));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, tomorrow.plusHours(1)));
        schedule1.reserve();

        // when
        List<CoachWithPossible> coaches = coachRepository.findCoaches();

        // then
        assertAll(
                () -> assertThat(coaches).hasSize(1),
                () -> assertThat(coaches.get(0).getName()).isEqualTo(coach.getName()),
                () -> assertThat(coaches.get(0).getIsPossible()).isTrue()
        );
    }

    @DisplayName("코치의 등록된 스케줄 중 예약가능하지만 지난 일정만 존재한다면 isPossible이 false이다.")
    @Test
    void findCoaches4() {
        // given
        Coach coach = coachRepository.save(getCoachJason());
        LocalDateTime before = LocalDateTime.now().minusMinutes(1);
        Schedule schedule = scheduleRepository.save(new Schedule(coach, before));

        // when
        List<CoachWithPossible> coaches = coachRepository.findCoaches();

        // then
        assertAll(
                () -> assertThat(coaches).hasSize(1),
                () -> assertThat(coaches.get(0).getName()).isEqualTo(coach.getName()),
                () -> assertThat(coaches.get(0).getIsPossible()).isFalse()
        );
    }

    @DisplayName("코치의 등록된 스케줄 중 지난일정과 예약이 완료된 일정만 존재한다면 isPossible이 false이다.")
    @Test
    void findCoaches5() {
        // given
        Coach coach = coachRepository.save(getCoachJason());
        LocalDateTime before1 = LocalDateTime.now().minusMinutes(1);
        LocalDateTime before2 = LocalDateTime.now().minusMinutes(1);
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        Schedule schedule1 = scheduleRepository.save(new Schedule(coach, before1));
        Schedule schedule2 = scheduleRepository.save(new Schedule(coach, before2));
        Schedule schedule3 = scheduleRepository.save(new Schedule(coach, tomorrow));
        schedule3.reserve();

        // when
        List<CoachWithPossible> coaches = coachRepository.findCoaches();

        // then
        assertAll(
                () -> assertThat(coaches).hasSize(1),
                () -> assertThat(coaches.get(0).getName()).isEqualTo(coach.getName()),
                () -> assertThat(coaches.get(0).getIsPossible()).isFalse()
        );
    }
}
