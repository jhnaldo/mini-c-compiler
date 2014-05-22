// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class ParenExpr extends Expr {
    Expr expr;
    public ParenExpr(Expr ex, Pos s, Pos e){
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print("(");
        expr.show_ast_c_ver();
        writer.print(")");
    }

    public ParenExpr semantic_analysis(){
        ParenExpr pe = new ParenExpr(null, start, end);
        pe.expr = expr.semantic_analysis();
        pe.tn = pe.expr.tn;
        return pe;
    }
}
