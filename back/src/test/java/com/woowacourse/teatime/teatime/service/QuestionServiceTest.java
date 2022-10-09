package com.woowacourse.teatime.teatime.service;

import static com.woowacourse.teatime.teatime.fixture.DomainFixture.COACH_BROWN;
import static com.woowacourse.teatime.teatime.fixture.DomainFixture.getCoachJason;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_QUESTION_CONTENT_1;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_QUESTION_CONTENT_3;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_QUESTION_CONTENT_4;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_QUESTION_CONTENT_5;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_SHEET_QUESTION_1;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_SHEET_QUESTION_3;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_SHEET_QUESTION_4;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.CUSTOM_SHEET_QUESTION_5;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.DEFAULT_SHEET_QUESTION_1;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.DEFAULT_SHEET_QUESTION_2;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.DEFAULT_SHEET_QUESTION_3;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.QUESTION_CONTENT_1;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.QUESTION_CONTENT_2;
import static com.woowacourse.teatime.teatime.fixture.DtoFixture.QUESTION_CONTENT_3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.teatime.teatime.controller.dto.request.SheetQuestionUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.SheetQuestionResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.SheetQuestionsResponse;
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

    @DisplayName("코치의 면담 시트를 조회한다.")
    @Test
    void get() {
        //given
        Coach coach = coachRepository.save(getCoachJason());
        List<SheetQuestionUpdateRequest> defaultQuestions =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        questionService.update(coach.getId(), defaultQuestions);

        //when
        SheetQuestionsResponse sheetQuestionsResponse = questionService.get(coach.getId());

        //then
        List<String> contents = sheetQuestionsResponse.getQuestions().stream()
                .map(SheetQuestionResponse::getQuestionContent)
                .collect(Collectors.toList());
        assertThat(contents).containsOnly(
                QUESTION_CONTENT_1,
                QUESTION_CONTENT_2,
                QUESTION_CONTENT_3
        );
    }

    @DisplayName("코치의 면담 시트 디폴트 질문을 저장한다.")
    @Test
    void create() {
        //given
        Coach coach = coachRepository.save(COACH_BROWN);

        //when 
        List<SheetQuestionUpdateRequest> defaultQuestions =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        questionService.update(coach.getId(), defaultQuestions);

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
        List<SheetQuestionUpdateRequest> defaultQuestions =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        questionService.update(coach.getId(), defaultQuestions);

        //then
        final List<Boolean> isRequired = questionRepository.findAllByCoachId(coach.getId()).stream()
                .map(Question::getIsRequired)
                .collect(Collectors.toList());
        assertThat(isRequired).containsOnly(true, true, true);
    }

    @DisplayName("코치의 면담 시트 질문을 업데이트한다.")
    @Test
    void update() {
        //given
        Coach coach = coachRepository.save(COACH_BROWN);
        List<SheetQuestionUpdateRequest> defaultQuestions =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        questionService.update(coach.getId(), defaultQuestions);

        //when
        List<SheetQuestionUpdateRequest> newQuestions =
                List.of(CUSTOM_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, CUSTOM_SHEET_QUESTION_3);
        questionService.update(coach.getId(), newQuestions);

        //then
        List<String> contents = questionRepository.findAllByCoachId(coach.getId()).stream()
                .map(Question::getContent)
                .collect(Collectors.toList());

        final List<Boolean> isRequired = questionRepository.findAllByCoachId(coach.getId()).stream()
                .map(Question::getIsRequired)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(contents).containsOnly(
                        CUSTOM_QUESTION_CONTENT_1,
                        QUESTION_CONTENT_2,
                        CUSTOM_QUESTION_CONTENT_3),
                () -> assertThat(isRequired).containsOnly(true, true, true)
        );

    }

    @DisplayName("코치의 면담 시트 질문을 업데이트한다. - 질문 개수가 다섯 개")
    @Test
    void update_질문_개수_여러개() {
        //given
        Coach coach = coachRepository.save(COACH_BROWN);
        List<SheetQuestionUpdateRequest> defaultQuestions =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        questionService.update(coach.getId(), defaultQuestions);

        //when
        List<SheetQuestionUpdateRequest> newQuestions =
                List.of(CUSTOM_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, CUSTOM_SHEET_QUESTION_3,
                        CUSTOM_SHEET_QUESTION_4, CUSTOM_SHEET_QUESTION_5);
        questionService.update(coach.getId(), newQuestions);

        //then
        List<String> contents = questionRepository.findAllByCoachId(coach.getId()).stream()
                .map(Question::getContent)
                .collect(Collectors.toList());

        final List<Boolean> isRequired = questionRepository.findAllByCoachId(coach.getId()).stream()
                .map(Question::getIsRequired)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(contents).containsOnly(
                        CUSTOM_QUESTION_CONTENT_1,
                        QUESTION_CONTENT_2,
                        CUSTOM_QUESTION_CONTENT_3,
                        CUSTOM_QUESTION_CONTENT_4,
                        CUSTOM_QUESTION_CONTENT_5),
                () -> assertThat(isRequired).containsOnly(true, true, true, false, true)
        );
    }

    @DisplayName("코치의 면담 시트 질문을 업데이트한다. - 질문 개수가 한 개")
    @Test
    void update_질문_개수가_디폴트_보다_적은_경우() {
        //given
        Coach coach = coachRepository.save(COACH_BROWN);
        List<SheetQuestionUpdateRequest> defaultQuestions =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        questionService.update(coach.getId(), defaultQuestions);

        //when
        List<SheetQuestionUpdateRequest> newQuestions =
                List.of(CUSTOM_SHEET_QUESTION_1);
        questionService.update(coach.getId(), newQuestions);

        //then
        List<String> contents = questionRepository.findAllByCoachId(coach.getId()).stream()
                .map(Question::getContent)
                .collect(Collectors.toList());

        final List<Boolean> isRequired = questionRepository.findAllByCoachId(coach.getId()).stream()
                .map(Question::getIsRequired)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(contents).containsOnly(CUSTOM_QUESTION_CONTENT_1),
                () -> assertThat(isRequired).containsOnly(true)
        );
    }
}
