package com.github.rjansem.microservices.training.profile.api;

import com.github.rjansem.microservices.training.commons.testing.*;
import com.github.rjansem.microservices.training.profile.service.ClientService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Tests associés à la ressource {@link ClientResource}
 *
 * @author jntakpe
 */
public class ClientResourceTests extends AbstractResourceTests {

    @Mock
    private ClientService clientService;

    @Test
    @WithMockUnknown
    public void findClients_shouldFindEmptyCuzUnknownUser() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.CLIENT).accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_EMPTY)
                .andExpect(jsonPath("$.[*]").isEmpty())
                .andExpect(jsonPath("$.[*].clientId").isEmpty());
    }

    @Test
    @WithMockA1
    public void findClients_shouldFindOneClient() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.CLIENT).accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.clients.[*].clientTechnicalId", Matchers.contains("A1R1")))
                .andExpect(jsonPath("$.clients.[*].lastName", Matchers.contains("REVOL YVES")))
                .andExpect(jsonPath("$.clients.[*].mandateFlag", Matchers.containsInAnyOrder(false)))
                .andExpect(jsonPath("$.clients.[*].accounts").isNotEmpty())
                .andExpect(jsonPath("$.clients.[*].accounts.[*].accountNumber", Matchers.contains("00000000001", "00000000002")));
    }

    @Test
    @WithMockA5
    public void findClients_shouldFindTwoClient() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.CLIENT).accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.[*]", hasSize(2)))
                .andExpect(jsonPath("$.clients.[*].clientTechnicalId", Matchers.contains("A5R1")))
                .andExpect(jsonPath("$.clients.[*].lastName", Matchers.contains("REVOL YVES")))
                .andExpect(jsonPath("$.clients.[*].mandateFlag", Matchers.containsInAnyOrder(false)))
                .andExpect(jsonPath("$.clients.[*].accounts").isNotEmpty())
                .andExpect(jsonPath("$.clients.[*].accounts.[*].accountNumber", Matchers.contains("00000000001", "00000000002")));
    }

    @Override
    public Object getMockResource() {

        return new ClientResource(clientService);
    }
}
