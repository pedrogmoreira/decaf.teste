#!/bin/sh
echo '------COMPILANDO...------\n'
ant

echo '\n------ILLEGAL 01------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-01
echo '\n------ILLEGAL 02------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-02
echo '\n------ILLEGAL 03------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-03
echo '\n------ILLEGAL 04------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-04
echo '\n------ILLEGAL 05------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-05
echo '\n------ILLEGAL 06------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-06
echo '\n------ILLEGAL 07------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-07
echo '\n------ILLEGAL 08------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-08
echo '\n------ILLEGAL 09------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-09
echo '\n------ILLEGAL 10------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-10
echo '\n------ILLEGAL 11------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-11
echo '\n------ILLEGAL 12------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-12
echo '\n------ILLEGAL 13------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-13
echo '\n------ILLEGAL 14------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-14
echo '\n------ILLEGAL 15------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-15
echo '\n------ILLEGAL 16------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-16
echo '\n------ILLEGAL 17------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-17
echo '\n------ILLEGAL 18------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-18
echo '\n------ILLEGAL 19------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-19
echo '\n------ILLEGAL 20------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/illegal-20
