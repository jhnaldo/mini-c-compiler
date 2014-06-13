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
        block_idx++;
        writer.println("    MOVE  VR(0)@ VR("+block_idx+")");
        e.right_expr = right_expr.semantic_analysis();
        e.expr_check();
        if(tn == TypeName.FLOAT){
        }else{
            writer.println("    SUB   VR("+block_idx+")@ VR(0)@ VR(0)");
            writer.println("    MOVE  1 VR("+block_idx+")");
            writer.println("    JMPN  VR(0)@ _L"+label_num);
            writer.println("    MOVE  0 VR("+block_idx+")");
            writer.println("LAB _L"+label_num);
            writer.println("    MOVE  VR("+block_idx+")@ VR(0)");
        }
        e.tn = TypeName.INT;
        block_idx--;
        label_num++;
        return e;
    }
}
