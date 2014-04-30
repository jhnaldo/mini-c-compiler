// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class RetStmt extends Stmt {
    Expr expr;

    public RetStmt(Expr ex, Pos s, Pos e) {
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("return ");
        if(expr!=null)expr.show_ast_c_ver();
        writer.println(";");
    }
}
