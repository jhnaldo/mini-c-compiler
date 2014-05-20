// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class ArrayIdent extends Ident {
    Integer size;

    public ArrayIdent(String n, Integer si, Pos s, Pos e) {
        name=n;
        size=si;
        start = s;
        end = e;
    }

    public boolean is_array(){ return true; }

    public void show_ast_c_ver(){
        writer.print(name+"["+size+"]");
    }

    public ArrayIdent semantic_analysis(){
        return this;
    }
}
