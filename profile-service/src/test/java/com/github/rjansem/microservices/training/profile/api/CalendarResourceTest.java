package com.github.rjansem.microservices.training.profile.api;

import com.github.rjansem.microservices.training.commons.testing.AbstractResourceTests;
import com.github.rjansem.microservices.training.commons.testing.AssertionConstants;
import com.github.rjansem.microservices.training.profile.service.RetrieveCalendarService;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Tests associés à la ressource {@link CalendarResource}
 *
 * @author mbouhamyd
 */
@Ignore
public class CalendarResourceTest extends AbstractResourceTests {

    private static final String START_PARAM = "start";

    private static final String END_PARAM = "end";

    @Mock
    private RetrieveCalendarService retrieveCalendarService;

    @Test
    public void findDaysOff_shouldFailCuzInvalidDateFormat() throws Exception {
        realMvc.perform(get(ApiConstants.RETRIEVE_CALENDAR).accept(MediaType.APPLICATION_JSON)
                .param(START_PARAM, "2016-20-10")
                .param(END_PARAM, "2016-10-40"))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST);
    }

    @Test
    public void findDaysOff_shouldFailCuzInvalidInterval() throws Exception {
        realMvc.perform(get(ApiConstants.RETRIEVE_CALENDAR).accept(MediaType.APPLICATION_JSON)
                .param(START_PARAM, "2016-10-10")
                .param(END_PARAM, "2016-09-10"))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST);
    }

    @Test
    public void findDaysOff_shouldNotFind() throws Exception {
        realMvc.perform(get(ApiConstants.RETRIEVE_CALENDAR).accept(MediaType.APPLICATION_JSON)
                .param(START_PARAM, "2016-10-01")
                .param(END_PARAM, "2016-10-01"))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_EMPTY);
    }

    @Test
    public void findDaysOff_shouldFindOneDay() throws Exception {
        realMvc.perform(get(ApiConstants.RETRIEVE_CALENDAR)
                .accept(MediaType.APPLICATION_JSON)
                .param(START_PARAM, "2016-11-01")
                .param(END_PARAM, "2016-11-01"))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.[*]", hasSize(1)))
                .andExpect(jsonPath("$.[0].year", is(2016)))
                .andExpect(jsonPath("$.[0].month", is(11)))
                .andExpect(jsonPath("$.[0].weeks[*]", hasSize(1)))
                .andExpect(jsonPath("$.[0].weeks[0].days", hasSize(1)))
                .andExpect(jsonPath("$.[0].weeks[0].days[0].dayOfMonth", is(1)))
                .andExpect(jsonPath("$.[0].weeks[0].days[0].dayOfWeek", is(2)))
                .andExpect(jsonPath("$.[0].weeks[0].days[0].currentBusinessDay", is(false)));
    }

    @Test
    public void findDaysOff_shouldFindMultipleDaysAndWeeks() throws Exception {
        realMvc.perform(get(ApiConstants.RETRIEVE_CALENDAR).accept(MediaType.APPLICATION_JSON)
                .param(START_PARAM, "2016-11-01")
                .param(END_PARAM, "2016-11-11"))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.[*]", hasSize(1)))
                .andExpect(jsonPath("$.[0].year", is(2016)))
                .andExpect(jsonPath("$.[0].month", is(11)))
                .andExpect(jsonPath("$.[*].weeks[*]", hasSize(2)))
                .andExpect(jsonPath("$.[*].weeks[*].days[*]", hasSize(2)))
                .andExpect(jsonPath("$.[*].weeks[*].days[*].dayOfMonth", containsInAnyOrder(1, 11)))
                .andExpect(jsonPath("$.[*].weeks[*].days[*].dayOfWeek", containsInAnyOrder(2, 5)));
    }

    @Test
    public void findDaysOff_shouldFindMultipleDaysWeeksAndMonths() throws Exception {
        realMvc.perform(get(ApiConstants.RETRIEVE_CALENDAR).accept(MediaType.APPLICATION_JSON)
                .param(START_PARAM, "2016-11-01")
                .param(END_PARAM, "2016-12-31"))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.[*]", hasSize(2)))
                .andExpect(jsonPath("$.[*].year", containsInAnyOrder(2016, 2016)))
                .andExpect(jsonPath("$.[*].month", containsInAnyOrder(11, 12)))
                .andExpect(jsonPath("$.[*].weeks[*]", hasSize(3)))
                .andExpect(jsonPath("$.[*].weeks[*].days[*]", hasSize(3)))
                .andExpect(jsonPath("$.[*].weeks[*].days[*].dayOfMonth", containsInAnyOrder(1, 11, 25)))
                .andExpect(jsonPath("$.[*].weeks[*].days[*].dayOfWeek", containsInAnyOrder(2, 5, 7)));
    }

    @Override
    public Object getMockResource() {
        return new CalendarResource(retrieveCalendarService);
    }
}