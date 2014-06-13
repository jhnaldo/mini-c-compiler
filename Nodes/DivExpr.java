// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class DivExpr extends BinaryExpr {
    public DivExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "/";
    }

    public DivExpr semantic_analysis(){
        DivExpr e = new DivExpr(null, null, start, end);

        e.left_expr = left_expr.semantic_analysis();
        block_idx++;
        writer.println("    MOVE  VR(0)@ VR("+block_idx+")");
        e.right_expr = right_expr.semantic_analysis();
        e.expr_check();
        if(tn == TypeName.FLOAT){
            writer.println("    FDIV  VR("+block_idx+")@ VR(0)@ VR(0)");
        }else{
            writer.println("    DIV   VR("+block_idx+")@ VR(0)@ VR(0)");
        }
        block_idx--;
        return e;
    }
}
