package io.protostuff.generator.java;

import io.protostuff.compiler.model.Package;
import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.ServiceMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Kostiantyn Shchepanovskyi
 */
class ServiceUtilTest {

    private Proto proto;
    private Service service;
    private ServiceMethod serviceMethod;

    @BeforeEach
    void setUp() {
        proto = new Proto();
        proto.setPackage(new Package(proto, "io.protostuff"));
        service = new Service(proto);
        service.setProto(proto);
        service.setName("service");
        serviceMethod = new ServiceMethod(service);
        serviceMethod.setName("service_method");

    }

    @Test
    void getClassName() {
        String name = ServiceUtil.getClassName(service);
        assertEquals("Service", name);
    }

    @Test
    void getMethodName() {
        String name = ServiceUtil.getMethodName(serviceMethod);
        assertEquals("serviceMethod", name);
    }

    @Test
    void getMethodName_reservedKeyword() {
        serviceMethod.setName("interface");
        String name = ServiceUtil.getMethodName(serviceMethod);
        assertEquals("interface_", name);
    }

}