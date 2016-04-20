package io.protostuff.it;

import io.protostuff.compiler.model.Message;
import io.protostuff.compiler.model.ServiceMethod;
import io.protostuff.generator.AbstractGenerator;
import io.protostuff.generator.GeneratorInitializer;
import io.protostuff.generator.java.UserTypeUtil;

public class CustomInitializer implements GeneratorInitializer {

    @Override
    public void init(AbstractGenerator generator) {
        generator.extend(ServiceMethod.class, "javaReturnTypeFullName", serviceMethod -> {
            Message returnType = serviceMethod.getReturnType();
            return UserTypeUtil.getCanonicalName(returnType);
        });
    }
}
