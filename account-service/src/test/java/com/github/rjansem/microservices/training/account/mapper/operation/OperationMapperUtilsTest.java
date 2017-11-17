package com.github.rjansem.microservices.training.account.mapper.operation;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author aazzerrifi
 */
public class OperationMapperUtilsTest {

    OperationMapperUtils operationMapperUtils;

    @Test
    public void creditDebitIndicator_DBIT() throws Exception {
        new OperationMapperUtils();
        assertEquals(operationMapperUtils.creditDebitIndicator(new BigDecimal("-3")), "DBIT");
    }

    @Test
    public void creditDebitIndicator_CRDT() throws Exception {
        assertEquals(operationMapperUtils.creditDebitIndicator(new BigDecimal("3")), "CRDT");
    }

}