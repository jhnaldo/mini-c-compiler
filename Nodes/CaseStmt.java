// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class CaseStmt extends Stmt {
    Integer num;
    StmtList stmts;
    Boolean has_break;

    public CaseStmt(Integer k, StmtList sl, Boolean hb){
        num = k;
        stmts = sl;
        has_break = hb;
    }

    public void show_ast_c_ver(){
        println("case "+num+":");
        level++;
        stmts.show_ast_c_ver();
        if(has_break) println("break;");
        level--;
    }

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        cur_func_name.add("case("+temp+")");
        show_func_name();
        stmts.show_sym_table();
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);
    }
}
