package com.woowacourse.teatime.teatime.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QuestionServiceTest {

    private Coach coach;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CoachRepository coachRepository;

    @BeforeEach
    void setUp() {
        coach = coachRepository.save(getCoachJason());
        디폴트_질문을_생성한다();
    }

    @DisplayName("코치의 면담 시트를 조회한다.")
    @Test
    void get() {
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
        //when
        List<String> contents = questionRepository.findAllByCoachIdOrderByNumber(coach.getId()).stream()
                .map(Question::getContent)
                .collect(Collectors.toList());

        //then
        assertThat(contents).containsOnly(
                QUESTION_CONTENT_1,
                QUESTION_CONTENT_2,
                QUESTION_CONTENT_3);
    }

    @DisplayName("코치의 면담 시트 디폴트 질문의 필수 여부가 true인지 확인한다.")
    @Test
    void create_필수_여부_등록_확인() {
        //when
        final List<Boolean> isRequired = questionRepository.findAllByCoachIdOrderByNumber(coach.getId()).stream()
                .map(Question::getIsRequired)
                .collect(Collectors.toList());

        //then
        assertThat(isRequired).containsExactly(true, true, true);
    }

    @DisplayName("코치의 면담 시트 질문을 업데이트한다.")
    @Test
    void update() {
        //when
        List<SheetQuestionUpdateRequest> newQuestions =
                List.of(CUSTOM_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, CUSTOM_SHEET_QUESTION_3);
        questionService.update(coach.getId(), newQuestions);

        //then
        List<String> contents = questionRepository.findAllByCoachIdOrderByNumber(coach.getId()).stream()
                .map(Question::getContent)
                .collect(Collectors.toList());
        List<Boolean> isRequired = questionRepository.findAllByCoachIdOrderByNumber(coach.getId()).stream()
                .map(Question::getIsRequired)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(contents).containsOnly(
                        CUSTOM_QUESTION_CONTENT_1,
                        QUESTION_CONTENT_2,
                        CUSTOM_QUESTION_CONTENT_3),
                () -> assertThat(isRequired).containsExactly(true, true, true)
        );

    }

    @DisplayName("코치의 면담 시트 질문을 업데이트한다. - 질문 개수가 다섯 개")
    @Test
    void update_질문_개수_여러개() {
        //when
        List<SheetQuestionUpdateRequest> newQuestions =
                List.of(CUSTOM_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, CUSTOM_SHEET_QUESTION_3,
                        CUSTOM_SHEET_QUESTION_4, CUSTOM_SHEET_QUESTION_5);
        questionService.update(coach.getId(), newQuestions);

        //then
        List<String> contents = questionRepository.findAllByCoachIdOrderByNumber(coach.getId()).stream()
                .map(Question::getContent)
                .collect(Collectors.toList());
        List<Boolean> isRequired = questionRepository.findAllByCoachIdOrderByNumber(coach.getId()).stream()
                .map(Question::getIsRequired)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(contents).containsOnly(
                        CUSTOM_QUESTION_CONTENT_1,
                        QUESTION_CONTENT_2,
                        CUSTOM_QUESTION_CONTENT_3,
                        CUSTOM_QUESTION_CONTENT_4,
                        CUSTOM_QUESTION_CONTENT_5),
                () -> assertThat(isRequired).containsExactly(true, true, true, false, true)
        );
    }

    @DisplayName("코치의 면담 시트 질문을 업데이트한다. - 질문 개수가 한 개")
    @Test
    void update_질문_개수가_디폴트_보다_적은_경우() {
        //when
        List<SheetQuestionUpdateRequest> newQuestions =
                List.of(CUSTOM_SHEET_QUESTION_1);
        questionService.update(coach.getId(), newQuestions);

        //then
        List<String> contents = questionRepository.findAllByCoachIdOrderByNumber(coach.getId()).stream()
                .map(Question::getContent)
                .collect(Collectors.toList());
        List<Boolean> isRequired = questionRepository.findAllByCoachIdOrderByNumber(coach.getId()).stream()
                .map(Question::getIsRequired)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(contents).containsOnly(CUSTOM_QUESTION_CONTENT_1),
                () -> assertThat(isRequired).containsExactly(true)
        );
    }

    private void 디폴트_질문을_생성한다() {
        List<SheetQuestionUpdateRequest> defaultQuestions =
                List.of(DEFAULT_SHEET_QUESTION_1, DEFAULT_SHEET_QUESTION_2, DEFAULT_SHEET_QUESTION_3);
        questionService.update(coach.getId(), defaultQuestions);
    }
}
