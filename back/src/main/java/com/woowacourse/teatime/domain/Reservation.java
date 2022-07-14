package com.woowacourse.teatime.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Schedule schedule;

    @ManyToOne
    private Crew crew;

    protected Reservation() {
    }

    public Reservation(Schedule schedule, Crew crew) {
        this.schedule = schedule;
        this.crew = crew;
    }

    public Long getId() {
        return id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Crew getCrew() {
        return crew;
    }
}
