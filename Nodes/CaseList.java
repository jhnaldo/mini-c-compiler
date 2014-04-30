// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class CaseList extends Absyn {
    ArrayList<CaseStmt> arr;

    public CaseList(Pos s, Pos e) {
        arr = new ArrayList<CaseStmt>();
        start = s;
        end = e;
    }

    public void add(Integer k, StmtList sl, Boolean hb, Pos s, Pos e){
        arr.add(new CaseStmt(k, sl, hb));
        if (arr.size() == 1)
            start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        for(CaseStmt c : arr){
            c.show_ast_c_ver();
        }
    }

    public void show_sym_table(){
        for(CaseStmt c : arr){
            c.show_sym_table();
        }
    }
}
