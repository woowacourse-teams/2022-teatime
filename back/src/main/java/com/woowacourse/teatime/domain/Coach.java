package com.woowacourse.teatime.domain;

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

    @Column(nullable = false, length = 10)
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
}
