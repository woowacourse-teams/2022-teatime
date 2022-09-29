package com.woowacourse.teatime.teatime.domain;

import com.woowacourse.teatime.teatime.exception.InvalidProfileInfoException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Crew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String slackId;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private String email;

    private String image;

    public Crew(String slackId, String name, String email, String image) {
        this.slackId = slackId;
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public void modifyName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidProfileInfoException();
        }
        this.name = name.trim();
    }

    public void setSlackId(String slackId) {
        if (!slackId.equals(this.slackId)) {
            this.slackId = slackId;
        }
    }

    public void setImage(String image) {
        if (!image.equals(this.image)) {
            this.image = image;
        }
    }
}
