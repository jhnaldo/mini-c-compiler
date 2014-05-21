// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Assign extends Absyn {
    String name;
    Expr index, expr;

    public Assign(String n, Expr idx, Expr ex, Pos s, Pos e) {
        name = n;
        index = idx;
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print(name);
        if(index!=null){
            writer.print("[");
            index.show_ast_c_ver();
            writer.print("]");
        }
        writer.print(" = ");
        expr.show_ast_c_ver();
    }

    public Assign semantic_analysis(){
        Assign as = new Assign(name, null, null, start, end);

        STElem ste = get_sym_table_elem(name);
        if(ste == null){
            System.err.println("[SemanticError]:"+start.str()+":Variable "+name+" is not defined");
            System.exit(0);
        }
        if(index!=null) as.index = index.semantic_analysis();
        as.expr = expr.semantic_analysis();

        return as;
    }
}
