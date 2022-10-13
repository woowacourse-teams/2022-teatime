package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.controller.dto.request.SheetQuestionUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.SheetQuestionsResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Question;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.QuestionRepository;
import com.woowacourse.teatime.teatime.repository.jdbc.QuestionDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionDao questionDao;
    private final CoachRepository coachRepository;

    public SheetQuestionsResponse get(Long coachId) {
        List<Question> savedQuestions = questionRepository.findAllByCoachIdOrderByNumber(coachId);
        return SheetQuestionsResponse.from(savedQuestions);
    }

    public void update(Long coachId, List<SheetQuestionUpdateRequest> request) {
        Coach coach = findCoach(coachId);

        List<Question> savedQuestions = questionRepository.findAllByCoachIdOrderByNumber(coach.getId());
        List<Question> requestQuestions = toQuestions(request, coach);

        deleteOldQuestions(savedQuestions, requestQuestions);
        saveNewQuestions(savedQuestions, requestQuestions);
    }

    private Coach findCoach(Long coachId) {
        return coachRepository.findById(coachId)
                .orElseThrow(NotFoundCoachException::new);
    }

    @NotNull
    private List<Question> toQuestions(List<SheetQuestionUpdateRequest> request, Coach coach) {
        return request.stream()
                .map(question -> new Question(coach,
                        question.getQuestionNumber(),
                        question.getQuestionContent(),
                        question.getIsRequired()))
                .collect(Collectors.toList());
    }

    private void deleteOldQuestions(List<Question> savedQuestions, List<Question> requestQuestions) {
        List<Question> copyQuestions = new ArrayList<>(savedQuestions);
        copyQuestions.removeAll(requestQuestions);
        questionRepository.deleteAllInBatch(copyQuestions);
    }

    private void saveNewQuestions(List<Question> savedQuestions, List<Question> requestQuestions) {
        requestQuestions.removeAll(savedQuestions);
        questionDao.saveAll(requestQuestions);
    }
}
