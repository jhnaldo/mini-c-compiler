// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class SingleIdExpr extends Expr {
    String name;

    public SingleIdExpr(String n, Pos s, Pos e){
        name = n;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print(name);
    }

    public SingleIdExpr semantic_analysis(){
        STElem ste = get_sym_table_elem(name);
        if(ste == null){
            System.err.println("[SemanticError]:"+start.str()+":Variable "+name+" is not defined");
            System.exit(0);
        }
        return this;
    }
}
