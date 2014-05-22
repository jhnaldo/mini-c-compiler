// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class IntToFloat extends Expr{
    Expr expr;

    public IntToFloat(Expr e){
        expr = e;
        tn = TypeName.FLOAT;
    }

    public void show_ast_c_ver(){
        writer.print("__int2float__(");
        expr.show_ast_c_ver();
        writer.print(")");
    }
}
