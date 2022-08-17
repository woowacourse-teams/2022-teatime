package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Question;
import com.woowacourse.teatime.teatime.repository.QuestionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionService {

    private static final String DEFAULT_QUESTION_1 = "이번 면담을 통해 논의하고 싶은 내용";
    private static final String DEFAULT_QUESTION_2 = "최근에 자신이 긍정적으로 보는 시도와 변화";
    private static final String DEFAULT_QUESTION_3 = "이번 면담을 통해 생기기를 원하는 변화";

    private final QuestionRepository questionRepository;

    public void saveDefaultQuestion(Coach coach) {
        List<Question> questions = List.of(
                new Question(coach, 1, DEFAULT_QUESTION_1),
                new Question(coach, 2, DEFAULT_QUESTION_2),
                new Question(coach, 3, DEFAULT_QUESTION_3));
        questionRepository.saveAll(questions);
    }
}
