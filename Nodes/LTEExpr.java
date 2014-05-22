// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class LTEExpr extends BinaryExpr {
    public LTEExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "<=";
    }

    public LTEExpr semantic_analysis(){
        LTEExpr e = new LTEExpr(null, null, start, end);
        e.left_expr = left_expr.semantic_analysis();
        e.right_expr = right_expr.semantic_analysis();
        e.expr_check();
        e.tn = TypeName.INT;
        return e;
    }
}
