// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class NullStmt extends Stmt{
    public NullStmt(Pos s, Pos e) {
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        println(";");
    }
}
