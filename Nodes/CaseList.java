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

    public void add(CaseStmt c){
        arr.add(c);
        if (arr.size() == 1)
            start = c.start;
        end = c.end;
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

    public CaseList semantic_analysis(){
        CaseList cl = new CaseList(start, end);

        int cur_label_num = label_num++;
        int case_label_num = cur_label_num;
        label_num += arr.size();
        for(CaseStmt c : arr){
            case_label_num++;
            writer.println("LAB _L"+case_label_num);
            cl.add(c.semantic_analysis());
            if(c.has_break){
                writer.println("    JMP   _L"+cur_label_num);
            }
        }
        return cl;
    }
}
