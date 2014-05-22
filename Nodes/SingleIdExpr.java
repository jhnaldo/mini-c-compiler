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
        if(ste == null)
            semantic_error(this,"Variable "+name+" is not defined.");
        if(ste.is_array()){
            if(ste.typ.typ == TypeName.INT)
                tn = TypeName.INT_ARR;
            else
                tn = TypeName.FLOAT_ARR;
        }else{
            tn = ste.typ.typ;
        }
        return this;
    }
}
