package com.woowacourse.teatime.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.teatime.service.CoachService;
import com.woowacourse.teatime.service.ReservationService;
import com.woowacourse.teatime.service.ScheduleService;
import com.woowacourse.teatime.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ScheduleService scheduleService;

    @MockBean
    protected ReservationService reservationService;

    @MockBean
    protected SheetService sheetService;

    @MockBean
    protected CoachService coachService;

    protected MockHttpServletRequestBuilder get(String url) {
        return MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
    }

    protected MockHttpServletRequestBuilder post(String url, Object body)
            throws JsonProcessingException {
        return MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(body));
    }
}
