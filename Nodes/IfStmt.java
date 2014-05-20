// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class IfStmt extends Stmt {
    Expr condition;
    Stmt then_stmt, else_stmt;

    public IfStmt(Expr cond, Stmt th, Stmt el, Pos s, Pos e) {
        condition = cond;
        then_stmt = th;
        else_stmt = el;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("if(");
        condition.show_ast_c_ver();
        writer.println(")");
        if(!CompStmt.class.isInstance(then_stmt)) level++;
        then_stmt.show_ast_c_ver();
        if(!CompStmt.class.isInstance(then_stmt)) level--;

        if(else_stmt!=null){
            println("else");
            if(!CompStmt.class.isInstance(else_stmt)) level++;
            else_stmt.show_ast_c_ver();
            if(!CompStmt.class.isInstance(else_stmt)) level--;
        }
    }

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        cur_func_name.add("if("+temp+")");
        show_func_name();
        then_stmt.show_sym_table();
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);

        if(else_stmt!=null){
            temp=comp_count+1;
            comp_count=0;
            cur_func_name.add("else("+temp+")");
            show_func_name();
            else_stmt.show_sym_table();
            comp_count=temp;
            cur_func_name.remove(cur_func_name.size()-1);
        }
    }

    public IfStmt semantic_analysis(){
        IfStmt f = new IfStmt(null, null, null, start, end);
        f.condition = condition.semantic_analysis();
        f.then_stmt = then_stmt.semantic_analysis();
        if(else_stmt!=null) f.else_stmt = else_stmt.semantic_analysis();
        return f;
    }
}
