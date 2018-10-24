parser grammar DecafParser;

@header {
package decaf;
}

options
{
  language=Java;
  tokenVocab=DecafLexer;
}

program: CLASS PROGRAM LCURLY field_block* method_declaration* RCURLY;

field_block: field_declaration (COMMA field_declaration)* SEMICOLLON;

field_declaration: TYPE ID 
                  | TYPE ID LSQUARE INT RSQUARE;

method_declaration: method_return method_name LPARENT argument_list RPARENT block;

method_return: TYPE | VOID;

argument_list: (TYPE ID (COMMA TYPE ID)*)?;

block: LCURLY var_declaration* statement* RCURLY;

var_declaration: (TYPE ID (COMMA ID)*)* SEMICOLLON;

statement: location assign_operator expression SEMICOLLON
          | method_call SEMICOLLON
          | if_block
          | for_block
          | return_statement
          | BREAK SEMICOLLON
          | CONTINUE SEMICOLLON
          | block;

return_statement: RETURN expression? SEMICOLLON;

assign_operator: ASSIGN_OP | ASSIGN;

if_block: IF LPARENT expression RPARENT block else_block?;

else_block: ELSE block;

for_block: FOR ID ASSIGN expression COMMA expression block;

method_call: method_name LPARENT method_call_arguments? RPARENT
            | callout_call;

method_call_arguments: expression (COMMA expression)*;

method_name: ID;

location: ID
          | ID LSQUARE expression RSQUARE;

expression: location
            | method_call
            | literal
            | expression operators expression
            | MINUS expression
            | NEGATION expression
            | LPARENT expression RPARENT;

callout_call: CALLOUT LPARENT STRING ((COMMA callout_argument)+)? RPARENT;

callout_argument: expression
                  | STRING;

operators: CONDITIONAL_OP
          | RELATIONAL_OP
          | EQUAL_OP
          | ARITHMETIC_OP 
          | MINUS;

literal: INT
        | CHAR
        | BOOLEAN;                        
                    