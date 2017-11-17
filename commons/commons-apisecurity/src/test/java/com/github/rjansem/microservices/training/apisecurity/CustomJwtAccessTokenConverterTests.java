package com.github.rjansem.microservices.training.apisecurity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests associés à l'ajout de données au token JWT {@link CustomJwtAccessTokenConverterTests}
 *
 * @author jntakpe
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomJwtAccessTokenConverterTests {

    public static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqbnRha3BlIiwic2NvcGUiOlsib3BlbmlkIl0sInJhY2luZXMiOlsiMDAwMDA4IiwiMDAwMDA3Il0sImV4cCI6MTQ3OTMxNDgwNiwiYXV0aG9yaXRpZXMiOlsiY29uc3VsdGF0aW9ucyJdLCJqdGkiOiI1YThhNzUxMy00MjRlLTQ3NjYtODU4Zi03MzQ5NmM1ZDllN2YiLCJjbGllbnRfaWQiOiJlZnMifQ.NS5mp0QY8mODd3rWWmNi1bdUOfeZ0PBCIoqSDjJ4qIZdMoEmr4J8CwqF5h_DxDH0TwWUbA-Kk5PAEvUAmmYmvsZEd411kqTgY2OyiPzQx4l6VKDbvE_kKHcY2J1dpCuy0XOArLdLTszWJHel669UWglqINuYaFhPJUtVw_cPXw3QonWMY4yzFWPpPPaoTEyMjpQdYQ1FJG3YwXtUdwSvW16MAJijy1JtbqBl30s8Crdz6bw9umKqYNB_7ZmUj5q-QceuaExwznu2ALw5w7WB3qIXFu9uruGCdWxIYUethKmQ4GBV345v4SVQWME5I9W2n49GYegYSwwvIjecwci5Xg";

    private static final String BAD_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJqbnRha3sdfBlIiwic2NvcGUiOlsib3BlbmlkIl0sImV4cCI6MTQ3OTIzMTI3MiwianRpIjoiNTBlMTNhMDYtMTVhZS00Y2E4LWFjZmEtYjQ4MTYzOTQwYmE0IiwiY2xpZW50X2lkIjoiZWZzIn0.d5seMOPXQAZym5JJhRFtiBsCPJ6LnkCzIAcswL5zKuIaWuVmKdVOMKlrlnj61WgQCAOvDEBiQCk9YjAe1Zr64XEL8apQ-2ezcZQTw_nUn2qxbJDNs_1vMmUygxNwaaSKjefgbA4sXKvNxu3svaWq6azko0UjJ9YFldLSRV-aO32pZbvBRpCyzNyrLyuI9gWJYcBwpZfdx5QhkC6AkcLk4vtwQ2G9SBUr8crfYhWqGMtK8DjHbHji3Rdy-Bh4G-dmhxAu5F_X2vniDkbxkQb1ijB1qHHXOzdQqPZSdB74O6wnl6pqO8IVHKiuYBPwBnZhRV8fzNf_VjAyQCh0yB754A";

    @Autowired
    private CustomJwtAccessTokenConverter customJwtAccessTokenConverter;

    @Test(expected = InvalidTokenException.class)
    public void decode_shouldFailCuzInvalidToken() throws Exception {
        customJwtAccessTokenConverter.decode(BAD_TOKEN);
    }

    @Test
    public void decode_shouldAddTokenToMap() throws Exception {
        Map<String, Object> updatedMap = customJwtAccessTokenConverter.decode(TOKEN);
        assertThat(updatedMap.get(SecurityConstants.TOKEN_KEY)).isNotNull().isEqualTo(TOKEN);
        assertThat(updatedMap.get("user_name")).isNotNull().isEqualTo("jntakpe");
        assertThat(updatedMap.get("scope")).asList().isNotEmpty().contains("openid");
        assertThat(updatedMap.get("exp")).isNotNull();
        assertThat(updatedMap.get("jti")).isNotNull();
        assertThat(updatedMap.get("client_id")).isNotNull();
    }

}
