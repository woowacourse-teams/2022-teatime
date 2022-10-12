package com.woowacourse.teatime.teatime.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.teatime.auth.infrastructure.JwtTokenProvider;
import com.woowacourse.teatime.auth.infrastructure.PayloadDto;
import com.woowacourse.teatime.auth.infrastructure.PayloadExtractor;
import com.woowacourse.teatime.auth.infrastructure.ResponseCookieTokenProvider;
import com.woowacourse.teatime.auth.service.AuthService;
import com.woowacourse.teatime.auth.service.LoginService;
import com.woowacourse.teatime.auth.service.UserAuthService;
import com.woowacourse.teatime.teatime.aspect.QueryCountInspector;
import com.woowacourse.teatime.teatime.service.CoachService;
import com.woowacourse.teatime.teatime.service.CrewService;
import com.woowacourse.teatime.teatime.service.ReservationService;
import com.woowacourse.teatime.teatime.service.ScheduleService;
import com.woowacourse.teatime.teatime.service.SheetService;
import com.woowacourse.teatime.teatime.support.ControllerTest;
import com.woowacourse.teatime.teatime.support.RestDocsSupporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@MockBean(JpaMetamodelMappingContext.class)
@ControllerTest
public class ControllerTestSupporter extends RestDocsSupporter {

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    protected QueryCountInspector queryCountInspector;

    @MockBean
    private PayloadExtractor payloadExtractor;

    @MockBean
    protected ScheduleService scheduleService;

    @MockBean
    protected ReservationService reservationService;

    @MockBean
    protected SheetService sheetService;

    @MockBean
    protected CoachService coachService;

    @MockBean
    protected CrewService crewService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected UserAuthService userAuthService;

    @MockBean
    protected LoginService loginService;

    @MockBean
    protected ResponseCookieTokenProvider responseCookieTokenProvider;

    protected MockHttpServletRequestBuilder get(String url) {
        return MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
    }

    protected MockHttpServletRequestBuilder get(String url, Object body)
            throws JsonProcessingException {
        return MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(body));
    }

    protected MockHttpServletRequestBuilder post(String url, Object body)
            throws JsonProcessingException {
        return MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(body));
    }

    protected MockHttpServletRequestBuilder delete(String url) {
        return MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
    }

    protected MockHttpServletRequestBuilder put(String url) {
        return MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
    }

    protected MockHttpServletRequestBuilder put(String url, Object body)
            throws JsonProcessingException {
        return MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(body));
    }

    protected void 크루의_토큰을_검증한다(String token) {
        given(jwtTokenProvider.validateToken(token))
                .willReturn(true);
        given(payloadExtractor.extract(any()))
                .willReturn(new PayloadDto("CREW", 1L));
    }

    protected void 코치의_토큰을_검증한다(String token) {
        given(jwtTokenProvider.validateToken(token))
                .willReturn(true);
        given(payloadExtractor.extract(any()))
                .willReturn(new PayloadDto("COACH", 1L));
    }
}

