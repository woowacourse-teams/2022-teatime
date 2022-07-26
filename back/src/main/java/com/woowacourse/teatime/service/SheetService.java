package com.woowacourse.teatime.service;

import com.woowacourse.teatime.domain.Question;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Sheet;
import com.woowacourse.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.repository.QuestionRepository;
import com.woowacourse.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.repository.SheetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SheetService {

    private final SheetRepository sheetRepository;
    private final QuestionRepository questionRepository;
    private final ReservationRepository reservationRepository;

    public int saveNewSheets(long coachId, long reservationId) {
        List<Question> questions = questionRepository.findByCoachId(coachId);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);

        for (Question question : questions) {
            sheetRepository.save(new Sheet(reservation, question.getNumber(), question.getContent()));
        }

        return questions.size();
    }
}
