package io.protostuff.compiler.parser;

import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import io.protostuff.compiler.model.AbstractElement;
import io.protostuff.compiler.model.SourceCodeLocation;

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
        List<Token> tokensBefore = tokens.getHiddenTokensToLeft(start.getTokenIndex(), ProtoLexer.HIDDEN);
        if (tokensBefore != null) {
            int i = tokensBefore.size();
            while (i > 0 && !usedComments.get(tokensBefore.get(i - 1).getLine()) &&
                    (tokensBefore.get(i - 1).getType() == ProtoLexer.LINE_COMMENT
                            || !tokensBefore.get(i - 1).getText().contains("\n"))) {
                i--;
            }
            for (; i < tokensBefore.size(); i++) {
                Token token = tokensBefore.get(i);
                if (token.getType() == ProtoLexer.LINE_COMMENT) {
                    String text = getTextFromLineCommentToken(token);
                    comments.add(text);
                    usedComments.set(token.getLine());
                }
            }
        }
        if (addTrailingComment) {
            List<Token> tokensAfter = tokens.getHiddenTokensToRight(stop.getTokenIndex(), ProtoLexer.HIDDEN);
            if (tokensAfter != null && tokensAfter.size() > 0) {
                for (Token token : tokensAfter) {
                    if (token.getType() == ProtoLexer.LINE_COMMENT) {
                        String text = getTextFromLineCommentToken(token);
                        comments.add(text);
                        usedComments.set(token.getLine());
                        break;
                    } else {
                        if (token.getText().contains("\n")) {
                            break;
                        }
                    }
                }
            }
        }
        List<String> trimComments = trim(comments);
        for (String comment : trimComments) {
            element.addComment(comment);
        }
    }

    /**
     * Remove common leading whitespaces from all strings in the list.
     * Returns new list instance.
     */
    private List<String> trim(List<String> comments) {
        List<String> trimComments = new ArrayList<>();
        int n = 0;
        boolean tryRemoveWhitespace = true;
        while (tryRemoveWhitespace) {
            boolean allLinesAreShorter = true;
            for (String comment : comments) {
                if (comment.length() > n) {
                    if (comment.charAt(n) != ' ') {
                        tryRemoveWhitespace = false;
                    }
                    allLinesAreShorter = false;
                }
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

    private String getTextFromLineCommentToken(Token token) {
        String comment = token.getText().trim();
        return comment.substring(2);
    }


}
