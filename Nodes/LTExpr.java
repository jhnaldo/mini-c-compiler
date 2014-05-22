// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class LTExpr extends BinaryExpr {
    public LTExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "<";
    }

    public LTExpr semantic_analysis(){
        LTExpr e = new LTExpr(null, null, start, end);
        e.left_expr = left_expr.semantic_analysis();
        e.right_expr = right_expr.semantic_analysis();
        e.expr_check();
        return e;
    }
}
