package com.woowacourse.teatime;

import com.woowacourse.teatime.domain.Coach;

public class CoachResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final String image;

    private CoachResponse() {
        this(null, null, null, null);
    }

    public CoachResponse(Coach coach) {
        this(coach.getId(), coach.getName(), coach.getDescription(), coach.getImage());
    }

    public CoachResponse(Long id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Long getId() {
        return id;
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
