parser grammar ProtoParser;

options {
    tokenVocab = ProtoLexer;
}

proto
    // syntax should be first statement in the file
    : syntax?
        ( packageStatement
        | importStatement
        | optionEntry
        | enumBlock
        | messageBlock
        | extendBlock
        | serviceBlock)*
    EOF
    ;
syntax
    : SYNTAX ASSIGN STRING_VALUE SEMICOLON
    ;
packageStatement
    : PACKAGE packageName SEMICOLON
    ;
packageName
    : fullIdent
    ;
importStatement
    : IMPORT PUBLIC? STRING_VALUE SEMICOLON
    ;
optionEntry
    : OPTION option SEMICOLON
    ;
enumBlock
    : ENUM enumName LCURLY (enumField | optionEntry)* RCURLY SEMICOLON?
    ;
enumName
    : ident
    ;
enumField
    : enumFieldName ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
    ;
enumFieldName
    : ident
    ;
extendBlock
    : EXTEND typeReference LCURLY extendBlockEntry* RCURLY SEMICOLON?
    ;
extendBlockEntry
    : field
    | groupBlock
    ;
serviceBlock
    : SERVICE serviceName LCURLY (rpcMethod | optionEntry)* RCURLY SEMICOLON?
    ;
serviceName
    : ident
    ;
rpcMethod
    : RPC rpcName LPAREN rpcType  RPAREN
      RETURNS LPAREN rpcType RPAREN (LCURLY optionEntry* RCURLY)? SEMICOLON?
    ;
rpcName
    : ident
    ;
rpcType
    : STREAM? typeReference
    ;
messageBlock
    : MESSAGE messageName LCURLY
        (field
        | optionEntry
        | messageBlock
        | enumBlock
        | extensions
        | extendBlock
        | groupBlock
        | oneof
        | map
        | reserved)*
   RCURLY SEMICOLON?
    ;
messageName
    : ident
    ;
oneof
    : ONEOF oneofName LCURLY (oneofField | oneofGroup)* RCURLY SEMICOLON?
    ;
oneofName
    : ident
    ;
oneofField
    : typeReference fieldName ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
    ;
oneofGroup
    : GROUP fieldName ASSIGN INTEGER_VALUE LCURLY
        (field
        | optionEntry
        | messageBlock
        | enumBlock
        | extensions
        | extendBlock
        | groupBlock)*
    RCURLY SEMICOLON?
    ;
map
    : MAP LT mapKey COMMA mapValue GT fieldName ASSIGN tag fieldOptions? SEMICOLON
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
    : fieldModifier GROUP groupName ASSIGN INTEGER_VALUE LCURLY
        (field
        | optionEntry
        | messageBlock
        | enumBlock
        | extensions
        | extendBlock
        | groupBlock)*
    RCURLY SEMICOLON?
    ;
groupName
    : ident
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
    : fieldNameString (COMMA fieldNameString)*
    ;
fieldNameString
    : STRING_VALUE
    ;
field
    : fieldModifier? typeReference fieldName ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
    ;
fieldName
    : ident
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
    | DOT? ident (DOT ident)*
    ;
fieldOptions
    : LSQUARE (option (COMMA option)* )? RSQUARE
    ;
option
    : optionName ASSIGN optionValue
    ;
optionName
    : ident (DOT ident)*
    | LPAREN typeReference RPAREN (DOT optionName)*    
    ;
optionValue
    : INTEGER_VALUE
    | FLOAT_VALUE
    | BOOLEAN_VALUE
    | STRING_VALUE
    | IDENT
    | textFormat
    ;
textFormat
    : LCURLY textFormatEntry* RCURLY
    ;
textFormatOptionName
    : ident
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
    | IDENT
    ;
fullIdent
    : ident (DOT ident)*
    ;
ident
    : IDENT
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