// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class FloatExpr extends Expr {
    Float num;

    public FloatExpr(Float k, Pos s, Pos e){
        num = k;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print(num);
    }
}
