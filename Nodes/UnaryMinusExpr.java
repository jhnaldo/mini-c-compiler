// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class UnaryMinusExpr extends UnaryExpr {
    public UnaryMinusExpr(Expr ex, Pos s, Pos e){
        super(ex,s,e);
        symbol = "-";
    }

    public UnaryMinusExpr semantic_analysis(){
        UnaryMinusExpr ume = new UnaryMinusExpr(null, start, end);
        ume.expr = expr.semantic_analysis();
        if(ume.expr.tn==TypeName.INT){
            writer.println("    SUB   0 VR(0)@ VR(0)");
        }else if(ume.expr.tn==TypeName.FLOAT){
            writer.println("    FSUB  0 VR(0)@ VR(0)");
        }else{
            semantic_error(ume,"This expression should have int or float type.");
        }
        ume.tn = ume.expr.tn;
        return ume;
    }
}
