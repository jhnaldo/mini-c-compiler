// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class ArgList extends Absyn {
    ArrayList<Expr> arr;

    public ArgList(){
        arr = new ArrayList<Expr>();
    }

    public ArgList(Expr ex, Pos s, Pos e) {
        arr = new ArrayList<Expr>();
        arr.add(ex);
        start = s;
        end = e;
    }

    public void add(Expr ex) {
        arr.add(ex);
        end = ex.end;
    }

    public void show_ast_c_ver(){
        arr.get(0).show_ast_c_ver();
        for(Expr e : arr.subList(1,arr.size())){
            writer.print(", ");
            e.show_ast_c_ver();
        }
    }
}
