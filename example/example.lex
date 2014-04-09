// Copyright (c) 2010 Gavin Harrison
// See LICENSE for GPLv3 Terms

import java_cup.runtime.Symbol;
%%
%cup
%%

"var"			{return new Symbol(sym.VAR); }
"begin"			{return new Symbol(sym.BEGIN); }
"end"			{return new Symbol(sym.END); }
"if"			{return new Symbol(sym.IF); }
"then"			{return new Symbol(sym.THEN); }
"while"			{return new Symbol(sym.WHILE); }
"do"			{return new Symbol(sym.DO); }
"odd"			{return new Symbol(sym.ODD); }
"print"			{return new Symbol(sym.PRINT); }

"="				{return new Symbol(sym.EQ); }
"#"				{return new Symbol(sym.NEQ); }
"<"				{return new Symbol(sym.LT); }
"<="			{return new Symbol(sym.LTE); }
">"				{return new Symbol(sym.GT); }
">="			{return new Symbol(sym.GTE); }

"("				{return new Symbol(sym.LPAREN); }
")"				{return new Symbol(sym.RPAREN); }
","				{return new Symbol(sym.COMMA); }
";"				{return new Symbol(sym.SEMI); }
":="			{return new Symbol(sym.ASSIGN); }
"+"				{return new Symbol(sym.PLUS); }
"-"				{return new Symbol(sym.MINUS); }
"*"				{return new Symbol(sym.MULT); }
"/"				{return new Symbol(sym.DIV); }
"."				{return new Symbol(sym.PERIOD); }

[0-9]+			{return new Symbol(sym.INT, new Integer(yytext())); }
[a-zA-Z]+ 		{return new Symbol(sym.ID, yytext()); }

[ \t\r\n\f]			{/* ignore white space */}
. {System.err.println("Illegal character: "+yytext());}
