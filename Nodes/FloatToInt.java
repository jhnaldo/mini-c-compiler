// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class FloatToInt extends Expr{
    Expr expr;

    public FloatToInt(Expr e){
        expr = e;
        tn = TypeName.INT;
    }

    public void show_ast_c_ver(){
        writer.print("__float2int__(");
        expr.show_ast_c_ver();
        writer.print(")");
    }
}
