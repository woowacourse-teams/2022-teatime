package com.woowacourse.teatime.teatime.domain;

import com.woowacourse.teatime.teatime.exception.InvalidProfileInfoException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String slackId;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private String email;

    @Lob
    private String description;

    private String image;

    public Coach(String slackId, String name, String email, String image) {
        this(slackId, name, email, getDescription(name), image);
    }

    public Coach(String slackId, String name, String email, String description, String image) {
        this.slackId = slackId;
        this.name = name;
        this.email = email;
        this.description = description;
        this.image = image;
    }

    public void modifyProfile(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidProfileInfoException();
        }
        this.name = name.trim();
        this.description = getDescription(name);
    }

    @NotNull
    private static String getDescription(String name) {
        return "안녕하세요~ " + name + "입니다:)";
    }

    public void setSlackId(String slackId) {
        if (needToUpdateSlackId(slackId)) {
            this.slackId = slackId;
        }
    }

    private boolean needToUpdateSlackId(String slackId) {
        return this.slackId == null || this.slackId.isBlank() || !this.slackId.equals(slackId);
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
