// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class ArrayIdExpr extends Expr {
    String name;
    Expr expr;

    public ArrayIdExpr(String n, Expr ex, Pos s, Pos e){
        name = n;
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        writer.print(name+"[");
        expr.show_ast_c_ver();
        writer.print("]");
    }

    public ArrayIdExpr semantic_analysis(){
        ArrayIdExpr aie = new ArrayIdExpr(name, null, start, end);
        STElem ste = get_sym_table_elem(name);
        if(ste == null)
            semantic_error(aie,"Variable "+name+" is not defined.");
        if(!ste.is_array())
            semantic_error(aie,"Variable "+name+" is not array type.");
        aie.expr = expr.semantic_analysis();
        if(aie.expr.tn != TypeName.INT)
            semantic_error(expr,"Array subscript of variable "+name+" should have int type.");
        aie.tn = ste.typ.typ;
        return aie;
    }
}
