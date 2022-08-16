package com.woowacourse.teatime.teatime.controller;

import static com.woowacourse.teatime.teatime.fixture.DtoFixture.COACH_BROWN_SAVE_REQUEST;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.teatime.controller.dto.request.CoachSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CoachControllerTest extends ControllerTest {

    @DisplayName("코치를 등록한다.")
    @Test
    void reserve() throws Exception {
        mockMvc.perform(post("/api/coaches", COACH_BROWN_SAVE_REQUEST))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("코치를 등록할 때, 이름이 비어 있으면 400에러를 낸다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  "})
    void reserveFailNameIsBlank(String name) throws Exception {
        mockMvc.perform(post("/api/coaches", new CoachSaveRequest(name, "brown@email.com", "I am a legend", "image")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("코치를 등록할 때, 설명이 비어 있으면 400에러를 낸다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  "})
    void reserveFailDescriptionIsNull(String description) throws Exception {
        mockMvc.perform(post("/api/coaches", new CoachSaveRequest("brown", "brown@email.com", description, "image")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("코치를 등록할 때, 이미지가 비어 있으면 400에러를 낸다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  "})
    void reserveFailImageIsBlank(String image) throws Exception {
        mockMvc.perform(post("/api/coaches", new CoachSaveRequest("brown", "brown@email.com", "I am a legend", image)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("코치의 면담 현황 목록 조회에 성공한다.")
    @Test
    void findReservations() throws Exception {
        mockMvc.perform(get("/api/coaches/me/reservations").header("coachId", 1L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("코치의 면담 현황 목록 조회에 실패한다. - 잘못된 코치 아이디")
    @Test
    void findReservationsFailWrongCoachId() throws Exception {
        mockMvc.perform(get("/api/coaches/me/reservations").header("coachId", "a"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}