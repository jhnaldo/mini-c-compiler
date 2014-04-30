// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class WhileStmt extends Stmt {
    Expr expr;
    Stmt stmt;
    Boolean is_do;

    public WhileStmt(Expr ex, Stmt st, Boolean d, Pos s, Pos e) {
        expr = ex;
        stmt = st;
        is_do = d;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        if(is_do){
            println("do");
            if(!CompStmt.class.isInstance(stmt)) level++;
            stmt.show_ast_c_ver();
            if(!CompStmt.class.isInstance(stmt)) level--;
            print("while(");
            expr.show_ast_c_ver();
            writer.println(");");
        }else{
            print("while(");
            expr.show_ast_c_ver();
            writer.println(");");
            if(!CompStmt.class.isInstance(stmt)) level++;
            stmt.show_ast_c_ver();
            if(!CompStmt.class.isInstance(stmt)) level--;
        }
    }

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        if(is_do){
            cur_func_name.add("do_while("+temp+")");
        }else{
            cur_func_name.add("while("+temp+")");
        }
        show_func_name();
        stmt.show_sym_table();
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);
    }
}
