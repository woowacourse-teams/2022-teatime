package com.woowacourse.teatime.teatime.domain;

import com.woowacourse.teatime.teatime.exception.InvalidProfileInfoException;
import java.util.Objects;
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
public class Coach {

    public static final String DEFAULT_DESCRIPTION = "안녕하세요~ %s입니다:)";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String slackId;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 60)
    private String description;

    private String image;

    @Column(nullable = false)
    private Boolean isPokable;

    public Coach(String slackId, String name, String email, String image) {
        this.slackId = slackId;
        this.name = name;
        this.email = email;
        this.description = String.format(DEFAULT_DESCRIPTION, name);
        this.image = image;
        this.isPokable = true;
    }

    public void modifyProfile(String name, String description, Boolean isPokable) {
        validateProfile(name, description, isPokable);
        this.name = name.trim();
        this.description = description.trim();
        this.isPokable = isPokable;
    }

    private void validateProfile(String name, String description, Boolean isPokable) {
        if ((name == null || name.isBlank())
                || (description == null || description.isBlank())
                || isPokable == null) {
            throw new InvalidProfileInfoException();
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coach coach = (Coach) o;
        return Objects.equals(slackId, coach.slackId) && Objects.equals(name, coach.name)
                && Objects.equals(email, coach.email) && Objects.equals(description, coach.description)
                && Objects.equals(image, coach.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slackId, name, email, description, image);
    }
}
