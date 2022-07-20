package com.woowacourse.teatime.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.teatime.controller.dto.CoachSaveRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CoachControllerTest extends ControllerTest {

    @DisplayName("코치를 등록한다.")
    @Test
    void reserve() throws Exception {
        mockMvc.perform(post("/api/coaches", new CoachSaveRequest("brown", "I am a legend", "image")))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("코치를 등록할 때, 이름이 비어 있으면 400에러를 낸다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  "})
    void reserveFailNameIsBlank(String name) throws Exception {
        mockMvc.perform(post("/api/coaches", new CoachSaveRequest(name, "I am a legend", "image")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("코치를 등록할 때, 설명이 비어 있으면 400에러를 낸다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  "})
    void reserveFailDescriptionIsNull(String description) throws Exception {
        mockMvc.perform(post("/api/coaches", new CoachSaveRequest("brown", description, "image")))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("코치를 등록할 때, 이미지가 비어 있으면 400에러를 낸다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "", "  "})
    void reserveFailImageIsBlank(String image) throws Exception {
        mockMvc.perform(post("/api/coaches", new CoachSaveRequest("brown", "I am a legend", image)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
