package io.protostuff.it;

import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.ServiceMethod;
import io.protostuff.generator.java.JavaExtensionProvider;
import io.protostuff.generator.java.UserTypeUtil;

public class CustomExtensionProvider extends JavaExtensionProvider {

    public CustomExtensionProvider() {
        super();
        registerProperty(ServiceMethod.class, "javaReturnTypeFullName", serviceMethod -> {
            Message returnType = serviceMethod.getReturnType();
            return UserTypeUtil.getCanonicalName(returnType);
        });
    }

}
