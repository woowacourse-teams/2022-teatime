package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.controller.dto.request.SheetQuestionUpdateDto;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Question;
import com.woowacourse.teatime.teatime.exception.NotFoundCoachException;
import com.woowacourse.teatime.teatime.repository.CoachRepository;
import com.woowacourse.teatime.teatime.repository.QuestionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CoachRepository coachRepository;

    public void updateQuestions(Long coachId, List<SheetQuestionUpdateDto> request) {
        Coach coach = findCoach(coachId);

        List<Question> savedQuestions = questionRepository.findAllByCoachId(coach.getId());
        List<Question> requestQuestions = request.stream()
                .map(question -> new Question(coach, question.getQuestionNumber(), question.getQuestionContent()))
                .collect(Collectors.toList());

        deleteOldQuestions(savedQuestions, requestQuestions);
        saveNewQuestions(savedQuestions, requestQuestions);
    }

    private Coach findCoach(Long coachId) {
        return coachRepository.findById(coachId)
                .orElseThrow(NotFoundCoachException::new);
    }

    private void deleteOldQuestions(List<Question> savedQuestions, List<Question> requestQuestions) {
        List<Question> copyQuestions = new ArrayList<>(savedQuestions);
        copyQuestions.removeAll(requestQuestions);
        questionRepository.deleteAllInBatch(copyQuestions);
    }

    private void saveNewQuestions(List<Question> savedQuestions, List<Question> requestQuestions) {
        requestQuestions.removeAll(savedQuestions);
        questionRepository.saveAll(requestQuestions);
    }
}
