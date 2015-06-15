parser grammar ProtoParser;

options {
    tokenVocab = ProtoLexer;
}

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
    | extendBlock
    | serviceBlock
    ;
packageStatement
    : PACKAGE packageName SEMICOLON
    ;
packageName
    : name (DOT name)*
    ;
importStatement
    : IMPORT PUBLIC? STRING_VALUE SEMICOLON
    ;
optionEntry
    : OPTION option SEMICOLON
    ;
enumBlock
    : ENUM NAME LCURLY enumBlockEntry* RCURLY SEMICOLON?
    ;
enumBlockEntry
    : enumConstant
    | optionEntry
    ;
enumConstant
    : NAME ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
    ;
extendBlock
    : EXTEND typeReference LCURLY extendBlockEntry* RCURLY SEMICOLON?
    ;
extendBlockEntry
    : field
    | groupBlock
    ;
serviceBlock
    : SERVICE name LCURLY serviceBlockEntry* RCURLY SEMICOLON?
    ;
serviceBlockEntry
    : rpcMethod
    | optionEntry
    ;
rpcMethod
    : RPC name LPAREN typeReference RPAREN 
      RETURNS LPAREN typeReference RPAREN rpcMethodOptions? SEMICOLON?
    ;
rpcMethodOptions
    : LCURLY optionEntry* RCURLY
    ;
messageBlock
    : MESSAGE NAME LCURLY messageBlockEntry* RCURLY SEMICOLON?
    ;
messageBlockEntry
    : field
    | optionEntry
    | messageBlock
    | enumBlock
    | extensions
    | extendBlock
    | groupBlock
    | oneof
    ;
oneof
    : ONEOF name LCURLY oneofEntry* RCURLY SEMICOLON?
    ;
oneofEntry
    : typeReference name ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
    | GROUP name ASSIGN INTEGER_VALUE LCURLY groupBlockEntry* RCURLY SEMICOLON?
    ;
groupBlock
    : fieldModifier GROUP name ASSIGN INTEGER_VALUE 
      LCURLY groupBlockEntry* RCURLY SEMICOLON?
    ;
groupBlockEntry
    : field
    | optionEntry
    | messageBlock
    | enumBlock
    | extensions
    | extendBlock
    | groupBlock
    ;
extensions
    : EXTENSIONS from (TO to)? SEMICOLON
    ;
from
    : INTEGER_VALUE
    ;
to
    : INTEGER_VALUE
    | MAX
    ;
field
    : fieldModifier? typeReference name ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
    ;
fieldModifier
    : OPTIONAL
    | REQUIRED
    | REPEATED
    ;
typeReference
    : DOT? name (DOT name)*
    ;
fieldOptions
    : LSQUARE (option (COMMA option)* )? RSQUARE
    ;
option
    : optionName ASSIGN optionValue
    ;
optionName
    : name (DOT name)*
    | LPAREN typeReference RPAREN (DOT optionName)*    
    ;
optionValue
    : INTEGER_VALUE
    | FLOAT_VALUE
    | BOOLEAN_VALUE
    | STRING_VALUE
    | NAME
    | textFormat
    ;
textFormat
    : LCURLY textFormatEntry* RCURLY
    ;
textFormatOptionName
    : name
    | LSQUARE typeReference RSQUARE
    ;
textFormatEntry
    : textFormatOptionName COLON textFormatOptionValue
    | textFormatOptionName textFormat
    ;
textFormatOptionValue
    : INTEGER_VALUE
    | FLOAT_VALUE
    | BOOLEAN_VALUE
    | STRING_VALUE
    | NAME
    ;
name
    : NAME
    | PACKAGE
    | SERVICE
    | EXTEND
    | EXTENSIONS
    | TO
    | SYNTAX
    ;