// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class UnaryExpr extends Expr{
    Expr expr;
    String symbol;

    public UnaryExpr(Expr ex, Pos s, Pos e){
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print(symbol);
        expr.show_ast_c_ver();
    }
}
