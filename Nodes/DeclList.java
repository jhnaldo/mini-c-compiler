// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class DeclList extends Absyn {
    ArrayList<Decl> arr;

    public DeclList(){
        arr = new ArrayList<Decl>();
    }

    public DeclList(Decl decl, Pos s, Pos e) {
        arr = new ArrayList<Decl>();
        arr.add(decl);
        start = s;
        end = e;
    }

    public void add(Decl decl) {
        arr.add(decl);
        end = decl.end;
    }

    public void show_ast_c_ver(){
        for(Decl d : arr){
            d.show_ast_c_ver();
        }
    }

    public void show_sym_table(){
        for(Decl d : arr){
            d.show_sym_table();
        }
    }

    public DeclList semantic_analysis(){
        DeclList dl = new DeclList();
        for(Decl d : arr){
            dl.add(d.semantic_analysis());
        }
        dl.start = start;
        dl.end = end;
        is_func = false;
        return dl;
    }
}
