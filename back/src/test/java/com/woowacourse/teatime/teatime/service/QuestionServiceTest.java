package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_BROWN;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Question;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.QuestionRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QuestionServiceTest {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CoachRepository coachRepository;

    @DisplayName("코치의 면담 시트 디폴트 질문을 생성한다.")
    @Test
    void create() {
        Coach coach = coachRepository.save(COACH_BROWN);
        questionService.saveDefaultQuestion(coach);
        List<String> contents = questionRepository.findByCoachId(coach.getId()).stream()
                .map(Question::getContent)
                .collect(Collectors.toList());

        assertThat(contents).containsOnly(
                "이번 면담을 통해 논의하고 싶은 내용",
                "최근에 자신이 긍정적으로 보는 시도와 변화",
                "이번 면담을 통해 생기기를 원하는 변화");
    }
}
