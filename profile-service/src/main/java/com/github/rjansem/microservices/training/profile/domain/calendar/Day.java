package com.github.rjansem.microservices.training.profile.domain.calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Bean représentant un jour férié [PBI]
 *
 * @author bouhamyd
 */
public class Day {

    //[Code review Jocelyn] : Ben non il faut surout pas passer les champs en final sinon jackson ne va pas kiffer
    private Integer dayOfMonth;

    private Integer dayOfWeek;

    private Boolean currentBusinessDay;

    public Day() {
        // For jackson
    }

    public Day(int dayOfMonth, int dayOfWeek, boolean currentBusinessDay) {
        this.dayOfMonth = dayOfMonth;
        this.dayOfWeek = dayOfWeek;
        this.currentBusinessDay = currentBusinessDay;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public boolean getCurrentBusinessDay() {
        return currentBusinessDay;
    }

    public void setCurrentBusinessDay(boolean currentBusinessDay) {
        this.currentBusinessDay = currentBusinessDay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dayOfMonth", dayOfMonth)
                .append("dayOfWeek", dayOfWeek)
                .append("currentBusinessDay", currentBusinessDay)
                .toString();
    }
}

