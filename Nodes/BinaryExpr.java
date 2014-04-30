// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class BinaryExpr extends Expr{
    Expr left_expr;
    Expr right_expr;
    String symbol;

    public BinaryExpr(Expr le, Expr re, Pos s, Pos e){
        left_expr = le;
        right_expr = re;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        left_expr.show_ast_c_ver();
        writer.print(symbol);
        right_expr.show_ast_c_ver();
    }
}
