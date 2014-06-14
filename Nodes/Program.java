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

        for(Func f : funcs.arr){
            // redefine check
            if(get_fun_table(f.name)!=null)
                semantic_error(this,"Redefine function "+f.name+".");
            if(sym_table_arr.get(0).hash.containsKey(f.name))
                semantic_error(this,"Duplicated function "+f.name+" with global variable.");
            FuncTable ft = new FuncTable(f.name, f.typ);

            fun_table_arr.add(ft);

            if(f.params!=null){
                int k = -(f.params.arr.size()+1);
                for(Param p : f.params.arr){
                    Type typ = p.typ;
                    Ident id = p.ident;

                    if(id.is_array()){
                        ArrayIdent aid = (ArrayIdent)id;
                        ft.add(typ, aid.size, SymbolRole.PARAM, k);
                    }else{
                        SingleIdent sid = (SingleIdent)id;
                        ft.add(typ, -1, SymbolRole.PARAM, k);
                    }
                    k++;
                }
            }
        }

        writer.println("    AREA  SP");
        writer.println("    AREA  FP");
        writer.println("    AREA  VR");
        writer.println("    AREA  MEM");
        writer.println("LAB START");
        writer.println("    MOVE  END MEM(0)");
        writer.println("    MOVE  0 MEM(1)");
        writer.println("    MOVE  1 SP");
        writer.println("    MOVE  1 FP");

        Program p = new Program();
        if(decls!=null) p.decls = decls.semantic_analysis();
        push("END");
        push(0);
        writer.println("    MOVE  SP@ FP");
        writer.println("    JMP   main");
        if(funcs!=null) p.funcs = funcs.semantic_analysis();
        p.start = start;
        p.end = end;
        sym_table_arr.remove(temp_sym_table);

        // main function check
        if(get_fun_table("main")==null)
            semantic_error(null,"Program should have main function");

        writer.println("LAB END");

        return p;
    }
}
