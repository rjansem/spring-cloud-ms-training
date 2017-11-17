package com.github.rjansem.microservices.training.profile.domain.calendar;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Bean représentant la liste des jours fériés PBI
 *
 * @author mbouhamyd
 */
public class BusinessMonth {

    private Integer year;

    private Integer month;

    private Set<Week> weeks = new HashSet<>();

    public BusinessMonth() {
    }

    public BusinessMonth(Integer year, Integer month) {
        this.year = year;
        this.month = month;
    }

    public BusinessMonth(Integer year, Integer month, Set<Week> weeks) {
        this.year = year;
        this.month = month;
        this.weeks = weeks;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Set<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(Set<Week> weeks) {
        this.weeks = weeks;
    }

    //[Code review Jocelyn] : les equals et hashcode ne doivent pas contenir les 'weeks'
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessMonth)) {
            return false;
        }
        BusinessMonth that = (BusinessMonth) o;
        return Objects.equals(year, that.year) &&
                Objects.equals(month, that.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("year", year)
                .append("month", month)
                .append("weeks", weeks.toString())
                .toString();
    }
}
