// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

import java_cup.runtime.*;

%%

%cup

ID          = [A-Za-z][A-Za-z0-9_]*
INTNUM      = [0-9]+
FLOATNUM    = [0-9]+\.[0-9]+
NEW_LINE    = (\n|\r)
WHITE_SPACE = (\ |\t|\f)

%%

"-"             { return new Symbol(sym.UMINUS); }

"*"             { return new Symbol(sym.MULT); }
"/"             { return new Symbol(sym.DIV); }
"+"             { return new Symbol(sym.PLUS); }
"-"             { return new Symbol(sym.MINUS); }
"<"             { return new Symbol(sym.LT); }
">"             { return new Symbol(sym.GT); }
"<="            { return new Symbol(sym.LTE); }
">="            { return new Symbol(sym.GTE); }
"=="            { return new Symbol(sym.EQ); }
"!="            { return new Symbol(sym.NEQ); }

"="             { return new Symbol(sym.ASSIGN); }
"("             { return new Symbol(sym.LPAREN); }
")"             { return new Symbol(sym.RPAREN); }
"{"             { return new Symbol(sym.LBRACE); }
"}"             { return new Symbol(sym.RBRACE); }
"["             { return new Symbol(sym.LSBRACE); }
"]"             { return new Symbol(sym.RSBRACE); }
","             { return new Symbol(sym.COMMA); }
";"             { return new Symbol(sym.SEMI); }
":"             { return new Symbol(sym.COLON); }

"int"           { return new Symbol(sym.INT); }
"float"         { return new Symbol(sym.FLOAT); }
"return"        { return new Symbol(sym.RETURN); }
"while"         { return new Symbol(sym.WHILE); }
"do"            { return new Symbol(sym.DO); }
"for"           { return new Symbol(sym.FOR); }
"if"            { return new Symbol(sym.IF); }
"else"          { return new Symbol(sym.ELSE); }
"switch"        { return new Symbol(sym.SWITCH); }
"case"          { return new Symbol(sym.CASE); }
"default"       { return new Symbol(sym.DEFAULT); }
"break"         { return new Symbol(sym.BREAK); }

{ID}            { return new Symbol(sym.ID, yytext()); }
{FLOATNUM}      { return new Symbol(sym.FLOATNUM, new Float(yytext())); }
{INTNUM}        { return new Symbol(sym.INTNUM, new Integer(yytext())); }

{WHITE_SPACE}   { /* ignore white space. */ }
{NEW_LINE}      { /* ignore new line. */ }

.               { System.err.println("Illegal character: "+yytext()); }
