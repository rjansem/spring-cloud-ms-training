package com.github.rjansem.microservices.training.profile.domain.calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Bean représentant un week
 *
 * @author mbouhamyd
 */
public class Week {

    private Set<Day> days = new HashSet<>();

    public Week() {
    }

    public Week(Set<Day> days) {
        this.days = days;
    }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("days", days.toString())
                .toString();
    }
}
