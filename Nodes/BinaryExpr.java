// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class BinaryExpr extends Expr{
    Expr left_expr;
    Expr right_expr;
    String symbol;

    public BinaryExpr(Expr le, Expr re, Pos s, Pos e){
        left_expr = le;
        right_expr = re;
        start = s;
        end = e;
    }

    public void expr_check(){
        if(left_expr.tn != TypeName.FLOAT && left_expr.tn != TypeName.INT){
            semantic_error(left_expr, "This expression should have int or float type.");
        }
        if(right_expr.tn != TypeName.FLOAT && right_expr.tn != TypeName.INT){
            semantic_error(right_expr, "This expression should have int or float type.");
        }
        if(left_expr.tn == right_expr.tn){
            tn = left_expr.tn;
        }else if(left_expr.tn == TypeName.FLOAT){
            semantic_warning(right_expr, "This expression should have float type value.");
            right_expr = new IntToFloat(right_expr);
            tn = TypeName.FLOAT;
        }else{
            semantic_warning(left_expr, "This expression should have float type value.");
            left_expr = new IntToFloat(left_expr);
            tn = TypeName.FLOAT;
        }
    }

    public void show_ast_c_ver(){
        left_expr.show_ast_c_ver();
        writer.print(symbol);
        right_expr.show_ast_c_ver();
    }
}
