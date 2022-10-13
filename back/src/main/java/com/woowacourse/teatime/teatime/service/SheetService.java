package com.woowacourse.teatime.teatime.service;

import com.woowacourse.teatime.exception.UnAuthorizedException;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateDto;
import com.woowacourse.teatime.teatime.controller.dto.request.SheetAnswerUpdateRequest;
import com.woowacourse.teatime.teatime.controller.dto.response.CoachFindCrewSheetResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnCanceledSheetResponse;
import com.woowacourse.teatime.teatime.controller.dto.response.CrewFindOwnSheetResponse;
import com.woowacourse.teatime.teatime.domain.CanceledReservation;
import com.woowacourse.teatime.teatime.domain.CanceledSheet;
import com.woowacourse.teatime.teatime.domain.Coach;
import com.woowacourse.teatime.teatime.domain.Question;
import com.woowacourse.teatime.teatime.domain.Reservation;
import com.woowacourse.teatime.teatime.domain.Sheet;
import com.woowacourse.teatime.teatime.domain.SheetStatus;
import com.woowacourse.teatime.teatime.exception.CannotSubmitBlankException;
import com.woowacourse.teatime.teatime.exception.NotFoundCrewException;
import com.woowacourse.teatime.teatime.exception.NotFoundReservationException;
import com.woowacourse.teatime.teatime.repository.CanceledReservationRepository;
import com.woowacourse.teatime.teatime.repository.CanceledSheetRepository;
import com.woowacourse.teatime.teatime.repository.CrewRepository;
import com.woowacourse.teatime.teatime.repository.QuestionRepository;
import com.woowacourse.teatime.teatime.repository.ReservationRepository;
import com.woowacourse.teatime.teatime.repository.SheetRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SheetService {

    private final SheetRepository sheetRepository;
    private final QuestionRepository questionRepository;
    private final ReservationRepository reservationRepository;
    private final CanceledReservationRepository canceledReservationRepository;
    private final CanceledSheetRepository canceledSheetRepository;
    private final CrewRepository crewRepository;

    public int save(Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        Coach coach = reservation.getCoach();
        List<Question> questions = questionRepository.findAllByCoachIdOrderByNumber(coach.getId());

        List<Sheet> sheets = toSheets(reservation, questions);
        List<Sheet> savedSheets = sheetRepository.saveAll(sheets);
        return savedSheets.size();
    }

    @NotNull
    private List<Sheet> toSheets(Reservation reservation, List<Question> questions) {
        return questions.stream()
                .map(question -> new Sheet(reservation,
                        question.getNumber(),
                        question.getContent(),
                        question.getIsRequired()))
                .collect(Collectors.toList());
    }

    public CrewFindOwnSheetResponse findOwnSheetByCrew(Long crewId, Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        validateAuthorization(crewId, reservation);
        List<Sheet> sheets = sheetRepository.findAllByReservationIdOrderByNumber(reservationId);
        return CrewFindOwnSheetResponse.of(reservation, sheets);
    }

    public CrewFindOwnCanceledSheetResponse findOwnCanceledSheetByCrew(Long crewId, Long originReservationId) {
        CanceledReservation reservation = findCanceledReservation(originReservationId);
        validateAuthorization(crewId, reservation);
        List<CanceledSheet> sheets = canceledSheetRepository.findAllByOriginId(originReservationId);
        return CrewFindOwnCanceledSheetResponse.of(reservation, sheets);
    }

    private void validateAuthorization(Long crewId, CanceledReservation reservation) {
        if (!reservation.isSameCrew(crewId)) {
            throw new UnAuthorizedException();
        }
    }

    private CanceledReservation findCanceledReservation(Long originReservationId) {
        return canceledReservationRepository.findByOriginId(originReservationId)
                .orElseThrow(NotFoundReservationException::new);
    }

    private void validateAuthorization(Long crewId, Reservation reservation) {
        if (!reservation.isSameCrew(crewId)) {
            throw new UnAuthorizedException();
        }
    }

    public CoachFindCrewSheetResponse findCrewSheetByCoach(Long crewId, Long reservationId) {
        validateCrew(crewId);
        Reservation reservation = findReservation(reservationId);
        List<Sheet> sheets = sheetRepository.findAllByReservationIdOrderByNumber(reservationId);
        return CoachFindCrewSheetResponse.of(reservation, sheets);
    }

    private void validateCrew(Long crewId) {
        crewRepository.findById(crewId)
                .orElseThrow(NotFoundCrewException::new);
    }

    public void updateAnswer(Long crewId, Long reservationId, SheetAnswerUpdateRequest request) {
        validateAuthorization(crewId, findReservation(reservationId));
        SheetStatus status = request.getStatus();
        List<SheetAnswerUpdateDto> sheetDtos = request.getSheets();
        if (SheetStatus.SUBMITTED.equals(status)) {
            validateAnswer(sheetDtos);
        }

        List<Sheet> sheets = sheetRepository.findAllByReservationIdOrderByNumber(reservationId);
        for (Sheet sheet : sheets) {
            modifyAnswer(sheetDtos, sheet);
        }
    }

    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(NotFoundReservationException::new);
    }

    private void validateAnswer(List<SheetAnswerUpdateDto> sheetDtos) {
        boolean hasEmptyAnswer = sheetDtos.stream()
                .filter(SheetAnswerUpdateDto::getIsRequired)
                .map(SheetAnswerUpdateDto::getAnswerContent)
                .anyMatch(answerContent -> answerContent == null || answerContent.isBlank());
        if (hasEmptyAnswer) {
            throw new CannotSubmitBlankException();
        }
    }

    private void modifyAnswer(List<SheetAnswerUpdateDto> sheetDtos, Sheet sheet) {
        for (SheetAnswerUpdateDto sheetDto : sheetDtos) {
            sheet.modifyAnswer(sheetDto.getQuestionNumber(), sheetDto.getAnswerContent());
        }
    }
}
