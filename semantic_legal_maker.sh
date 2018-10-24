#!/bin/sh
echo '------COMPILANDO...------\n'
ant

echo '\n------LEGAL 01------\n'
java -jar dist/Compiler.jar -target inter ../semantics/legal-01.dcf