package com.woowacourse.teatime.teatime.repository;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getQuestion1;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getQuestion2;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getQuestion3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.support.RepositoryTestSupporter;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Question;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class QuestionRepositoryTest extends RepositoryTestSupporter {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void findAllByCoachIdOrderByNumber() {
        //given
        Coach coach = coachRepository.save(getCoachJason());
        questionDao.saveAll(List.of(getQuestion3(coach), getQuestion1(coach), getQuestion2(coach)));

        //when
        final List<Question> findQuestions = questionRepository.findAllByCoachIdOrderByNumber(coach.getId());

        //then
        List<Integer> questionsNumbers = findQuestions.stream()
                .map(Question::getNumber)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(findQuestions).hasSize(3),
                () -> assertThat(questionsNumbers).containsOnly(1, 2, 3)
        );
    }
}
