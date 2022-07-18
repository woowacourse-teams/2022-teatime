package com.woowacourse.teatime.controller.dto;

public class CoachSaveRequest {

    private String name;
    private String description;
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
