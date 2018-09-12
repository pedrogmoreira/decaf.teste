#!/bin/sh
echo '------COMPILANDO...------\n'
ant

echo '\n------LEGAL 1------\n'
java -jar dist/Compiler.jar -target parser -debug ../parser/legal-01
