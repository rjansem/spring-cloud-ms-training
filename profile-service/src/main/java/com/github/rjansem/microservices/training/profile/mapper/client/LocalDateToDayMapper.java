package com.github.rjansem.microservices.training.profile.mapper.client;


import com.github.rjansem.microservices.training.profile.domain.calendar.Day;

import java.time.LocalDate;

/**
 * Mapper transformant une LocalDate en Day
 *
 * @author mbouhamyd
 */
public class LocalDateToDayMapper {

    public Day map(LocalDate input) {
        boolean isToday = false;
        if (LocalDate.now().equals(input)) {
            isToday = true;
        }
       return new Day(input.getDayOfMonth(), input.getDayOfWeek().getValue(), isToday);
    }
}
