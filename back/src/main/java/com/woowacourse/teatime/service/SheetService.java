package com.woowacourse.teatime.service;

import com.woowacourse.teatime.controller.dto.response.CoachFindCrewSheetResponse;
import com.woowacourse.teatime.controller.dto.response.CrewFindOwnSheetResponse;
import com.woowacourse.teatime.domain.Question;
import com.woowacourse.teatime.domain.Reservation;
import com.woowacourse.teatime.domain.Sheet;
import com.woowacourse.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.repository.CrewRepository;
import com.woowacourse.teatime.repository.QuestionRepository;
import com.woowacourse.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.repository.SheetRepository;
import java.util.List;
import java.util.stream.Collectors;
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
    private final CrewRepository crewRepository;

    public int save(Long coachId, Long reservationId) {
        List<Question> questions = questionRepository.findByCoachId(coachId);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);

        List<Sheet> sheets = questions.stream()
                .map(question -> new Sheet(reservation, question.getNumber(), question.getContent()))
                .collect(Collectors.toList());

        List<Sheet> savedSheets = sheetRepository.saveAll(sheets);
        return savedSheets.size();
    }

    public CrewFindOwnSheetResponse findOwnSheetByCrew(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        List<Sheet> sheets = sheetRepository.findByReservationIdOrderByNumber(reservationId);
        return CrewFindOwnSheetResponse.of(reservation, sheets);
    }

    public CoachFindCrewSheetResponse findCrewSheetByCoach(Long crewId, Long reservationId) {
        validateCrew(crewId);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
        List<Sheet> sheets = sheetRepository.findByReservationIdOrderByNumber(reservationId);
        return CoachFindCrewSheetResponse.of(reservation, sheets);
    }

    private void validateCrew(Long crewId) {
        crewRepository.findById(crewId)
                .orElseThrow(NotFoundCrewException::new);
    }
}
