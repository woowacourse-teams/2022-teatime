package com.woowacourse.teatime.repository;

import com.woowacourse.teatime.domain.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCoachId(Long coachId);
}
