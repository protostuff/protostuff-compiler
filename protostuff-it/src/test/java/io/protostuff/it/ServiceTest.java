package io.protostuff.it;

import io.protostuff.Request;
import io.protostuff.Response;
import io.protostuff.Rpc;
import io.protostuff.Service;
import io.protostuff.it.service_test.RequestMessage;
import io.protostuff.it.service_test.ResponseMessage;
import io.protostuff.it.service_test.TestService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceTest {

    private Class<TestService> serviceClass;
    private Method serviceMethod;

    static class TestServiceImpl implements TestService {

        @Override
        public CompletableFuture<ResponseMessage> test(RequestMessage request) {
            ResponseMessage responseMessage = ResponseMessage.newBuilder().build();
            return CompletableFuture.completedFuture(responseMessage);
        }
    }

    @Before
    public void setUp() throws Exception {
        serviceClass = TestService.class;
        serviceMethod = serviceClass.getMethod("test", RequestMessage.class);
    }

    @Test
    public void simpleSignatureCheck() throws Exception {
        CompletableFuture<ResponseMessage> result = new TestServiceImpl().test(RequestMessage.newBuilder().build());
        Assert.assertTrue(result.isDone());
    }

    @Test
    public void service_annotation() throws Exception {
        Service annotation = serviceClass.getAnnotation(Service.class);
        Assert.assertEquals("io.protostuff.it.TestService", annotation.value());
    }

    @Test
    public void rpc_annotation() throws Exception {
        Rpc annotation = serviceMethod.getAnnotation(Rpc.class);
        Assert.assertNotNull(annotation);
    }

    @Test
    public void request_annotation() throws Exception {
        Request annotation = serviceMethod.getAnnotation(Request.class);
        Assert.assertEquals("testRequest", annotation.value());
    }

    @Test
    public void response_annotation() throws Exception {
        Response annotation = serviceMethod.getAnnotation(Response.class);
        Assert.assertEquals("testResponse", annotation.value());
    }
}
