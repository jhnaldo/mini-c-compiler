// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Param extends Absyn {
    Type typ;
    Ident ident;

    public Param(Type t, Ident id){
        typ = t;
        ident = id;
    }

    public void show_ast_c_ver(){
        typ.show_ast_c_ver();
        writer.print(" ");
        ident.show_ast_c_ver();
    }
}
