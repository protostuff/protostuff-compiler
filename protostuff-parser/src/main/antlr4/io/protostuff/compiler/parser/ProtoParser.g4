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
    : name (DOT name)*
    ;
importStatement
    : IMPORT PUBLIC? STRING_VALUE SEMICOLON
    ;
optionEntry
    : OPTION option SEMICOLON
    ;
enumBlock
    : ENUM name LCURLY (enumConstant | optionEntry)* RCURLY SEMICOLON?
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
    : SERVICE name LCURLY (rpcMethod | optionEntry)* RCURLY SEMICOLON?
    ;
rpcMethod
    : RPC name LPAREN rpcType  RPAREN
      RETURNS LPAREN rpcType RPAREN (LCURLY optionEntry* RCURLY)? SEMICOLON?
    ;
rpcType
    : STREAM? typeReference
    ;
messageBlock
    : MESSAGE name LCURLY
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
oneof
    : ONEOF name LCURLY (oneofField | oneofGroup)* RCURLY SEMICOLON?
    ;
oneofField
    : typeReference name ASSIGN INTEGER_VALUE fieldOptions? SEMICOLON
    ;
oneofGroup
    : GROUP name ASSIGN INTEGER_VALUE LCURLY
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
    : fieldModifier GROUP name ASSIGN INTEGER_VALUE LCURLY
        (field
        | optionEntry
        | messageBlock
        | enumBlock
        | extensions
        | extendBlock
        | groupBlock)*
    RCURLY SEMICOLON?
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