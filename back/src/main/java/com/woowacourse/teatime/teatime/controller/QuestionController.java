package com.woowacourse.teatime.teatime.controller;

import com.woowacourse.teatime.auth.support.CoachAuthenticationPrincipal;
import com.woowacourse.teatime.teatime.controller.dto.response.SheetQuestionsResponse;
import com.woowacourse.teatime.teatime.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/questions")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<SheetQuestionsResponse> get(@CoachAuthenticationPrincipal Long coachId) {
        SheetQuestionsResponse response = questionService.get(coachId);
        return ResponseEntity.ok(response);
    }

//    @PutMapping
//    public ResponseEntity<Void> update(
//            @CoachAuthenticationPrincipal Long coachId, @Valid @RequestBody List<SheetQuestionUpdateDto> request) {
//        questionService.updateQuestions(coachId, request);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
