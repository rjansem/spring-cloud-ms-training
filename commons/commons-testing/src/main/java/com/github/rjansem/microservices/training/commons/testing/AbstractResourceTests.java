package com.github.rjansem.microservices.training.commons.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Méthodes utilitaires pour les tests de ressources REST
 *
 * @author jntakpe
 */
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(stubs = "classpath:/stubs")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class AbstractResourceTests {

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc realMvc;

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public abstract Object getMockResource();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.realMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).alwaysDo(print()).build();
        this.mockMvc = MockMvcBuilders.standaloneSetup(getMockResource()).alwaysDo(print()).build();
    }

}
