// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class ArrayIdExpr extends Expr {
    String name;
    Expr expr;

    public ArrayIdExpr(String n, Expr ex, Pos s, Pos e){
        name = n;
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print(name+"[");
        expr.show_ast_c_ver();
        writer.print("]");
    }
}
