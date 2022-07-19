package com.woowacourse.teatime.controller.dto;

import javax.validation.constraints.NotBlank;

public class CoachSaveRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String image;

    private CoachSaveRequest() {
    }

    public CoachSaveRequest(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
