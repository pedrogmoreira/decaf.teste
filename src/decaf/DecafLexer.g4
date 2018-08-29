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

LCURLY : '{';
RCURLY : '}';

ID  :
  ('a'..'z' | 'A'..'Z')+;

WS_ : (' ' | '\n' ) -> skip;

SL_COMMENT : '//' (~'\n')* '\n' -> skip;

CHAR : '\'' (ESC | ASCII_ACCEPT) '\'';
STRING : '"' (ESC | ASCII_ACCEPT)* '"';
INT: INTEGER_LITERAL | HEX_LITERAL;

OP: ('+' | '-' | '*' | '/' | '||' | '&&' | '>' | '<' | '>=' | '<=' | '==' | '!=');

fragment ESC :  '\\' ('n' | 't' | 'r' | '"' | '\\' | '\'');
fragment ASCII_ACCEPT: [\u0020-\u0021 | \u0023-\u0026 | \u0028-\u005B | \u005D-\u007E];
fragment SIGNED_NUMBER: [0-9]+;
fragment INTEGER_LITERAL: '-'? SIGNED_NUMBER;
fragment HEX_DIGIT: [0-9a-fA-F];
fragment HEX_PREFIX: '0' [xX];
fragment HEX_LITERAL: HEX_PREFIX HEX_DIGIT+;