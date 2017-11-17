package com.github.rjansem.microservices.training.transaction.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.commons.testing.AbstractResourceTests;
import com.github.rjansem.microservices.training.commons.testing.AssertionConstants;
import com.github.rjansem.microservices.training.commons.testing.WithMockA1;
import com.github.rjansem.microservices.training.exception.CodeMessageErrorDTO;
import com.github.rjansem.microservices.training.exception.ListCodeMessageErrorDTO;
import com.github.rjansem.microservices.training.transaction.domain.pbi.transaction.SignTransaction;
import com.github.rjansem.microservices.training.transaction.service.OrderService;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * Tests associés à la ressource {@link SignatureResourceTest}
 *
 * @author aazzerrifi
 */
public class SignatureResourceTest extends AbstractResourceTests {
    @Mock
    private OrderService mockOrderService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void signTransactionByKbv() throws Exception {
    }

    @Test
    @WithMockA1
    public void sign_transaction_shouldFindEmptyCuzUnknownUser() throws Exception {
        String json = mapper.writeValueAsString(getSignTransaction(1));
        MvcResult mvcResult = realMvc.perform(post(ServicesUris.API + ApiConstants.SIGN_PAYMENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.[*]").isNotEmpty())
                .andExpect(jsonPath("$.[*]", hasSize(1)))
                .andExpect(jsonPath("$.[*].[*].transactionId").value("WBSCT3_O_58501245c5438a1c_0"))
                .andExpect(jsonPath("$.[*].[*].status", contains("4096")));
    }


    @Test
    @WithMockA1
    public void payment_order_EncryptedPassword_NULL() throws Exception {
        String json = mapper.writeValueAsString(getSignTransaction_EncryptedPassword_NULL(1));
        String code = "BAD_REQUEST";
        String label = "encryptedPassword may not be null";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(post(ServicesUris.API + ApiConstants.SIGN_PAYMENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST)
                .andExpect(content().string(err))
                .andExpect(content().json(err));
    }

    @Test
    @WithMockA1
    public void payment_order_KeyboardIdentifier_NULL() throws Exception {
        String json = mapper.writeValueAsString(getSignTransaction_KeyboardIdentifier_NULL(1));
        String code = "BAD_REQUEST";
        String label = "keyboardIdentifier may not be null";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(post(ServicesUris.API + ApiConstants.SIGN_PAYMENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST)
                .andExpect(content().json(err));
    }

    @Test
    @WithMockA1
    public void payment_order_TransactionIds_NULL() throws Exception {
        String json = mapper.writeValueAsString(getSignTransaction_TransactionIds_NULL(1));
        String code = "BAD_REQUEST";
        String label = "transactionIds may not be empty";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(post(ServicesUris.API + ApiConstants.SIGN_PAYMENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST)
                .andExpect(content().string(err))
                .andExpect(content().json(err));
    }

    @Test
    @WithMockA1
    public void payment_order_TransactionIds_5() throws Exception {
        String json = mapper.writeValueAsString(getSignTransaction_TransactionIds_NULL(5));
        String code = "BAD_REQUEST";
        String label = "transactionIds may not be empty";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(post(ServicesUris.API + ApiConstants.SIGN_PAYMENT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST)
                .andExpect(content().string(err))
                .andExpect(content().json(err));
    }

    private SignTransaction getSignTransaction(int nbreTrans) {
        SignTransaction signTransaction = new SignTransaction();
        signTransaction.setEncryptedPassword("XXXXX");
        signTransaction.setKeyboardIdentifier("YYYYYY");
        String id = "WBSCT3_O_58501245c5438a1c_0";
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < nbreTrans; i++) strings.add(id);
        signTransaction.setTransactionIds(strings);
        return signTransaction;
    }

    private SignTransaction getSignTransaction_EncryptedPassword_NULL(int nbreTrans) {
        SignTransaction signTransaction = getSignTransaction(nbreTrans);
        signTransaction.setEncryptedPassword(null);
        return signTransaction;
    }

    private SignTransaction getSignTransaction_KeyboardIdentifier_NULL(int nbreTrans) {
        SignTransaction signTransaction = getSignTransaction(nbreTrans);
        signTransaction.setKeyboardIdentifier(null);
        return signTransaction;
    }

    private SignTransaction getSignTransaction_TransactionIds_NULL(int nbreTrans) {
        SignTransaction signTransaction = getSignTransaction(nbreTrans);
        signTransaction.setTransactionIds(null);
        return signTransaction;
    }
    @Override
    public Object getMockResource() {
        return new SignatureResource(mockOrderService);
    }
}