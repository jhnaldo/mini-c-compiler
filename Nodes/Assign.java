// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Assign extends Absyn {
    String name;
    Expr index, expr;

    public Assign(String n, Expr idx, Expr ex, Pos s, Pos e) {
        name = n;
        index = idx;
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print(name);
        if(index!=null){
            writer.print("[");
            index.show_ast_c_ver();
            writer.print("]");
        }
        writer.print(" = ");
        expr.show_ast_c_ver();
    }
}
