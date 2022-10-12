package com.woowacourse.teatime.teatime.service.dto.poke;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TextWithImageBlock {

    private String type;
    private TextDto text;
    private AccessoryDto accessory;
}
