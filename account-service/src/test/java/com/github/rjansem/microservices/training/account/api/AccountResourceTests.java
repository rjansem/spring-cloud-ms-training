package com.github.rjansem.microservices.training.account.api;

import com.github.rjansem.microservices.training.account.util.AccountTypeValidator;
import com.github.rjansem.microservices.training.account.service.AccountService;
import com.github.rjansem.microservices.training.account.service.CartesBancairesService;
import com.github.rjansem.microservices.training.account.service.RetrieveBalanceService;
import com.github.rjansem.microservices.training.account.service.RibService;
import com.github.rjansem.microservices.training.commons.testing.*;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static com.github.rjansem.microservices.training.commons.testing.UserConstants.UNKNOWN_RACINE;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Tests associés à la ressource gérant les comptes {@link AccountResource}
 *
 * @author jntakpe
 * @author aazzerrifi
 */
public class AccountResourceTests extends AbstractResourceTests {

    static final String ACCOUNT_TYPE = "accountType";

    static final String CREDITCARD_ID = "creditcardId";

    @Mock
    private AccountService mockAccountService;

    @Mock
    private RibService mockRibService;

    @Mock
    private RetrieveBalanceService mockRetrieveBalanceService;

    @Mock
    private CartesBancairesService mockCartesBancairesService;

    @Mock
    private AccountTypeValidator mockaccountTypeValidator;

