package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.profile.domain.calendar.BusinessMonth;
import com.github.rjansem.microservices.training.profile.domain.calendar.Day;
import com.github.rjansem.microservices.training.profile.domain.calendar.Week;
import com.github.rjansem.microservices.training.profile.domain.calendar.BusinessMonth;
import com.github.rjansem.microservices.training.profile.domain.calendar.Day;
import com.github.rjansem.microservices.training.profile.domain.calendar.Week;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests associés au service {@link RetrieveCalendarService}
 *
 * @author mbouhamyd
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RetrieveCalendarServiceTests {

    @Autowired
    private RetrieveCalendarService retrieveCalendarService;

    @Test(expected = ValidationException.class)
    public void findDaysOff_shouldFailCuzInvalidInterval() {
        retrieveCalendarService.findDaysOff(LocalDate.now().plusDays(1), LocalDate.now());
        fail("Should have failed at this point");
    }

    @Test
    public void findDaysOff_shouldNotFindAny() {
        Set<BusinessMonth> months = retrieveCalendarService.findDaysOff(LocalDate.of(2010, 1, 1), LocalDate.of(2010, 1, 2));
        assertThat(months).isEmpty();
        Assertions.assertThat(toWeeks(months)).isEmpty();
        Assertions.assertThat(toDays(months)).isEmpty();
    }

    @Test
    public void findDaysOff_shouldFindOneExactlyMatchingInterval() {
        Set<BusinessMonth> months = retrieveCalendarService.findDaysOff(LocalDate.of(2016, 11, 1), LocalDate.of(2016, 11, 1));
        assertThat(months).isNotEmpty();
        Assertions.assertThat(toWeeks(months)).isNotEmpty().hasSize(1);
        Assertions.assertThat(toDays(months)).isNotEmpty().hasSize(1);
        Day day = toDays(months).stream().findAny().orElseThrow(() -> new IllegalStateException("Impossible"));
        assertThat(day.getDayOfMonth()).isEqualTo(1);
        assertThat(day.getDayOfWeek()).isEqualTo(2);
        assertThat(months).containsOnly(new BusinessMonth(2016, 11));
    }


    @Test
    public void findDaysOff_shouldFindOneSameStartInterval() {
        Set<BusinessMonth> months = retrieveCalendarService.findDaysOff(LocalDate.of(2016, 11, 1), LocalDate.of(2016, 11, 2));
        assertThat(months).isNotEmpty();
        Assertions.assertThat(toWeeks(months)).isNotEmpty().hasSize(1);
        assertThat(toDays(months)).isNotEmpty().hasSize(1);
        Day day = toDays(months).stream().findAny().orElseThrow(() -> new IllegalStateException("Impossible"));
        assertThat(day.getDayOfMonth()).isEqualTo(1);
        assertThat(day.getDayOfWeek()).isEqualTo(2);
        assertThat(months).containsOnly(new BusinessMonth(2016, 11));
    }

    @Test
    public void findDaysOff_shouldFindOneSameEndInterval() {
        Set<BusinessMonth> months = retrieveCalendarService.findDaysOff(LocalDate.of(2016, 10, 30), LocalDate.of(2016, 11, 1));
        assertThat(months).isNotEmpty();
        Assertions.assertThat(toWeeks(months)).isNotEmpty().hasSize(1);
        assertThat(toDays(months)).isNotEmpty().hasSize(1);
        Day day = toDays(months).stream().findAny().orElseThrow(() -> new IllegalStateException("Impossible"));
        assertThat(day.getDayOfMonth()).isEqualTo(1);
        assertThat(day.getDayOfWeek()).isEqualTo(2);
        assertThat(months).containsOnly(new BusinessMonth(2016, 11));
    }

    @Test
    public void findDaysOff_shouldFindOneWithinInterval() {
        Set<BusinessMonth> months = retrieveCalendarService.findDaysOff(LocalDate.of(2016, 10, 30), LocalDate.of(2016, 11, 2));
        assertThat(months).isNotEmpty();
        Assertions.assertThat(toWeeks(months)).isNotEmpty().hasSize(1);
        assertThat(toDays(months)).isNotEmpty().hasSize(1);
        Day day = toDays(months).stream().findAny().orElseThrow(() -> new IllegalStateException("Impossible"));
        assertThat(day.getDayOfMonth()).isEqualTo(1);
        assertThat(day.getDayOfWeek()).isEqualTo(2);
        assertThat(months).containsOnly(new BusinessMonth(2016, 11));
    }

    @Test
    public void findDaysOff_shouldFindMultipleDaysAndWeeksWithinSingleMonth() {
        Set<BusinessMonth> months = retrieveCalendarService.findDaysOff(LocalDate.of(2016, 11, 1), LocalDate.of(2016, 11, 30));
        Assertions.assertThat(toWeeks(months)).isNotEmpty().hasSize(2);
        assertThat(toDays(months)).isNotEmpty().hasSize(2).extracting(Day::getDayOfMonth).contains(1, 11);
        assertThat(months).containsOnly(new BusinessMonth(2016, 11));
    }

    @Test
    public void findDaysOff_shouldFindMultipleMonths() {
        Set<BusinessMonth> months = retrieveCalendarService.findDaysOff(LocalDate.of(2017, 4, 1), LocalDate.of(2017, 6, 1));
        assertThat(months).isNotEmpty().hasSize(2);
        assertThat(months).contains(new BusinessMonth(2017, 4), new BusinessMonth(2017, 5));
        Assertions.assertThat(toWeeks(months)).isNotEmpty().hasSize(3);
        assertThat(toDays(months)).isNotEmpty().hasSize(4).extracting(Day::getDayOfMonth).contains(1, 2, 3, 1);
    }

    @Test
    public void findDaysOff_shouldFindMultipleDaysWithinSingleWeek() {
        Set<BusinessMonth> months = retrieveCalendarService.findDaysOff(LocalDate.of(2017, 4, 1), LocalDate.of(2017, 4, 2));
        Assertions.assertThat(toWeeks(months)).isNotEmpty().hasSize(1);
        assertThat(toDays(months)).isNotEmpty().hasSize(2).extracting(Day::getDayOfMonth).contains(1, 2);
        assertThat(months).containsOnly(new BusinessMonth(2017, 4));
    }

    @Test
    public void findDaysOff_shouldFindMultipleDaysWithinCouplesWeeks() {
        Set<BusinessMonth> months = retrieveCalendarService.findDaysOff(LocalDate.of(2017, 4, 1), LocalDate.of(2017, 4, 3));
        Assertions.assertThat(toWeeks(months)).isNotEmpty().hasSize(2);
        assertThat(toDays(months)).isNotEmpty().hasSize(3).extracting(Day::getDayOfMonth).contains(1, 2, 3);
        assertThat(months).containsOnly(new BusinessMonth(2017, 4));
    }

    private Set<Week> toWeeks(Set<BusinessMonth> daysOff) {
        return daysOff.stream()
                .flatMap(b -> b.getWeeks().stream())
                .collect(Collectors.toSet());
    }

    private Set<Day> toDays(Set<BusinessMonth> daysOff) {
        return toWeeks(daysOff).stream()
                .flatMap(w -> w.getDays().stream())
                .collect(Collectors.toSet());
    }

}