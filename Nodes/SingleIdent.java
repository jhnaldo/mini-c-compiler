// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class SingleIdent extends Ident {
    Number val;

    public SingleIdent(String n, Pos s, Pos e) {
        name = n;
        val = 0;
        start = s;
        end = e;
    }

    public boolean is_array(){ return false; }

    public void show_ast_c_ver(){
        writer.print(name);
    }

    public SingleIdent semantic_analysis(){
        return this;
    }
}
