// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class SingleIdExpr extends Expr {
    String name;

    public SingleIdExpr(String n, Pos s, Pos e){
        name = n;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print(name);
    }
}
