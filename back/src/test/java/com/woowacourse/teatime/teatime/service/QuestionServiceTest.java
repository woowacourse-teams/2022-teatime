package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_QUESTION_CONTENT_1;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_QUESTION_CONTENT_3;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_SHEET_QUESTION_1;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_SHEET_QUESTION_3;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.DEFAULT_SHEET_QUESTION_1;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.DEFAULT_SHEET_QUESTION_2;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.DEFAULT_SHEET_QUESTION_3;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.QUESTION_CONTENT_1;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.QUESTION_CONTENT_2;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.QUESTION_CONTENT_3;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.teatime.teatime.controller.dto.request.SheetQuestionUpdateDto;
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

    @DisplayName("코치의 면담 시트 디폴트 질문을 저장한다.")
    @Test
    void create() {
        //given
        Coach coach = coachRepository.save(COACH_BROWN);

        //when 
        List<SheetQuestionUpdateDto> defaultQuestions =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        questionService.updateQuestions(coach.getId(), defaultQuestions);

        //then
        List<String> contents = questionRepository.findAllByCoachId(coach.getId()).stream()
                .map(Question::getContent)
                .collect(Collectors.toList());

        assertThat(contents).containsOnly(
                QUESTION_CONTENT_1,
                QUESTION_CONTENT_2,
                QUESTION_CONTENT_3);
    }

    @DisplayName("코치의 면담 시트 디폴트 질문의 필수 여부가 true인지 확인한다.")
    @Test
    void create_필수_여부_등록_확인() {
        //given
        Coach coach = coachRepository.save(COACH_BROWN);

        //when 
        List<SheetQuestionUpdateDto> defaultQuestions =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        questionService.updateQuestions(coach.getId(), defaultQuestions);

        //then
        final List<Boolean> isRequired = questionRepository.findAllByCoachId(coach.getId()).stream()
                .map(Question::getIsRequired)
                .collect(Collectors.toList());
        assertThat(isRequired).containsOnly(true, true, true);
    }

    @DisplayName("코치의 면담 시트 질문을 업데이트한다.")
    @Test
    void create2() {
        Coach coach = coachRepository.save(COACH_BROWN);
        List<SheetQuestionUpdateDto> defaultQuestions =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        questionService.updateQuestions(coach.getId(), defaultQuestions);

        List<SheetQuestionUpdateDto> newQuestions =
                List.of(CUSTOM_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, CUSTOM_SHEET_QUESTION_3);
        questionService.updateQuestions(coach.getId(), newQuestions);

        List<String> contents = questionRepository.findAllByCoachId(coach.getId()).stream()
                .map(Question::getContent)
                .collect(Collectors.toList());

        assertThat(contents).containsOnly(
                CUSTOM_QUESTION_CONTENT_1,
                QUESTION_CONTENT_2,
                CUSTOM_QUESTION_CONTENT_3);
    }
}
