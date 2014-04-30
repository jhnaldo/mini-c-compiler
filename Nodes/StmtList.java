// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class StmtList extends Absyn {
    ArrayList<Stmt> arr;

    public StmtList(Pos s, Pos e) {
        arr = new ArrayList<Stmt>();
        start = s;
        end = e;
    }

    public void add(Stmt s) {
        arr.add(s);
        if (arr.size() == 1)
            start = s.start;
        end = s.end;
    }

    public void show_ast_c_ver(){
        for(Stmt s : arr){
            s.show_ast_c_ver();
        }
    }

    public void show_sym_table(){
        for(Stmt s : arr){
            if(s instanceof CompStmt){
                Integer temp=comp_count+1;
                comp_count=0;
                cur_func_name.add("compound("+temp+")");
                show_func_name();
                s.show_sym_table();
                comp_count=temp;
                cur_func_name.remove(cur_func_name.size()-1);
            }
            s.show_sym_table();
        }
    }
}
