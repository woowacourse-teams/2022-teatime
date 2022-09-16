package com.woowacourse.teatime.teatime.controller.dto.request;

import com.woowacourse.teatime.teatime.domain.SheetStatus;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SheetAnswerUpdateRequest {

    @NotNull
    private SheetStatus status;

    private List<SheetAnswerUpdateDto> sheets;
}
