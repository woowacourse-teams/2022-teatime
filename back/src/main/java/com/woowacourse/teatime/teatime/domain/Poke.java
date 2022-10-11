package com.woowacourse.teatime.teatime.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Poke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long crewId;

    @Column(nullable = false)
    private Long coachId;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Poke(Long crewId, Long coachId) {
        this.crewId = crewId;
        this.coachId = coachId;
    }
}
