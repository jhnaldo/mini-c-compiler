// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Func extends Absyn {
    Type typ;
    String name;
    ParamList params;
    CompStmt comp_stmt;

    public Func(){
        typ=null;
        name="";
        params=null;
        comp_stmt=null;
    }

    public Func(Type t, String fn, ParamList pl, CompStmt cs, Pos s, Pos e) {
        typ = t;
        name = fn;
        params = pl;
        comp_stmt = cs;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        typ.show_ast_c_ver();
        writer.print(" "+name+" (");
        if(params!=null){
            params.show_ast_c_ver();
        }
        writer.println(")");
        comp_stmt.show_ast_c_ver();
    }

    public void show_sym_table(){
        sym_count=0;
        comp_count=0;
        cur_func_name.add(name);
        show_func_name();
        if(params!=null) params.show_sym_table();
        comp_stmt.show_sym_table();
        cur_func_name.remove(cur_func_name.size()-1);
    }

    public Func semantic_analysis(){
        rel_pos = 1;
        writer.println("LAB "+name);

        sym_table_arr.add(new SymbolTable(name));
        cur_sym_table = sym_table_arr.get(sym_table_arr.size()-1);
        cur_fun_table = get_fun_table(name);
        SymbolTable temp_sym_table = cur_sym_table;

        Func f = new Func();
        if(params!=null) f.params = params.semantic_analysis();
        is_func = true;
        f.comp_stmt = comp_stmt.semantic_analysis();
        f.typ=typ;
        f.name=name;

        writer.println("    MOVE  FP@ SP");
        writer.println("    MOVE  MEM(FP@)@ FP");
        writer.println("    SUB   SP@ 1 SP");
        writer.println("    JMP   MEM(SP@)@");

        sym_table_arr.remove(temp_sym_table);
        return f;
    }
}
