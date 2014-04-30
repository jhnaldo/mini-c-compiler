// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class CallStmt extends Stmt {
    Call call;

    public CallStmt(Call c, Pos s, Pos e){
        call = c;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("");
        call.show_ast_c_ver();
        writer.println(";");
    }
}
