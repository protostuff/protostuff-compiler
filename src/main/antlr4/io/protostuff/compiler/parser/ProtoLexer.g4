lexer grammar ProtoLexer;

PACKAGE
    : 'package'
    ;
SYNTAX
    : 'syntax'
    ;
IMPORT
    : 'import'
    ;
PUBLIC
    : 'public'
    ;
OPTION
    : 'option'
    ;
MESSAGE
    : 'message'
    ;
GROUP
    : 'group'
    ;
OPTIONAL
    : 'optional'
    ;
REQUIRED
    : 'required'
    ;
REPEATED
    : 'repeated'
    ;
ONEOF
    : 'oneof'
    ;
EXTEND
    : 'extend'
    ;
EXTENSIONS
    : 'extensions'
    ;
TO
    : 'to'
    ;
MAX
    : 'max'
    ;
ENUM
    : 'enum'
    ;
SERVICE
    : 'service'
    ;
RPC
    : 'rpc'
    ;
RETURNS
    : 'returns'
    ;
COMMENT
    : '/*' .*? '*/' -> skip
    ;
LINE_COMMENT
    : '//' .*? '\r'? '\n' -> skip
    ;
WS
    : [ \t\r\n]+ -> skip
    ;
LCURLY
    :   '{'
    ;

RCURLY
    :   '}'
    ;

LPAREN
    :   '('
    ;

RPAREN
    :   ')'
    ;

LSQUARE
    :   '['
    ;

RSQUARE
    :   ']'
    ;
COMMA
    :   ','
    ;
DOT
    :   '.'
    ;
COLON
    :   ':'
    ;
SEMICOLON
    : ';'
    ;
ASSIGN
    : '='
    ;
BOOLEAN_VALUE
    : 'true'
    | 'false'
    ;
NAME
    :   (ALPHA | UNDERSCORE) (ALPHA | DIGIT | UNDERSCORE)*
    ;
STRING_VALUE
    : '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;
TEXTFORMAT_STRING_VALUE
    : '\'' ( ESC_SEQ | ~('\\'|'\'') )* '\''
    ;
INTEGER_VALUE
    : DEC_VALUE
    | HEX_VALUE
    | OCT_VALUE
    ;
FLOAT_VALUE
    : EXPONENT
    | FLOAT
    | MINUS? INF
    | NAN
    ;
fragment EXPONENT
    : (FLOAT|DEC_VALUE) EXP DEC_VALUE
    ;
fragment FLOAT
    : MINUS? DIGIT+ '.' DIGIT*     // "0.", "0.123"
    | MINUS? '.' DIGIT+            // ".123"
    ;
fragment INF
    : 'inf'
    ;
fragment NAN 
    : 'nan'
    ;
fragment EXP
    : 'e'
    | 'E'
    ;
fragment DEC_VALUE
    : '0' | MINUS? '1'..'9' '0'..'9'*
    ;
fragment HEX_VALUE
    : MINUS? '0' [xX] HEX_DIGIT+
    ;
fragment OCT_VALUE
    : MINUS? '0' OCT_DIGIT+
    ;
fragment MINUS
    : '-'
    ;
fragment ALPHA
    : [a-zA-Z]
    ;
fragment DIGIT
    : [0-9]
    ;
fragment HEX_DIGIT
    : [0-9a-fA-F]
    ;
fragment OCT_DIGIT
    : [0-7]
    ;
fragment UNDERSCORE
    : '_'
    ;
fragment ESC_SEQ
    :   '\\' ('a'|'v'|'b'|'t'|'n'|'f'|'r'|'?'|'\"'|'\''|'\\')
    |   '\\' ('x'|'X') HEX_DIGIT HEX_DIGIT
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;
fragment OCTAL_ESC
    :   '\\' [0-3] OCT_DIGIT OCT_DIGIT
    |   '\\' OCT_DIGIT OCT_DIGIT
    |   '\\' OCT_DIGIT
    ;
fragment UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;
