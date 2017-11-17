package com.github.rjansem.microservices.training.transaction.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rjansem.microservices.training.apisecurity.ServicesUris;
import com.github.rjansem.microservices.training.commons.testing.AbstractResourceTests;
import com.github.rjansem.microservices.training.commons.testing.AssertionConstants;
import com.github.rjansem.microservices.training.commons.testing.WithMockA1;
import com.github.rjansem.microservices.training.exception.CodeMessageErrorDTO;
import com.github.rjansem.microservices.training.exception.ListCodeMessageErrorDTO;
import com.github.rjansem.microservices.training.transaction.domain.pbi.PaymentOrder;
import com.github.rjansem.microservices.training.transaction.domain.pbi.TransactionInfo;
import com.github.rjansem.microservices.training.transaction.domain.pbi.account.TransactionAccount;
import com.github.rjansem.microservices.training.transaction.service.MeansSignatureService;
import com.github.rjansem.microservices.training.transaction.service.OrderService;
import com.github.rjansem.microservices.training.transaction.service.RetrieveABookService;
import com.github.rjansem.microservices.training.transaction.service.TransactionService;
import com.github.rjansem.microservices.training.transaction.validator.PaymentOrderValidator;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Tests associés à la ressource gérant les comptes {@link TransactionResource}
 *
 * @author aazzerrifi
 */
public class TransactionResourceTests extends AbstractResourceTests {


    @Mock
    private RetrieveABookService mockRetrieveABookService;

    @Mock
    private TransactionService mockTransactionService;

    @Mock
    private OrderService mockOrderService;

    @Mock
    private PaymentOrderValidator paymentOrderValidator;

    @Mock
    private MeansSignatureService meansSignatureService;


    @Autowired
    private ObjectMapper mapper;

