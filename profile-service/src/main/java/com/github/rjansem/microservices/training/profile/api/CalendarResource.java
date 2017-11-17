package com.github.rjansem.microservices.training.profile.api;

import com.github.rjansem.microservices.training.profile.domain.calendar.BusinessMonth;
import com.github.rjansem.microservices.training.profile.service.RetrieveCalendarService;
import com.github.rjansem.microservices.training.profile.domain.calendar.BusinessMonth;
import com.github.rjansem.microservices.training.profile.service.RetrieveCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

/**
 * Ressource permettant de récupérer les jours fériés
 *
 * @author mbouhamyd
 */
@RestController
@RequestMapping(ApiConstants.RETRIEVE_CALENDAR)
public class CalendarResource {

    private final RetrieveCalendarService retrieveCalendarService;

    @Autowired
    public CalendarResource(RetrieveCalendarService retrieveCalendarService) {
        this.retrieveCalendarService = retrieveCalendarService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Set<BusinessMonth> retrieveHolidays(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return retrieveCalendarService.findDaysOff(start, end);
    }

}
