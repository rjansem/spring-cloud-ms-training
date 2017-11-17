package config.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.rjansem.microservices.training.apisecurity.config.deserializer.BigDecimalMoneySerializer;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés à la déserialisation des {@link BigDecimal}
 *
 * @author jntakpe
 */
public class BigDecimalMoneySerializerTests {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void serialize_shouldReturnNull() throws Exception {
        String json = objectMapper.writeValueAsString(new TestObject(null));
        assertThat(objectMapper.readValue(json, TestObject.class).getAmount()).isNull();
    }

    @Test
    public void serialize_shouldReturnZero() throws Exception {
        String json = objectMapper.writeValueAsString(new TestObject(BigDecimal.ZERO));
        assertThat(objectMapper.readValue(json, TestObject.class).getAmount()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void serialize_shouldReturnInteger() throws Exception {
        String json = objectMapper.writeValueAsString(new TestObject(BigDecimal.ONE));
        assertThat(objectMapper.readValue(json, TestObject.class).getAmount()).isEqualTo(new BigDecimal("1.00"));
    }

    @Test
    public void serialize_shouldReturnDecimalScale2() throws Exception {
        String json = objectMapper.writeValueAsString(new TestObject(new BigDecimal("12.34")));
        assertThat(objectMapper.readValue(json, TestObject.class).getAmount()).isEqualTo(new BigDecimal("12.34"));
    }

    @Test
    public void serialize_shouldReturnDecimalScale2Cut() throws Exception {
        String json = objectMapper.writeValueAsString(new TestObject(new BigDecimal("12.341")));
        assertThat(objectMapper.readValue(json, TestObject.class).getAmount()).isEqualTo(new BigDecimal("12.34"));
    }

    private static class TestObject {

        @JsonSerialize(using = BigDecimalMoneySerializer.class)
        private BigDecimal amount;

        public TestObject() {
        }

        public TestObject(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public TestObject setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }
    }

}
