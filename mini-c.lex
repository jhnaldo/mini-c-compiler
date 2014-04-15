// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

import java_cup.runtime.*;

%%

%cup
%line
%char

%{
    private Symbol tok(int kind) {
        return new Symbol(kind, yyline+1, yychar+1);
    }

    private Symbol tok(int kind, Object value) {
        return new Symbol(kind, yyline+1, yychar+1, value);
    }

    private void yyline() {
        yychar = -1;
    }
%}

ID          = [A-Za-z][A-Za-z0-9_]*
INTNUM      = [0-9]+
FLOATNUM    = [0-9]+\.[0-9]+
NEW_LINE    = (\n|\r)
WHITE_SPACE = (\ |\t|\f)
%%

"*"             { return tok(sym.MULT); }
"/"             { return tok(sym.DIV); }
"+"             { return tok(sym.PLUS); }
"-"             { return tok(sym.MINUS); }
"<"             { return tok(sym.LT); }
">"             { return tok(sym.GT); }
"<="            { return tok(sym.LTE); }
">="            { return tok(sym.GTE); }
"=="            { return tok(sym.EQ); }
"!="            { return tok(sym.NEQ); }

"="             { return tok(sym.ASSIGN); }
"("             { return tok(sym.LPAREN); }
")"             { return tok(sym.RPAREN); }
"{"             { return tok(sym.LBRACE); }
"}"             { return tok(sym.RBRACE); }
"["             { return tok(sym.LSBRACE); }
"]"             { return tok(sym.RSBRACE); }
","             { return tok(sym.COMMA); }
";"             { return tok(sym.SEMI); }
":"             { return tok(sym.COLON); }

"int"           { return tok(sym.INT); }
"float"         { return tok(sym.FLOAT); }
"return"        { return tok(sym.RETURN); }
"while"         { return tok(sym.WHILE); }
"do"            { return tok(sym.DO); }
"for"           { return tok(sym.FOR); }
"if"            { return tok(sym.IF); }
"else"          { return tok(sym.ELSE); }
"switch"        { return tok(sym.SWITCH); }
"case"          { return tok(sym.CASE); }
"default"       { return tok(sym.DEFAULT); }
"break"         { return tok(sym.BREAK); }

{ID}            { return tok(sym.ID, yytext()); }
{FLOATNUM}      { return tok(sym.FLOATNUM, new Float(yytext())); }
{INTNUM}        { return tok(sym.INTNUM, new Integer(yytext())); }

{WHITE_SPACE}   { /* ignore white space. */ }
{NEW_LINE}      { yyline(); }

.               { System.err.println("Illegal character: "+yytext()); }
