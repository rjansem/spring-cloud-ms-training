package com.github.rjansem.microservices.training.transaction.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.commons.testing.AbstractResourceTests;
import com.github.rjansem.microservices.training.commons.testing.AssertionConstants;
import com.github.rjansem.microservices.training.commons.testing.WithMockA1;
import com.github.rjansem.microservices.training.exception.CodeMessageErrorDTO;
import com.github.rjansem.microservices.training.exception.ListCodeMessageErrorDTO;
import com.github.rjansem.microservices.training.transaction.service.RetrieveABookService;
import com.github.rjansem.microservices.training.transaction.validator.AdressBookValidator;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Tests associés à la ressource {@link BeneficiariesResource}
 *
 * @author aazzerrifi
 */
public class BeneficiariesResourceTest extends AbstractResourceTests {

    @Mock
    private RetrieveABookService retrieveABookService;

    @Mock
    private AdressBookValidator adressBookValidator;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockA1
    public void findBeneficairesWithType() throws Exception {
    }

    @Test
    @WithMockA1
    public void sign_transaction_shouldFindEmptyCuzUnknownUser() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ServicesUris.API + ApiConstants.BENEFICIARIES)
                .param("transferType", "SEPA")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.[*]").isNotEmpty());
    }

    @Test
    @WithMockA1
    public void benef_No_SEPA() throws Exception {
        String code = "BAD_REQUEST";
        String label = "transferType attribute must be equal to SEPA";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(get(ServicesUris.API + ApiConstants.BENEFICIARIES)
                .param("transferType", "SEfdfPA")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST)
                .andExpect(content().json(err));
    }

    @Override
    public Object getMockResource() {
        return new BeneficiariesResource(retrieveABookService, adressBookValidator);
    }

}