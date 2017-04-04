package io.protostuff.compiler.parser;

import static io.protostuff.compiler.parser.ProtoLexer.LINE_COMMENT;
import static io.protostuff.compiler.parser.ProtoLexer.NL;
import static org.antlr.v4.runtime.Lexer.HIDDEN;

import io.protostuff.compiler.model.AbstractElement;
import io.protostuff.compiler.model.SourceCodeLocation;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

/**
 * @author Kostiantyn Shchepanovskyi
 */
public abstract class AbstractProtoParserListener extends ProtoParserBaseListener {

    protected final ProtoContext context;
    private final BufferedTokenStream tokens;

    private BitSet usedComments;

    protected AbstractProtoParserListener(BufferedTokenStream tokens, ProtoContext context) {
        this.context = context;
        this.tokens = tokens;
        usedComments = new BitSet();
    }

    protected SourceCodeLocation getSourceCodeLocation(ParserRuleContext ctx) {
        String file = context.getProto().getFilename();
        int line = ctx.getStart().getLine();
        return new SourceCodeLocation(file, line);
    }

    protected void attachComments(ParserRuleContext ctx, AbstractElement element, boolean addTrailingComment) {
        List<String> comments = new ArrayList<>();
        Token stop = ctx.getStop();
        Token start = ctx.getStart();
        List<Token> tokensBefore = tokens.getHiddenTokensToLeft(start.getTokenIndex(), HIDDEN);
        if (tokensBefore != null) {
            for (Token token : tokensBefore) {
                if (usedComments.get(token.getLine())) {
                    continue;
                }
                if (token.getType() == LINE_COMMENT) {
                    addCommentToList(token, comments);
                }
            }
        }
        if (addTrailingComment) {
            List<Token> tokensAfter = tokens.getHiddenTokensToRight(stop.getTokenIndex(), HIDDEN);
            findTrailingComment(tokensAfter)
                    .ifPresent(token -> addCommentToList(token, comments));
        }
        List<String> trimComments = trim(comments);
        for (String comment : trimComments) {
            element.addComment(comment);
        }
    }

    private void addCommentToList(Token token, List<String> comments) {
        String text = getTextFromLineCommentToken(token);
        comments.add(text);
        usedComments.set(token.getLine());
    }

    private Optional<Token> findTrailingComment(@Nullable List<Token> tokensAfter) {
        if (tokensAfter == null) {
            return Optional.empty();
        }
        Optional<Token> trailingComment = Optional.empty();
        for (Token token : tokensAfter) {
            if (token.getType() == LINE_COMMENT) {
                trailingComment = Optional.of(token);
            }
            // Try to find single trailing comment (on the same line).
            // If we hit newline, then we can stop right away.
            if (trailingComment.isPresent() || token.getType() == NL) {
                break;
            }
        }
        return trailingComment;
    }

    /**
     * Remove common leading whitespaces from all strings in the list.
     * Returns new list instance.
     */
    protected List<String> trim(List<String> comments) {
        List<String> trimComments = new ArrayList<>();
        int n = 0;
        boolean tryRemoveWhitespace = true;
        while (tryRemoveWhitespace) {
            boolean allLinesAreShorter = true;
            for (String comment : comments) {
                if (comment.length() <= n) {
                    continue;
                }
                if (comment.charAt(n) != ' ') {
                    tryRemoveWhitespace = false;
                }
                allLinesAreShorter = false;
            }
            if (allLinesAreShorter) {
                break;
            }
            if (tryRemoveWhitespace) {
                n++;
            }
        }
        for (String comment : comments) {
            if (comment.length() > n) {
                String substring = comment.substring(n);
                trimComments.add(substring);
            } else {
                trimComments.add("");
            }
        }
        return trimComments;
    }

    protected String getTextFromLineCommentToken(Token token) {
        String comment = token.getText().trim();
        return comment.substring(2);
    }


}
