// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Call extends Absyn {
    String name;
    ArgList args;

    public Call(String n, ArgList al, Pos s, Pos e){
        name = n;
        args = al;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print(name+"(");
        if(args!=null)args.show_ast_c_ver();
        writer.print(")");
    }
}