    @Test
    @WithMockA1
    public void payment_order_shouldFailCuzRacineDontBelongToCurrentUser() throws Exception {
        String json = mapper.writeValueAsString(getPaymentOrder_INPUT_200000000());
        String code = "moyen.plafond.cumule.atteint.109";
        String label = "Le montant saisi dépasse le plafond maximum autorisé sur votre espace privé. Pour plus de précisions, " +
                "veuillez consulter la rubrique \"Aide\". ";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(post(ServicesUris.API + ApiConstants.PAYMENT_ORDER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(content().string(err))
                .andExpect(content().json(err));
    }

    @Test
    @WithMockA1
    public void payment_order_NoPaymentMode() throws Exception {
        String json = mapper.writeValueAsString(getPaymentOrder_INPUT_NO_PayMod());
        String code = "BAD_REQUEST";
        String label = "paymentMode attribute must be equal to SINGLE";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(post(ServicesUris.API + ApiConstants.PAYMENT_ORDER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST)
                .andExpect(content().string(err))
                .andExpect(content().json(err));
    }

    @Test
    @WithMockA1
    public void payment_order_PaymentMode_XX() throws Exception {
        String json = mapper.writeValueAsString(getPaymentOrder_INPUT_PayMod_XX());
        String code = "BAD_REQUEST";
        String label = "paymentMode attribute must be equal to SINGLE";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(post(ServicesUris.API + ApiConstants.PAYMENT_ORDER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST)
                .andExpect(content().string(err))
                .andExpect(content().json(err));
    }

    @Test
    @WithMockA1
    public void payment_order_Amount_0() throws Exception {
        String json = mapper.writeValueAsString(getPaymentOrder_INPUT_NULL());
        String code = "BAD_REQUEST";
        String label = "instructedAmount ne peut pas être nul";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(post(ServicesUris.API + ApiConstants.PAYMENT_ORDER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST)
                .andExpect(content().string(err))
                .andExpect(content().json(err));
    }

    @Test
    @WithMockA1
    public void payment_order_ExecutionDateTime_NULL() throws Exception {
        String json = mapper.writeValueAsString(getPaymentOrder_ExecutionDateTime_NULL());
        String code = "BAD_REQUEST";
        String label = "executionDateTime ne peut pas être nul";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(post(ServicesUris.API + ApiConstants.PAYMENT_ORDER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST)
                .andExpect(content().string(err))
                .andExpect(content().json(err));
    }

    @Test
    @WithMockA1
    public void payment_order_InstructedCurrency_NULL() throws Exception {
        String json = mapper.writeValueAsString(getPaymentOrder_InstructedCurrency_NULL());
        String code = "BAD_REQUEST";
        String label = "instructedCurrency ne peut pas être nul";
        CodeMessageErrorDTO codeMessageErrorDTO = new CodeMessageErrorDTO(code, label);
        String err = mapper.writeValueAsString(new ListCodeMessageErrorDTO(Arrays.asList(codeMessageErrorDTO)));
        realMvc.perform(post(ServicesUris.API + ApiConstants.PAYMENT_ORDER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST)
                .andExpect(content().string(err))
                .andExpect(content().json(err));
    }

    @Test
    @WithMockA1
    public void issuanceOrder() throws Exception {
        String json = mapper.writeValueAsString(getPaymentOrder_INPUT());
        MvcResult mvcResult = realMvc.perform(post(ServicesUris.API + ApiConstants.PAYMENT_ORDER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.[*]", hasSize(3)))
                .andExpect(jsonPath("$.[*].paymentOrderList").isNotEmpty())
                .andExpect(jsonPath("$.[*].paymentOrderList.[*].id", contains("WBSCT3_O_58501245c5438a1c_0")))
                .andExpect(jsonPath("$.[*].paymentOrderList.[*].status").value("4096"))
                .andExpect(jsonPath("$.[*].paymentOrderList.[*].debitAccount.id").value("FR7630788001008888888888830"))
                .andExpect(jsonPath("$.[*].paymentOrderList.[*].debitAccount.type").value("Compte courant"))
                .andExpect(jsonPath("$.[*].paymentOrderList.[*].debitAccount.typeId").value("DAV"))
                .andExpect(jsonPath("$.[*].paymentOrderList.[*].creditAccount.id").value("FR7630003001001111111111132"))
                .andExpect(jsonPath("$.[*].paymentOrderList.[*].creditAccount.beneficiaryLastName").value("Benef 1"))
                .andExpect(jsonPath("$.[*].paymentOrderList.[*].transactionInfo.memo").value("Loyer"))
                .andExpect(jsonPath("$.[*].paymentOrderList.[*].transactionInfo.instructedCurrency").value("EUR"));
    }

    private PaymentOrder getPaymentOrder_INPUT() {
        PaymentOrder input = new PaymentOrder();
        TransactionAccount debitAccount = new TransactionAccount();
        debitAccount.setId("FR7630788001002504780000351");
        debitAccount.setIban("FR7630788001002504780000351");
        TransactionAccount creditAccount = new TransactionAccount();
        creditAccount.setId("101301");
        creditAccount.setIban("FR7630003001001111111111101");
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setInstructedAmount(new BigDecimal("200.00"));
        transactionInfo.setExecutionDateTime("2016-12-14T10:21:15.223Z");
        transactionInfo.setMemo("Merci mon ami");
        transactionInfo.setPaymentMode("SINGLE");
        transactionInfo.setUrgentTransfer(false);
        transactionInfo.setInstructedCurrency("EUR");
        input.setDebitAccount(debitAccount);
        input.setCreditAccount(creditAccount);
        input.setTransactionInfo(transactionInfo);
        return input;
    }

    private PaymentOrder getPaymentOrder_INPUT_NO_PayMod() {
        PaymentOrder input = new PaymentOrder();
        TransactionAccount debitAccount = new TransactionAccount();
        debitAccount.setId("FR7630788001002504780000351");
        debitAccount.setIban("FR7630788001002504780000351");
        TransactionAccount creditAccount = new TransactionAccount();
        creditAccount.setId("101301");
        creditAccount.setIban("FR7630003001001111111111101");
        TransactionInfo transactionInfo = new TransactionInfo();
        transactionInfo.setInstructedAmount(new BigDecimal("200.00"));
        transactionInfo.setExecutionDateTime("2016-12-14T10:21:15.223Z");
        transactionInfo.setMemo("Merci mon ami");
        transactionInfo.setUrgentTransfer(false);
        transactionInfo.setInstructedCurrency("EUR");
        input.setDebitAccount(debitAccount);
        input.setCreditAccount(creditAccount);
        input.setTransactionInfo(transactionInfo);
        return input;
    }

    private PaymentOrder getPaymentOrder_INPUT_PayMod_XX() {
        PaymentOrder input = getPaymentOrder_INPUT();
        input.getTransactionInfo().setPaymentMode("XX");
        return input;
    }

    private PaymentOrder getPaymentOrder_INPUT_200000000() {
        PaymentOrder input = getPaymentOrder_INPUT();
        input.getTransactionInfo().setInstructedAmount(new BigDecimal("2000000.00"));
        input.getCreditAccount().setId("FR7630003001001111111111101");
        return input;
    }

    private PaymentOrder getPaymentOrder_NULL() {
        PaymentOrder input = new PaymentOrder();
        input.setCreditAccount(null);
        input.setDebitAccount(null);
        input.setTransactionInfo(null);
        return input;
    }

    private PaymentOrder getPaymentOrder_INPUT_NULL() {
        PaymentOrder input = getPaymentOrder_INPUT();
        input.getTransactionInfo().setInstructedAmount(null);
        return input;
    }

    private PaymentOrder getPaymentOrder_ExecutionDateTime_NULL() {
        PaymentOrder input = getPaymentOrder_INPUT();
        input.getTransactionInfo().setExecutionDateTime(null);
        return input;
    }

    private PaymentOrder getPaymentOrder_InstructedCurrency_NULL() {
        PaymentOrder input = getPaymentOrder_INPUT();
        input.getTransactionInfo().setInstructedCurrency(null);
        return input;
    }

    @Override
    public Object getMockResource() {

        return new TransactionResource(mockOrderService, paymentOrderValidator, meansSignatureService);
    }
}
