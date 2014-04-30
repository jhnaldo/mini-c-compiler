// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class SwitchStmt extends Stmt {
    Ident ident;
    CaseList cases;
    StmtList default_stmt;
    Boolean default_has_break;

    public SwitchStmt(Ident id, CaseList cl, StmtList ds, Boolean dhb, Pos s, Pos e) {
        ident = id;
        cases = cl;
        default_stmt = ds;
        default_has_break = dhb;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("switch(");
        ident.show_ast_c_ver();
        writer.println(")");
        println("{");
        level++;
        cases.show_ast_c_ver();
        if(default_stmt!=null){
            println("default:");
            level++;
            default_stmt.show_ast_c_ver();
            if(default_has_break){
                println("break;");
            }
            level--;
        }
        level--;
        println("}");
    }

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        cur_func_name.add("switch("+temp+")");
        show_func_name();
        cases.show_sym_table();
        if(default_stmt!=null){
            temp=comp_count+1;
            comp_count=0;
            cur_func_name.add("default("+temp+")");
            show_func_name();
            default_stmt.show_sym_table();
            comp_count=temp;
            cur_func_name.remove(cur_func_name.size()-1);
        }
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);
    }
}
