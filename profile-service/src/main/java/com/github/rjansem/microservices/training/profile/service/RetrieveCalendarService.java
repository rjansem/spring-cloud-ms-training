package com.github.rjansem.microservices.training.profile.service;

import com.github.rjansem.microservices.training.profile.client.CalendarClient;
import com.github.rjansem.microservices.training.profile.domain.calendar.BusinessMonth;
import com.github.rjansem.microservices.training.profile.domain.calendar.Day;
import com.github.rjansem.microservices.training.profile.domain.calendar.Week;
import com.github.rjansem.microservices.training.profile.mapper.client.LocalDateToDayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service qui récupére les jours fériés
 *
 * @author mbouhamyd
 * @author jntakpe
 */
@Service
public class RetrieveCalendarService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetrieveCalendarService.class);

    private CalendarClient calendarClient;

    private LocalDateToDayMapper localDateToDayMapper = new LocalDateToDayMapper();

    public RetrieveCalendarService(CalendarClient calendarClient) {
        this.calendarClient = calendarClient;
    }

    public Set<BusinessMonth> findDaysOff(LocalDate start, LocalDate end) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        LOGGER.info("Récupération des jours fériés entre le {} et le {}", start, end);
        return groupByMonths(daysOffBetween(start, end)).entrySet().stream()
                .peek(e -> e.getKey().setWeeks(mapToWeeks(e.getValue())))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private Set<LocalDate> daysOffBetween(LocalDate start, LocalDate end) {
        checkInterval(start, end);
        return calendarClient.findAllDaysOff().stream()
                .filter(isBetweenDates(start, end))
                .collect(Collectors.toSet());
    }

    private void checkInterval(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            LOGGER.info("La date de début {} est après la date de fin {}", start, end);
            throw new ValidationException(String.format("La date de début %s est après la date de fin %s", start, end));
        }
    }

    private Predicate<LocalDate> isBetweenDates(LocalDate start, LocalDate end) {
        return d -> d.isAfter(start.minusDays(1)) && d.isBefore(end.plusDays(1));
    }

    private Map<BusinessMonth, Set<LocalDate>> groupByMonths(Set<LocalDate> daysOff) {
        return daysOff.stream()
                .collect(Collectors.groupingBy(d -> new BusinessMonth(d.getYear(), d.getMonth().getValue()),
                        Collectors.mapping(Function.identity(), Collectors.toSet())));
    }

    private Set<Week> mapToWeeks(Set<LocalDate> daysOff) {
        Map<Integer, Set<Day>> weekDaysMap = daysOff.stream()
                .collect(Collectors.groupingBy(d -> d.get(WeekFields.ISO.weekOfMonth()),
                        Collectors.mapping(d -> localDateToDayMapper.map(d), Collectors.toSet())));
        return weekDaysMap.entrySet().stream()
                .map(e -> new Week(e.getValue()))
                .collect(Collectors.toSet());
    }

}

