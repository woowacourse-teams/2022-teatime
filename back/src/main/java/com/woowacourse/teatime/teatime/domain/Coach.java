package com.woowacourse.teatime.teatime.domain;

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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false)
    private String email;

    @Lob
    private String description;

    private String image;

    public Coach(String name, String email, String image) {
        this(name, email, "안녕하세요~ " + name + "입니다:)", image);
    }

    public Coach(String name, String email, String description, String image) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.image = image;
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
        return Objects.equals(getName(), coach.getName()) && Objects.equals(getEmail(), coach.getEmail())
                && Objects.equals(getDescription(), coach.getDescription()) && Objects.equals(getImage(),
                coach.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getEmail(), getDescription(), getImage());
    }
}
