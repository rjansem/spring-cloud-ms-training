package com.github.rjansem.microservices.training.profile.domain.calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Bean représentant businessMonths
 *
 * @author mbouhamyd
 */
public class RetrieveCalendar {

    private Set<BusinessMonth> businessMonths = new HashSet<>();

    public Set<BusinessMonth> getBusinessMonths() {
        return businessMonths;
    }

    public void setBusinessMonths(Set<BusinessMonth> businessMonths) {
        this.businessMonths = businessMonths;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("businessMonths", businessMonths)
                .toString();
    }
}
