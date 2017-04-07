package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.Proto;
import io.protostuff.compiler.model.Service;
import io.protostuff.compiler.model.ServiceMethod;
import org.antlr.v4.runtime.BufferedTokenStream;

/**
 * Parse listener for services.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class ServiceParseListener extends AbstractProtoParserListener {


    protected ServiceParseListener(BufferedTokenStream tokens, ProtoContext context) {
        super(tokens, context);
    }

    @Override
    public void enterServiceBlock(ProtoParser.ServiceBlockContext ctx) {
        Proto parent = context.peek(Proto.class);
        Service service = new Service(parent);
        context.push(service);
    }

    @Override
    public void exitServiceBlock(ProtoParser.ServiceBlockContext ctx) {
        Service service = context.pop(Service.class);
        Proto proto = context.peek(Proto.class);
        String name = ctx.serviceName().getText();
        service.setName(name);
        service.setSourceCodeLocation(getSourceCodeLocation(ctx));
        proto.addService(service);
        attachComments(ctx, service, false);
    }

    @Override
    public void enterRpcMethod(ProtoParser.RpcMethodContext ctx) {
        Service parent = context.peek(Service.class);
        ServiceMethod method = new ServiceMethod(parent);
        context.push(method);
    }

    @Override
    public void exitRpcMethod(ProtoParser.RpcMethodContext ctx) {
        final ServiceMethod method = context.pop(ServiceMethod.class);
        final Service service = context.peek(Service.class);
        String name = ctx.rpcName().getText();
        String arg = ctx.rpcType(0).typeReference().getText();
        boolean argStream = ctx.rpcType(0).STREAM() != null;
        String ret = ctx.rpcType(1).typeReference().getText();
        boolean retStream = ctx.rpcType(1).STREAM() != null;
        method.setName(name);
        method.setArgTypeName(arg);
        method.setArgStream(argStream);
        method.setReturnTypeName(ret);
        method.setReturnStream(retStream);
        method.setSourceCodeLocation(getSourceCodeLocation(ctx));
        service.addMethod(method);
        attachComments(ctx, method, true);
    }
}
