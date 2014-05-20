// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Program extends Absyn {
    DeclList decls;
    FuncList funcs;

    public Program(){
        decls=null;
        funcs=null;
    }

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

    public Program semantic_analysis(){
        sym_table_arr.add(new SymbolTable("GLOBAL"));
        cur_sym_table = sym_table_arr.get(sym_table_arr.size()-1);
        SymbolTable temp_sym_table = cur_sym_table;

        Program p = new Program();
        if(decls!=null) p.decls = decls.semantic_analysis();
        if(funcs!=null) p.funcs = funcs.semantic_analysis();
        p.start = start;
        p.end = end;

        sym_table_arr.remove(temp_sym_table);
        return p;
    }
}
