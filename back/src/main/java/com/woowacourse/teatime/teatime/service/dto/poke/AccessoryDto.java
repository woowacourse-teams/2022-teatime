package com.woowacourse.teatime.teatime.service.dto.poke;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccessoryDto {

    private String type;

    @JsonProperty(value = "image_url")
    private String imageUrl;

    @JsonProperty(value = "alt_text")
    private String altText;
}
