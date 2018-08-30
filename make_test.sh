#!/bin/sh
echo '------COMPILANDO...------\n'
ant
echo '\n------NUMBER 1------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/number1
echo '\n------NUMBER 2------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/number2
echo '\n------HEXLIT 2------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/hexlit2
echo '\n------HEXLIT 3------\n'
java -jar dist/Compiler.jar -target scan -debug ../scanner/hexlit3
