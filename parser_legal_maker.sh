#!/bin/sh
echo '------COMPILANDO...------\n'
ant

echo '\n------LEGAL 01------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-01
echo '\n------LEGAL 02------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-02
echo '\n------LEGAL 03------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-03
echo '\n------LEGAL 04------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-04
echo '\n------LEGAL 05------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-05
echo '\n------LEGAL 06------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-06
echo '\n------LEGAL 07------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-07
echo '\n------LEGAL 08------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-08
echo '\n------LEGAL 09------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-09
echo '\n------LEGAL 10------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-10
echo '\n------LEGAL 11------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-11
echo '\n------LEGAL 12------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-12
echo '\n------LEGAL 13------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-13
echo '\n------LEGAL 14------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-14
echo '\n------LEGAL 15------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-15
echo '\n------LEGAL 16------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-16
echo '\n------LEGAL 17------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-17
echo '\n------LEGAL 18------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-18