    @Test
    @WithMockUnknown
    public void findClients_shouldFindEmptyCuzUnknownUser() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.CLIENTS_TECHNICAL_BY_ID + ApiConstants.RIB, UNKNOWN_RACINE)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_NOT_FOUND)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY);
    }

    @Test
    @WithMockA1
    public void findClients_shouldFailCuzRacineDontBelongToCurrentUser() throws Exception {
        realMvc.perform(get(ApiConstants.CLIENTS_TECHNICAL_BY_ID + ApiConstants.RIB, UNKNOWN_RACINE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(AssertionConstants.STATUS_BAD_REQUEST);
                //.andExpect(AssertionConstants.CONTENT_TYPE_JSON)
        //.andExpect(content().string("La racine '" + UNKNOWN_RACINE + "' n'appartient pas à l'utilisateur connecté"));
    }

    @Test
    @WithMockA4
    public void findRibs_shouldFindRibsRacineA4R1() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.CLIENTS_TECHNICAL_BY_ID + ApiConstants.RIB, "A4R1")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.accounts.[*].id", contains("FR7630788001002504780000342", "FR7630788001002504780000343", "FR7630788001002504780000341")))
                .andExpect(jsonPath("$.accounts.[0].accountNumber").value("10106895245"))
                .andExpect(jsonPath("$.accounts.[0].iban").value("FR7630788001002504780000342"))
                .andExpect(jsonPath("$.accounts.[0].bankName").value("Banque Neuflize OBC - 00100"))
                .andExpect(jsonPath("$.accounts.[0].clientLastName").value("AMIOT FABRICE"))
                .andExpect(jsonPath("$.accounts.[0].bic").value("NSMBFRPPXXX"));
    }

    @Test
    @WithMockA5
    public void findRibs_shouldFindRibsRacineA5R1() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.CLIENTS_TECHNICAL_BY_ID + ApiConstants.RIB, "A5R1")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.accounts.[*].id", contains("FR7630788001002504780000353", "FR7630788001002504780000351")))
                .andExpect(jsonPath("$.accounts.[*].accountNumber", contains("00000000003", "00000000001")))
                .andExpect(jsonPath("$.accounts.[*].iban", contains("FR7630788001002504780000353", "FR7630788001002504780000351")))
                .andExpect(jsonPath("$.accounts.[*].bankName", contains("Banque Neuflize OBC - 00100", "Banque Neuflize OBC - 00100")))
                .andExpect(jsonPath("$.accounts.[*].clientLastName", contains("AMIOT FABRICE", "AMIOT FABRICE")))
                .andExpect(jsonPath("$.accounts.[*].bic", contains("NSMBFRPPXXX", "NSMBFRPPXXX")));
    }

    @Test
    @WithMockA1
    public void findRetrieveBalanceForAllAccounts_shouldOneAccount() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.CLIENTS_TECHNICAL_BY_ID + ApiConstants.RETRIEVE_BALANCE_ALL, "A1R1")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.content.[*]", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].id").value("FR7630788001002504780000311"))
                .andExpect(jsonPath("$.content.[0].type").value("Compte courant"))
                .andExpect(jsonPath("$.content.[0].typeId").value("DAV"))
                .andExpect(jsonPath("$.content.[0].subtype").value("COMPTE A VUE"))
                .andExpect(jsonPath("$.content.[0].subtypeId").value("CAV"))
                .andExpect(jsonPath("$.content.[0].accountNumber").value("00000000001"))
                .andExpect(jsonPath("$.content.[0].iban").value("FR7630788001002504780000311"))
                .andExpect(jsonPath("$.content.[0].creditcards.[*].creditcardId").value("111"))
                .andExpect(jsonPath("$.content.[0].creditcards.[*].cardNumber").value("100250478XXXXXX1"))
                .andExpect(jsonPath("$.content.[0].creditcards.[*].cardBalance").value(-14.000000))
                .andExpect(jsonPath("$.content.[0].creditcards.[*].cardCurrency").value("EUR"))
                .andExpect(jsonPath("$.content.[0].creditcards.[*].date").value("2016-12-01"))
                .andExpect(jsonPath("$.content.[0].creditcards.[*].cardBalanceDate").value("2017-11-11T00:00:00.000Z"));
    }

    @Test
    @WithMockA1
    public void findRetrieveBalanceForOneAccount_shouldFindOneAcountWithTwoCards() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.CLIENTS_TECHNICAL_BY_ID + ApiConstants.RETRIEVE_BALANCE_ACCOUNT, "A1R1", "FR7630788001002504780000311")
                .param(ACCOUNT_TYPE, "DAV")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.id").value("FR7630788001002504780000311"))
                .andExpect(jsonPath("$.type").value("Compte courant"))
                .andExpect(jsonPath("$.typeId").value("DAV"))
                .andExpect(jsonPath("$.subtype").value("COMPTE A VUE"))
                .andExpect(jsonPath("$.subtypeId").value("CAV"))
                .andExpect(jsonPath("$.accountNumber").value("00000000001"))
                .andExpect(jsonPath("$.iban").value("FR7630788001002504780000311"))
                .andExpect(jsonPath("$.creditcards.[*].creditcardId").value("111"))
                .andExpect(jsonPath("$.creditcards.[*].cardNumber").value("100250478XXXXXX1"))
                .andExpect(jsonPath("$.creditcards.[*].cardBalance").value(-14.000000))
                .andExpect(jsonPath("$.creditcards.[*].cardCurrency").value("EUR"))
                .andExpect(jsonPath("$.creditcards.[*].date").value("2016-12-01"))
                .andExpect(jsonPath("$.creditcards.[*].cardBalanceDate").value("2017-11-11T00:00:00.000Z"));

    }

    @Test
    @WithMockA1
    public void findRetrieveBalance_shouldFindTwoCards() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.CLIENTS_TECHNICAL_BY_ID + ApiConstants.RETRIEVE_BALANCE_ACCOUNT, "A1R1", "FR7630788001002504780000311")
                .param(ACCOUNT_TYPE, "DAV")
                .param(CREDITCARD_ID, "ALL")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.creditcards.[*].creditcardId", contains("111")))
                .andExpect(jsonPath("$.creditcards.[*].cardNumber", contains("100250478XXXXXX1")))
                .andExpect(jsonPath("$.creditcards.[*].cardName", contains("NADAL CHRISTIAN")))
                .andExpect(jsonPath("$.creditcards.[*].cardCurrency", contains("EUR")))
                .andExpect(jsonPath("$.creditcards.[*].cardBalance", contains(-14.000000)))
                .andExpect(jsonPath("$.creditcards.[*].date", contains("2016-12-01")))
                .andExpect(jsonPath("$.creditcards.[*].cardBalanceDate", contains("2017-11-11T00:00:00.000Z")));
    }

    @Test
    @WithMockA1
    public void findRetrieveBalance_shouldFindCard() throws Exception {
        MvcResult mvcResult = realMvc.perform(get(ApiConstants.CLIENTS_TECHNICAL_BY_ID + ApiConstants.RETRIEVE_BALANCE_ACCOUNT, "A1R1", "FR7630788001002504780000311")
                .param(ACCOUNT_TYPE, "DAV")
                .param(CREDITCARD_ID, "111")
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        realMvc.perform(asyncDispatch(mvcResult))
                .andExpect(AssertionConstants.STATUS_OK)
                .andExpect(AssertionConstants.CONTENT_TYPE_JSON)
                .andExpect(AssertionConstants.ARRAY_NOT_EMPTY)
                .andExpect(jsonPath("$.[*]", hasSize(1)))
                .andExpect(jsonPath("$.creditcards[0].creditcardId").value("111"))
                .andExpect(jsonPath("$.creditcards[0].cardNumber").value("100250478XXXXXX1"))
                .andExpect(jsonPath("$.creditcards[0].cardName").value("NADAL CHRISTIAN"))
                .andExpect(jsonPath("$.creditcards[0].cardCurrency").value("EUR"))
                .andExpect(jsonPath("$.creditcards[0].cardBalance").value(-14.0))
                .andExpect(jsonPath("$.creditcards[0].date").value("2016-12-01"))
                .andExpect(jsonPath("$.creditcards[0].cardBalanceDate").value("2017-11-11T00:00:00.000Z"));
    }


    @Override
    public Object getMockResource() {
        return new AccountResource(mockAccountService, mockRibService, mockRetrieveBalanceService, mockCartesBancairesService, mockaccountTypeValidator);
    }
}
