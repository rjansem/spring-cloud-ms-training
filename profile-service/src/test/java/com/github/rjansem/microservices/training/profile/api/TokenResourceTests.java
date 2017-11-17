package com.github.rjansem.microservices.training.profile.api;

import com.github.rjansem.microservices.training.commons.testing.*;
import com.github.rjansem.microservices.training.profile.service.TokenService;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Tests associés à la ressource {@link TokenResource}
 *
 * @author jntakpe
 */
public class TokenResourceTests extends AbstractResourceTests {

    @Mock
    private TokenService tokenService;

    @Test
    @WithMockUnknown
    public void findTokenInfos_shouldReturn404CuzUnknownUser() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.TOKEN).accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(AssertionConstants.STATUS_NOT_FOUND)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON);
    }

    @Test
    @WithMockA1
    public void findTokenInfos_shouldFindFullToken() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.TOKEN).accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.OBJECT_EXISTS)
                .andExpect(jsonPath("$.login").exists())
                .andExpect(jsonPath("$.login").value("A1"))
                .andExpect(jsonPath("$.racines").isArray())
                .andExpect(jsonPath("$.racines").isNotEmpty())
                .andExpect(jsonPath("$.racines", hasSize(1)))
                .andExpect(jsonPath("$.racines[0]", is("A1R1")))
                .andExpect(jsonPath("$.authorities").isArray())
                .andExpect(jsonPath("$.authorities").isNotEmpty())
                .andExpect(jsonPath("$.authorities", hasSize(2)))
                .andExpect(jsonPath("$.authorities", hasItems("consultations", "transactions")));
    }

    @Test
    @WithMockA5
    public void findTokenInfos_shouldFindFullTokenWithCoupleRacines() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.TOKEN).accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.OBJECT_EXISTS)
                .andExpect(jsonPath("$.login").exists())
                .andExpect(jsonPath("$.login").value("A5"))
                .andExpect(jsonPath("$.racines").isArray())
                .andExpect(jsonPath("$.racines").isNotEmpty())
                .andExpect(jsonPath("$.racines", hasSize(2)))
                .andExpect(jsonPath("$.racines", hasItems("A5R1", "A5R2")))
                .andExpect(jsonPath("$.authorities").isArray())
                .andExpect(jsonPath("$.authorities").isNotEmpty())
                .andExpect(jsonPath("$.authorities", hasSize(2)))
                .andExpect(jsonPath("$.authorities", hasItems("consultations", "transactions")));
    }

    @Override
    public Object getMockResource() {
        return tokenService;
    }
}
