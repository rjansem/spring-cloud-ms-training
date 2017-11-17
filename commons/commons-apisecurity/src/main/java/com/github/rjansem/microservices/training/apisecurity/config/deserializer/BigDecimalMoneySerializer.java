package com.github.rjansem.microservices.training.apisecurity.config.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Serializer de {@link BigDecimal} avec 2 chiffres avec la virgule
 *
 * @author jntakpe
 */
public class BigDecimalMoneySerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        BigDecimal scaleVal = BigDecimal.ZERO.equals(value) ? BigDecimal.ZERO : value.setScale(2, BigDecimal.ROUND_HALF_UP);
        gen.writeNumber(scaleVal);
    }

}