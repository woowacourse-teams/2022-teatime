package com.woowacourse.teatime.teatime.support;

import static org.springframework.restdocs.http.HttpDocumentation.httpRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(value = {RestDocumentationExtension.class})
public class RestDocsSupporter {

    @Autowired
    protected MockMvc mockMvc;

    @BeforeEach
    void setUp(final WebApplicationContext context, final RestDocumentationContextProvider provider) {
        setMockMvcRestDocsSpec(context, provider);
    }

    private void setMockMvcRestDocsSpec(final WebApplicationContext context,
                                        final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider)
                        .snippets()
                        .withDefaults(httpRequest(), httpResponse())
                        .and()
                        .operationPreprocessors()
                        .withRequestDefaults(
                                modifyUris()
                                        .scheme("https")
                                        .host("api.teatime.pe.kr")
                                        .removePort(),
                                prettyPrint()
                        ).withResponseDefaults(
                                removeHeaders(
                                        "Transfer-Encoding",
                                        "Date",
                                        "Keep-Alive",
                                        "Connection"),
                                prettyPrint()
                        )
                ).build();
    }
}
