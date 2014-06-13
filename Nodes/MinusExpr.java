// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class MinusExpr extends BinaryExpr {
    public MinusExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "-";
    }

    public MinusExpr semantic_analysis(){
        MinusExpr e = new MinusExpr(null, null, start, end);

        e.left_expr = left_expr.semantic_analysis();
        block_idx++;
        writer.println("    MOVE  VR(0)@ VR("+block_idx+")");
        e.right_expr = right_expr.semantic_analysis();
        e.expr_check();
        if(tn == TypeName.FLOAT){
            writer.println("    FSUB  VR("+block_idx+")@ VR(0)@ VR(0)");
        }else{
            writer.println("    SUB   VR("+block_idx+")@ VR(0)@ VR(0)");
        }
        block_idx--;
        return e;
    }
}
