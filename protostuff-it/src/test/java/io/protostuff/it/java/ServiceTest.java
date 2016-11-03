package io.protostuff.it.java;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.protostuff.Request;
import io.protostuff.Response;
import io.protostuff.Rpc;
import io.protostuff.Service;
import io.protostuff.it.service_test.RequestMessage;
import io.protostuff.it.service_test.ResponseMessage;
import io.protostuff.it.service_test.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceTest {

    private Class<TestService> serviceClass;
    private Method serviceMethod;

    @BeforeEach
    public void setUp() throws Exception {
        serviceClass = TestService.class;
        serviceMethod = serviceClass.getMethod("test", RequestMessage.class);
    }

    @Test
    public void simpleSignatureCheck() throws Exception {
        ListenableFuture<ResponseMessage> result = new TestServiceImpl().test(RequestMessage.newBuilder().build());
        assertTrue(result.isDone());
    }

    @Test
    public void service_annotation() throws Exception {
        Service annotation = serviceClass.getAnnotation(Service.class);
        assertEquals("io.protostuff.it.TestService", annotation.value());
    }

    @Test
    public void rpc_annotation() throws Exception {
        Rpc annotation = serviceMethod.getAnnotation(Rpc.class);
        assertNotNull(annotation);
    }

    @Test
    public void request_annotation() throws Exception {
        Request annotation = serviceMethod.getAnnotation(Request.class);
        assertEquals("testRequest", annotation.value());
    }

    @Test
    public void response_annotation() throws Exception {
        Response annotation = serviceMethod.getAnnotation(Response.class);
        assertEquals("testResponse", annotation.value());
    }

    static class TestServiceImpl implements TestService {

        @Override
        public ListenableFuture<ResponseMessage> test(RequestMessage request) {
            ResponseMessage responseMessage = ResponseMessage.newBuilder().build();
            return Futures.immediateFuture(responseMessage);
        }

        @Override
        public ListenableFuture<ResponseMessage> deprecated(RequestMessage request) {
            return null;
        }

        @Override
        public ListenableFuture<ResponseMessage> custom(RequestMessage request) {
            return null;
        }
    }
}
