package com.f.transport.parser;

import com.f.transport.ResponseContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author : caibi
 * @date : 2019-01-16 15:03
 */
public class SpelContext extends StandardEvaluationContext implements ResponseContext {
    /**
     * Create a {@code StandardEvaluationContext} with a null root object.
     */
    public SpelContext() {
        setRootObject(null);
    }

    /**
     * Create a {@code StandardEvaluationContext} with the given root object.
     * @param rootObject the root object to use
     * @see #setRootObject
     */
    public SpelContext(Object rootObject) {
        setRootObject(rootObject);
    }
}
