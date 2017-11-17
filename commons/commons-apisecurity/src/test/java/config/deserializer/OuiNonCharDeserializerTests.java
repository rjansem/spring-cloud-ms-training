package config.deserializer;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.rjansem.microservices.training.apisecurity.config.deserializer.OuiNonCharDeserializer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests associés aux déserializer de Boolean {@link OuiNonCharDeserializer}
 *
 * @author jntakpe
 */
public class OuiNonCharDeserializerTests {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test(expected = JsonMappingException.class)
    public void deserialize_shouldFailCuzUnmappableValue() throws Exception {
        deserialize("unmappable");
        fail("should have failed at this point");
    }

    @Test
    public void deserialize_shouldReturnNull() throws Exception {
        assertThat(deserialize(null)).isNull();
    }

    @Test
    public void deserialize_shouldMapOToTrue() throws Exception {
        assertThat(deserialize("O")).isTrue();
    }

    @Test
    public void deserialize_shouldMapOToTrueIgnoringCase() throws Exception {
        assertThat(deserialize("o")).isTrue();
    }

    @Test
    public void deserialize_shouldMapNToFalse() throws Exception {
        assertThat(deserialize("N")).isFalse();
    }

    @Test
    public void deserialize_shouldMapNToFalseIgnoringCase() throws Exception {
        assertThat(deserialize("n")).isFalse();
    }

    private Boolean deserialize(String yesOrNo) throws IOException {
        String value = yesOrNo != null ? String.format("\"%s\"", yesOrNo) : null;
        TestObject testObject = objectMapper.readValue(String.format("{\"yesOrNo\": %s}", value), TestObject.class);
        return testObject.getYesOrNo();
    }

    private static class TestObject {

        @JsonDeserialize(using = OuiNonCharDeserializer.class)
        private Boolean yesOrNo;

        public Boolean getYesOrNo() {
            return yesOrNo;
        }

        public void setYesOrNo(Boolean yesOrNo) {
            this.yesOrNo = yesOrNo;
        }
    }

}