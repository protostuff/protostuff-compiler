parser grammar ProtoParser;

options {
    tokenVocab = ProtoLexer;
}

proto
    // syntax should be first statement in the file
    : syntax? statement* EOF
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
    : ENUM name LCURLY enumBlockEntry* RCURLY SEMICOLON?
    ;
enumBlockEntry
    : enumConstant
    | optionEntry
    ;
enumConstant
    : name ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
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
    : RPC name LPAREN rpcType  RPAREN
      RETURNS LPAREN rpcType RPAREN rpcMethodOptions? SEMICOLON?
    ;
rpcType
    : STREAM? typeReference
    ;
rpcMethodOptions
    : LCURLY optionEntry* RCURLY
    ;
messageBlock
    : MESSAGE name LCURLY messageBlockEntry* RCURLY SEMICOLON?
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
    | map
    | reserved
    ;
oneof
    : ONEOF name LCURLY oneofEntry* RCURLY SEMICOLON?
    ;
oneofEntry
    : oneofField
    | oneofGroup
    ;
oneofField
    : typeReference name ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
    ;
oneofGroup
    : GROUP name ASSIGN INTEGER_VALUE LCURLY groupBlockEntry* RCURLY SEMICOLON?
    ;
map
    : MAP LT mapKey COMMA mapValue GT name ASSIGN tag fieldOptions? SEMICOLON
    ;
mapKey
    : INT32
    | INT64
    | UINT32
    | UINT64
    | SINT32
    | SINT64
    | FIXED32
    | FIXED64
    | SFIXED32
    | SFIXED64
    | BOOL
    | STRING
    ;
mapValue
    : typeReference
    ;
tag
    : INTEGER_VALUE
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
    : EXTENSIONS ranges SEMICOLON
    ;
ranges
    : range (COMMA range)*
    ;
range
    : INTEGER_VALUE ( TO ( INTEGER_VALUE | MAX ) )?
    ;
reserved
    : RESERVED (ranges | fieldNames) SEMICOLON
    ;
fieldNames
    : fieldName (COMMA fieldName)*
    ;
fieldName
    : STRING_VALUE
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
    : DOUBLE
    | FLOAT
    | INT32
    | INT64
    | UINT32
    | UINT64
    | SINT32
    | SINT64
    | FIXED32
    | FIXED64
    | SFIXED32
    | SFIXED64
    | BOOL
    | STRING
    | BYTES
    | DOT? name (DOT name)*
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
    | SYNTAX
    | IMPORT
    | PUBLIC
    | OPTION
    | MESSAGE
    | GROUP
    | OPTIONAL
    | REQUIRED
    | REPEATED
    | ONEOF
    | EXTEND
    | EXTENSIONS
    | TO
    | MAX
    | RESERVED
    | ENUM
    | SERVICE
    | RPC
    | RETURNS
    | STREAM
    | MAP
    | BOOLEAN_VALUE
    | DOUBLE
    | FLOAT
    | INT32
    | INT64
    | UINT32
    | UINT64
    | SINT32
    | SINT64
    | FIXED32
    | FIXED64
    | SFIXED32
    | SFIXED64
    | BOOL
    | STRING
    | BYTES
    ;