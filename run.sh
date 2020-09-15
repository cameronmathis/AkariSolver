#!/bin/bash
# compile the program in Java
javac ./src/*.java
# execute the program
java -cp ./src Main $1 $2