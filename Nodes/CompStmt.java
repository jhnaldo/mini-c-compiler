// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class CompStmt extends Stmt{
    DeclList decls;
    StmtList stmts;

    public CompStmt(DeclList dl, StmtList sl, Pos s, Pos e) {
        decls = dl;
        stmts = sl;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        println("{");
        level++;
        if(decls!=null)decls.show_ast_c_ver();
        stmts.show_ast_c_ver();
        level--;
        println("}");
    }

    public void show_sym_table(){
        if(decls!=null)decls.show_sym_table();
        stmts.show_sym_table();
    }
}
