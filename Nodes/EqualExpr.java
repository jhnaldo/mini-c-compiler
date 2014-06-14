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
        EqualExpr e = new EqualExpr(null, null, start, end);

        e.left_expr = left_expr.semantic_analysis();
        block_idx++;
        writer.println("    MOVE  VR(0)@ VR("+block_idx+")");
        e.right_expr = right_expr.semantic_analysis();
        e.expr_check();
        if(tn == TypeName.FLOAT){
            writer.println("    FSUB  VR("+block_idx+")@ VR(0)@ VR("+(block_idx+1)+")");
            writer.println("    FADD  VR("+(block_idx+1)+")@ 1.0 VR("+(block_idx+1)+")");
            writer.println("    F2I   VR("+(block_idx+1)+")@ VR("+(block_idx+1)+")");
            writer.println("    SUB   VR("+(block_idx+1)+")@ 1 VR("+(block_idx+1)+")");

            writer.println("    FSUB  VR(0)@ VR("+block_idx+")@ VR("+(block_idx+2)+")");
            writer.println("    FADD  VR("+(block_idx+2)+")@ 1.0 VR("+(block_idx+2)+")");
            writer.println("    F2I   VR("+(block_idx+2)+")@ VR("+(block_idx+2)+")");
            writer.println("    SUB   VR("+(block_idx+2)+")@ 1 VR("+(block_idx+2)+")");

            writer.println("    MOVE  0 VR("+block_idx+")");
            writer.println("    JMPN  VR("+(block_idx+1)+")@ _L"+label_num);
            writer.println("    JMPN  VR("+(block_idx+2)+")@ _L"+label_num);
            writer.println("    MOVE  1 VR("+block_idx+")");
            writer.println("LAB _L"+label_num);
            writer.println("    MOVE  VR("+block_idx+")@ VR(0)");
        }else{
            writer.println("    SUB   VR("+block_idx+")@ VR(0)@ VR(0)");
            writer.println("    MOVE  1 VR("+block_idx+")");
            writer.println("    JMPZ  VR(0)@ _L"+label_num);
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
