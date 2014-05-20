// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class FuncList extends Absyn {
    ArrayList<Func> arr;

    public FuncList(){
        arr = new ArrayList<Func>();
    }

    public FuncList(Func func, Pos s, Pos e) {
        arr = new ArrayList<Func>();
        arr.add(func);
        start = s;
        end = e;
    }

    public void add(Func func) {
        arr.add(func);
        end = func.end;
    }

    public void show_ast_c_ver(){
        for(Func f : arr){
            f.show_ast_c_ver();
        }
    }

    public void show_sym_table(){
        for(Func f : arr){
            f.show_sym_table();
        }
    }

    public FuncList semantic_analysis(){
        FuncList fl = new FuncList();
        for(Func f : arr){
            fl.add(f.semantic_analysis());
        }
        fl.start = start;
        fl.end = end;
        return fl;
    }
}
