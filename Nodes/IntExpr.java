// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class IntExpr extends Expr {
    Integer num;

    public IntExpr(Integer k, Pos s, Pos e){
        num = k;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print(num);
    }
}
