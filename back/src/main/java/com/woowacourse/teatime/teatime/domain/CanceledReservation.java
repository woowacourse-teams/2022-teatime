package com.woowacourse.teatime.teatime.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class CanceledReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @ManyToOne
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @Column(nullable = false)
    private LocalDateTime scheduledAt;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public CanceledReservation(Coach coach, Crew crew, LocalDateTime scheduledAt) {
        this.coach = coach;
        this.crew = crew;
        this.scheduledAt = scheduledAt;
    }
}
