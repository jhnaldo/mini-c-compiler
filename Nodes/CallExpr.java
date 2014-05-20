// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class CallExpr extends Expr {
    Call call;

    public CallExpr(Call c, Pos s, Pos e){
        call = c;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        call.show_ast_c_ver();
    }

    public CallExpr semantic_analysis(){
        CallExpr ce = new CallExpr(null, start, end);
        ce.call = call.semantic_analysis();
        return ce;
    }
}
