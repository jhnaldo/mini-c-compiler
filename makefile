JAVAC = javac
JAVA = java
CLASSPATH = .

init:
	$(JAVAC) JLex/*.java
	$(JAVAC) java_cup/runtime/*.java
	$(JAVAC) java_cup/*.java

parse: mini-c.cup
	$(JAVA) -classpath $(CLASSPATH) java_cup.Main mini-c.cup

lex: mini-c.lex
	$(JAVA) JLex.Main mini-c.lex
	mv mini-c.lex.java Yylex.java

build: sym.java parser.java Yylex.java
	$(JAVAC) Nodes/*.java
	$(JAVAC) sym.java parser.java Yylex.java

all:
	$(JAVA) -classpath $(CLASSPATH) java_cup.Main mini-c.cup
	$(JAVA) JLex.Main mini-c.lex
	mv mini-c.lex.java Yylex.java
	$(JAVAC) Nodes/*.java
	$(JAVAC) -classpath $(CLASSPATH) sym.java parser.java Yylex.java

run:
	$(JAVA) -classpath $(CLASSPATH) parser

test:
	$(JAVA) -classpath $(CLASSPATH) parser < test_inputs/my_test.c

clean:
	-rm Nodes/*.class
	-rm *.class
	-rm *.txt
	-rm Yylex.java
	-rm parser.java
	-rm sym.java

vclean:
	-rm */*.class
	-rm *.txt
	-rm Yylex.java
	-rm parser.java
	-rm sym.java
	-rm JLex/*.class
