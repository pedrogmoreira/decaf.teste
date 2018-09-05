#!/bin/sh
echo '------COMPILANDO...------\n'
ant

echo '\n------CHAR 1------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/char1
echo '\n------CHAR 2------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/char2
echo '\n------CHAR 3------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/char3
echo '\n------CHAR 4------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/char4
echo '\n------CHAR 5------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/char5
echo '\n------CHAR 6------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/char6
echo '\n------CHAR 7------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/char7
echo '\n------CHAR 8------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/char8
echo '\n------CHAR 9------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/char9

echo '\n------HEXLIT 1------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/hexlit1
echo '\n------HEXLIT 2------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/hexlit2
echo '\n------HEXLIT 3------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/hexlit3

echo '\n------ID 1------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/id1
echo '\n------ID 2------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/id2
echo '\n------ID 3------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/id3

echo '\n------NUMBER 1------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/number1
echo '\n------NUMBER 2------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/number2

echo '\n------OP 1------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/op1
echo '\n------OP 2------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/op2

echo '\n------STRING 1------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/string1
echo '\n------STRING 2------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/string2
echo '\n------STRING 3------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/string3

echo '\n------TOKENS 1------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/tokens1
echo '\n------TOKENS 2------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/tokens2
echo '\n------TOKENS 3------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/tokens3
echo '\n------TOKENS 4------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/tokens4

echo '\n------WS 1------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/ws1