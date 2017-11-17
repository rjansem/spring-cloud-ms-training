package com.github.rjansem.microservices.training.transaction.api;

import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.commons.testing.AbstractResourceTests;
import com.github.rjansem.microservices.training.commons.testing.AssertionConstants;
import com.github.rjansem.microservices.training.commons.testing.WithMockA1;
import com.github.rjansem.microservices.training.transaction.service.AccountToDebitService;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by aazzerrifi on 17/03/2017.
 */
public class AccountToDebitResourceTest extends AbstractResourceTests {

    @Mock
    private AccountToDebitService accountToDebitService;

    @Test
    @WithMockA1
    public void findAccountToDebit() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ServicesUris.API + ApiConstants.ACCOUNT_TO_DEBIT)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.[*]").isNotEmpty());
    }

    @Override
    public Object getMockResource() {
        return new AccountToDebitResource(accountToDebitService);
    }

}