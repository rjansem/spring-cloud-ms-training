package com.github.rjansem.microservices.training.commons.testing;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Constants d'assertions pour les ressources web
 *
 * @author rjansem
 */
public final class AssertionConstants {

    public static final ResultMatcher STATUS_CREATED = MockMvcResultMatchers.status().isCreated();

    public static final ResultMatcher STATUS_OK = MockMvcResultMatchers.status().isOk();

    public static final ResultMatcher STATUS_BAD_REQUEST = MockMvcResultMatchers.status().isBadRequest();

    public static final ResultMatcher STATUS_NOT_ALLOWED = MockMvcResultMatchers.status().isMethodNotAllowed();

    public static final ResultMatcher STATUS_SERVER_ERROR = MockMvcResultMatchers.status().isInternalServerError();

    public static final ResultMatcher STATUS_UNAUTHORIZED = MockMvcResultMatchers.status().isUnauthorized();

    public static final ResultMatcher STATUS_NO_CONTENT = MockMvcResultMatchers.status().isNoContent();

    public static final ResultMatcher STATUS_NOT_FOUND = MockMvcResultMatchers.status().isNotFound();

    public static final ResultMatcher CONTENT_TYPE_JSON = MockMvcResultMatchers.content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON);

    public static final ResultMatcher OBJECT_EXISTS = MockMvcResultMatchers.jsonPath("$").exists();

    public static final ResultMatcher ARRAY_EMPTY = MockMvcResultMatchers.jsonPath("$.[*]").isEmpty();

    public static final ResultMatcher ARRAY_NOT_EMPTY = MockMvcResultMatchers.jsonPath("$.[*]").isNotEmpty();

}
