// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class EqualExpr extends BinaryExpr {
    public EqualExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "==";
    }

    public EqualExpr semantic_analysis(){
        EqualExpr me = new EqualExpr(null, null, start, end);
        me.left_expr = left_expr.semantic_analysis();
        me.right_expr = right_expr.semantic_analysis();
        return me;
    }
}
