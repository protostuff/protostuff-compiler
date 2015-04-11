grammar Proto3;

proto
    // syntax should be first statement in the file
    : syntax? statement*
    ;
syntax
    // only proto3 is supported
    : SYNTAX ASSIGN STRING_VALUE SEMICOLON
    ;
statement
    : packageStatement
    | importStatement
    | optionEntry
    | enumBlock
    | messageBlock
    ;
packageStatement
    : PACKAGE declarationRef SEMICOLON
    ;
importStatement
    : IMPORT STRING_VALUE SEMICOLON
    ;
optionEntry
    : OPTION option SEMICOLON
    ;
enumBlock
    : ENUM NAME '{' enumBlockEntry* '}'
    ;
enumBlockEntry
    : enumConstant
    | optionEntry
    ;
enumConstant
    : NAME ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
    ;
messageBlock
    : MESSAGE NAME '{' messageBlockEntry* '}'
    ;
messageBlockEntry
    : messageField
    | optionEntry
    | messageBlock
    | enumBlock
    ;
messageField
    : fieldType NAME ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
    ;
fieldType
    : '.'? declarationRef
    ;
fieldOptions
    : '[' (option (',' option)* )? ']'
    ;
option
    : optionName ASSIGN optionValue
    ;
optionName
    : NAME
    | customOptionName
    ;
customOptionName
    : '(' declarationRef ')' ('.' declarationRef)?
    ;
declarationRef
    : NAME ('.' NAME)*
    ;
optionValue
    : INTEGER_VALUE
    | BOOLEAN_VALUE
    | STRING_VALUE
    | NAME
    | textFormat
    ;
textFormat
    : '{' textFormatEntry* '}'
    ;
textFormatEntry
    : NAME ':' optionValue
    ;
PACKAGE
    : 'package'
    ;
SYNTAX
    : 'syntax'
    ;
IMPORT
    : 'import'
    ;
OPTION
    : 'option'
    ;
MESSAGE
    : 'message'
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
REPEATED
    : 'repeated'
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
INTEGER_VALUE
    : DEC_VALUE
    | HEX_VALUE
    | OCT_VALUE
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
    :   '\\' ('a'|'v'|'b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
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
