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

    public CompStmt semantic_analysis(){
        sym_table_arr.add(new SymbolTable("compound"));
        cur_sym_table = sym_table_arr.get(sym_table_arr.size()-1);
        SymbolTable temp_sym_table = cur_sym_table;

        CompStmt cp = new CompStmt(null, null, start, end);
        if(decls!=null){
            int cur_rel_pos = rel_pos;
            cp.decls = decls.semantic_analysis();
            cp.stmts = stmts.semantic_analysis();
            writer.println("    ADD   SP@ "+(cur_rel_pos-rel_pos)+" SP");
            rel_pos = cur_rel_pos;
        }else{
            cp.stmts = stmts.semantic_analysis();
        }

        sym_table_arr.remove(temp_sym_table);
        return cp;
    }
}
