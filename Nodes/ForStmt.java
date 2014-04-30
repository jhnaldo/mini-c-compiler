// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class ForStmt extends Stmt {
    Assign initial;
    Expr condition;
    Assign incl;
    Stmt stmt;

    public ForStmt(Assign init, Expr cond, Assign as, Stmt st, Pos s, Pos e) {
        initial = init;
        condition = cond;
        incl = as;
        stmt = st;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("for(");
        initial.show_ast_c_ver();
        writer.print("; ");
        condition.show_ast_c_ver();
        writer.print("; ");
        incl.show_ast_c_ver();
        writer.println(")");
        if(!CompStmt.class.isInstance(stmt)) level++;
        stmt.show_ast_c_ver();
        if(!CompStmt.class.isInstance(stmt)) level--;
    }

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        cur_func_name.add("for("+temp+")");
        show_func_name();
        stmt.show_sym_table();
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);
    }
}
