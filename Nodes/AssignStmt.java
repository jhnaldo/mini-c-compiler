// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class AssignStmt extends Stmt {
    Assign assign;

    public AssignStmt(Assign as, Pos s, Pos e) {
        assign = as;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("");
        assign.show_ast_c_ver();
        writer.println(";");
    }
}
