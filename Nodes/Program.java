// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Program extends Absyn {
    DeclList decls;
    FuncList funcs;

    public Program(DeclList dl, FuncList fl, Pos s, Pos e) {
        decls = dl;
        funcs = fl;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        if(decls!=null) decls.show_ast_c_ver();
        if(funcs!=null) funcs.show_ast_c_ver();
    }

    public void show_sym_table(){
        show_func_name();
        if(decls!=null) decls.show_sym_table();
        if(funcs!=null) funcs.show_sym_table();
    }
}
