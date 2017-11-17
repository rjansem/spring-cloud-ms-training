package com.github.rjansem.microservices.training.logstash;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests de la gestion des logs par logstash
 * Permet d'écrire des logs dans les fichiers de logs
 *
 * @author rjansem
 */
public class LogStashTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogStashTests.class);

    @Test
    public void testLogin() {
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "COCKZAO72");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "EQOEBW39");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "LACH011");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "BEBERT69");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "COCKZAO72");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "KAKID56");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "SDFGSDF");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "SDFGSDF");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "EV240304");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "LACH011");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "COCKZAO72");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "LAURE075");
        LOGGER.info("[ANALYTICS] [{}] [Profile] [TokenInfo]", "COCKZAO72");
    }

    @Test
    public void testNewTransfer() {
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "COCKZAO72", "13072409724", 1000, "SINGLE", "EUR", true);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "EQOEBW39", "45674783724", 6952, "SINGLE", "EUR", false);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "LACH011", "10674783457", 454, "SINGLE", "EUR", true);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "BEBERT69", "11674783774", 6900, "SINGLE", "EUR", true);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "EV240304", "45674522457", 457, "SINGLE", "EUR", false);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "LAURE075", "45674783724", 6983, "SINGLE", "EUR", true);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "KAKID56", "78974745724", 1568, "SINGLE", "EUR", false);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "TOASTER", "54645643798", 7988, "SINGLE", "EUR", false);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "OSEFDUD", "45674783758", 6900, "SINGLE", "EUR", true);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "SDFSGGS", "87674783721", 20, "SINGLE", "EUR", false);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "SDFGSDF", "35247783756", 789, "SINGLE", "EUR", false);
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [NewTransfer] transactionId={} totalAmount={} paymentMode={} currency={} internal={}", "54FDFDD", "45601247896", 98631, "SINGLE", "EUR", true);
    }

    @Test
    public void testSignByVkb() {
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "COCKZAO72", "13072409724");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "EQOEBW39", "45674783724");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "LACH011", "10674783457");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "BEBERT69", "11674783774");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "COCKZAO72", "45674522457");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "KAKID56", "45674783724");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "SDFGSDF", "78974745724");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "SDFGSDF", "54645643798");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "EV240304", "45674783758");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "LACH011", "87674783721");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "COCKZAO72", "35247783756");
        LOGGER.info("[ANALYTICS] [{}] [Transaction] [SignByVkb] transactionId={}", "LAURE075", "45601247896");
    }

    @Test
    public void testRib() {
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "COCKZAO72");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "EQOEBW39");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "LACH011");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "BEBERT69");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "COCKZAO72");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "KAKID56");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "SDFGSDF");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "SDFGSDF");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "EV240304");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "LACH011");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "COCKZAO72");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "LAURE075");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "COCKZAO72");
        LOGGER.info("[ANALYTICS] [{}] [Account] [Rib]", "54FDFDD");
    }

    @Test
    public void dataFiller() {

    }
}
