package io.protostuff.it;

import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.ServiceMethod;
import io.protostuff.generator.java.JavaExtensionProvider;
import io.protostuff.generator.java.UserTypeUtil;

/**
 * Custom extension provider used for tests.
 */
public class CustomExtensionProvider extends JavaExtensionProvider {

    /**
     * Create new instance of custom extension provider.
     */
    public CustomExtensionProvider() {
        super();
        registerProperty(ServiceMethod.class, "javaReturnTypeFullName", serviceMethod -> {
            Message returnType = serviceMethod.getReturnType();
            return UserTypeUtil.getCanonicalName(returnType);
        });
    }

}
