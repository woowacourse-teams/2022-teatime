package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.exception.UnAuthorizedException;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateDto;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindCrewSheetResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnSheetResponse;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Question;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.Sheet;
import com.woowacourse.teatime.teatime.domain.SheetStatus;
import com.woowacourse.teatime.teatime.exception.CannotSubmitBlankException;
import com.woowacourse.teatime.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.QuestionRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.SheetRepository;
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
        Reservation reservation = findReservation(reservationId);

        List<Sheet> sheets = questions.stream()
                .map(question -> new Sheet(reservation, question.getNumber(), question.getContent()))
                .collect(Collectors.toList());

        List<Sheet> savedSheets = sheetRepository.saveAll(sheets);
        return savedSheets.size();
    }

    public int saveV2(Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        Coach coach = reservation.getCoach();
        List<Question> questions = questionRepository.findByCoachId(coach.getId());

        List<Sheet> sheets = questions.stream()
                .map(question -> new Sheet(reservation, question.getNumber(), question.getContent()))
                .collect(Collectors.toList());

        List<Sheet> savedSheets = sheetRepository.saveAll(sheets);
        return savedSheets.size();
    }

    public CrewFindOwnSheetResponse findOwnSheetByCrew(Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        List<Sheet> sheets = sheetRepository.findByReservationIdOrderByNumber(reservationId);
        return CrewFindOwnSheetResponse.of(reservation, sheets);
    }

    public CrewFindOwnSheetResponse findOwnSheetByCrewV2(Long crewId, Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        validateAuthorization(crewId, reservation);
        List<Sheet> sheets = sheetRepository.findByReservationIdOrderByNumber(reservationId);
        return CrewFindOwnSheetResponse.of(reservation, sheets);
    }

    private void validateAuthorization(Long crewId, Reservation reservation) {
        if (!reservation.isSameCrew(crewId)) {
            throw new UnAuthorizedException();
        }
    }

    public CoachFindCrewSheetResponse findCrewSheetByCoach(Long crewId, Long reservationId) {
        validateCrew(crewId);
        Reservation reservation = findReservation(reservationId);
        List<Sheet> sheets = sheetRepository.findByReservationIdOrderByNumber(reservationId);
        return CoachFindCrewSheetResponse.of(reservation, sheets);
    }

    private void validateCrew(Long crewId) {
        crewRepository.findById(crewId)
                .orElseThrow(NotFoundCrewException::new);
    }

    public void updateAnswer(Long reservationId, SheetAnswerUpdateRequest request) {
        validateReservation(reservationId);
        SheetStatus status = request.getStatus();
        List<SheetAnswerUpdateDto> sheetDtos = request.getSheets();
        if (SheetStatus.SUBMITTED.equals(status)) {
            validateAnswer(sheetDtos);
        }

        List<Sheet> sheets = sheetRepository.findByReservationIdOrderByNumber(reservationId);
        for (Sheet sheet : sheets) {
            modifyAnswer(sheetDtos, sheet);
        }
    }

    public void updateAnswerV2(Long crewId, Long reservationId, SheetAnswerUpdateRequest request) {
        validateAuthorization(crewId, findReservation(reservationId));
        SheetStatus status = request.getStatus();
        List<SheetAnswerUpdateDto> sheetDtos = request.getSheets();
        if (SheetStatus.SUBMITTED.equals(status)) {
            validateAnswer(sheetDtos);
        }

        List<Sheet> sheets = sheetRepository.findByReservationIdOrderByNumber(reservationId);
        for (Sheet sheet : sheets) {
            modifyAnswer(sheetDtos, sheet);
        }
    }

    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
    }

    private void validateReservation(Long reservationId) {
        reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
    }

    private void validateAnswer(List<SheetAnswerUpdateDto> sheetDtos) {
        for (SheetAnswerUpdateDto sheetDto : sheetDtos) {
            String answerContent = sheetDto.getAnswerContent();
            if (answerContent == null || answerContent.isBlank()) {
                throw new CannotSubmitBlankException();
            }
        }
    }

    private void modifyAnswer(List<SheetAnswerUpdateDto> sheetDtos, Sheet sheet) {
        for (SheetAnswerUpdateDto sheetDto : sheetDtos) {
            sheet.modifyAnswer(sheetDto.getQuestionNumber(), sheetDto.getAnswerContent());
        }
    }
}
