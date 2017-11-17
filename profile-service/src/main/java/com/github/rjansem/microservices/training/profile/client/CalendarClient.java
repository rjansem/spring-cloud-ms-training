package com.github.rjansem.microservices.training.profile.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Récupère la liste des jours fériés
 *
 * @author mbouhamyd
 * @author jntakpe
 */
@Component
public class CalendarClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarClient.class);

    private static final String FILE_SEPARATOR = ";";

    private final ResourceLoader resourceLoader;

    @Value("${business.daysoff.path:classpath:/daysoff-list.txt}")
    private String filePath;

    @Autowired
    public CalendarClient(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Set<LocalDate> findAllDaysOff() {
        return Arrays.stream(loadDaysOff().stream().collect(Collectors.joining()).split(FILE_SEPARATOR))
                .map(LocalDate::parse)
                .collect(Collectors.toSet());
    }

    private List<String> loadDaysOff() {
        try {
            return Files.readAllLines(resourceLoader.getResource(filePath).getFile().toPath());
        } catch (IOException e) {
            LOGGER.warn("Impossible de charger la liste des jours fériés depuis le fichier {}", filePath);
            return Collections.emptyList();
        }
    }

}
