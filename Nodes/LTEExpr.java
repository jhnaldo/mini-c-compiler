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
        block_idx++;
        writer.println("    MOVE  VR(0)@ VR("+block_idx+")");
        e.right_expr = right_expr.semantic_analysis();
        e.expr_check();
        if(tn == TypeName.FLOAT){
            writer.println("    FSUB  VR(0)@ VR("+block_idx+")@ VR(0)");
            writer.println("    FADD  VR(0)@ 1.0 VR(0)");
            writer.println("    F2I   VR(0)@ VR(0)");
            writer.println("    SUB   VR(0)@ 1 VR(0)");
        }else{
            writer.println("    SUB   VR(0)@ VR("+block_idx+")@ VR(0)");
        }
        writer.println("    MOVE  0 VR("+block_idx+")");
        writer.println("    JMPN  VR(0)@ _L"+label_num);
        writer.println("    MOVE  1 VR("+block_idx+")");
        writer.println("LAB _L"+label_num);
        writer.println("    MOVE  VR("+block_idx+")@ VR(0)");

        e.tn = TypeName.INT;
        block_idx--;
        label_num++;
        return e;
    }
}
