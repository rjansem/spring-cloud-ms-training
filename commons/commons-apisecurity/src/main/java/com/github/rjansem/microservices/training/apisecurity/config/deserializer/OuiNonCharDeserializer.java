package com.github.rjansem.microservices.training.apisecurity.config.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Déserializer Jackson permettant de convertir les 'O' et 'N' en boolean
 *
 * @author rjansem
 */
public class OuiNonCharDeserializer extends JsonDeserializer<Boolean> {

    private static final String OUI_CHAR = "O";

    private static final String NON_CHAR = "N";

    @Override
    public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken currentToken = p.getCurrentToken();
        if (currentToken.equals(JsonToken.VALUE_STRING)) {
            String text = p.getValueAsString();
            if (OUI_CHAR.equalsIgnoreCase(text)) {
                return Boolean.TRUE;
            } else if (NON_CHAR.equalsIgnoreCase(text)) {
                return Boolean.FALSE;
            }
        } else if (currentToken.equals(JsonToken.VALUE_NULL)) {
            return getNullValue(ctxt);
        }
        ctxt.handleUnexpectedToken(Boolean.class, p);
        return null;
    }
}
