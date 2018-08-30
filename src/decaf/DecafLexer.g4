lexer grammar DecafLexer;

@header {
package decaf;
}

options
{
  language=Java;
}

tokens
{
  TK_class
}

PROGRAM: 'Program';
CLASS: 'class';
TYPE: 'int' | 'boolean';
CALLOUT: 'callout';
IF: 'if';
ELSE: 'else';
FOR: 'for';
VOID: 'void';
RETURN: 'return';
BREAK: 'break';
CONTINUE: 'continue';

LCURLY : '{';
RCURLY : '}';
LSQUARE : '[';
RSQUARE : ']';
LPARENT : '(';
RPARENT : ')';
COMMA : ',';
SEMICOLLON : ';';

WS_ : (' ' | '\n' | '\t' | '\r' ) -> skip;

SL_COMMENT : '//' (~'\n')* '\n' -> skip;

CHAR : '\'' (ESC | ASCII_ACCEPT) '\'';
STRING : '"' (ESC | ASCII_ACCEPT)* '"';
INT: HEX_LITERAL | INTEGER_LITERAL; 
BOOLEAN: TRUE | FALSE;

NEGATION: '!';
ASSIGN: '=';
ASSIGN_OP : '+=' | '-=' | '*=' | '/=';
BINARY_OP: '||' | '&&' | '>' | '<' | '>=' | '<=' | '==' | '!=';
ARITHMETIC_OP: '+' | '-' | '*' | '/' | '%';



fragment ESC :  '\\' ('n' | 't' | 'r' | '"' | '\\' | '\'');
fragment ASCII_ACCEPT: [\u0020-\u0021 | \u0023-\u0026 | \u0028-\u005B | \u005D-\u007E];
fragment NUMBER: [0-9];
fragment INTEGER_LITERAL: NUMBER+ WS_;
fragment LETTER: [a-zA-Z];
fragment HEX_DIGIT: [0-9a-fA-F];
fragment HEX_PREFIX: '0x';
fragment HEX_LITERAL: HEX_PREFIX HEX_DIGIT+;
fragment TRUE: 'true';
fragment FALSE: 'false';