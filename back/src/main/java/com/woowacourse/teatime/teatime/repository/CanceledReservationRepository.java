package com.woowacourse.teatime.teatime.repository;

import com.woowacourse.teatime.teatime.domain.CanceledReservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CanceledReservationRepository extends JpaRepository<CanceledReservation, Long> {

    List<CanceledReservation> findAllByCoachId(Long coachId);

    List<CanceledReservation> findAllByCrewId(Long crewId);
}
