package io.protostuff.compiler.parser;

import io.protostuff.compiler.model.SourceCodeLocation;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractProtoParsetListener extends ProtoParserBaseListener {

    protected final ProtoContext context;

    protected AbstractProtoParsetListener(ProtoContext context) {
        this.context = context;
    }

    protected SourceCodeLocation getSourceCodeLocation(ParserRuleContext ctx) {
        String file = context.getProto().getFilename();
        int line = ctx.getStart().getLine();
        return new SourceCodeLocation(file, line);
    }

}
