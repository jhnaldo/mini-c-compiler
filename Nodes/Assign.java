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
        if(ste == null)
            semantic_error(this,"Variable "+name+" is not defined.");

        as.expr = expr.semantic_analysis();
        writer.println("    MOVE  VR(0)@ VR("+(block_idx+1)+")");
        block_idx++;
        if(index!=null){
            as.index = index.semantic_analysis();
            writer.println("    MOVE  VR(0)@ VR("+(block_idx+1)+")");
            block_idx++;
            ste.get_T(writer);
            block_idx--;
            writer.println("    ADD   MEM(VR(0)@)@ VR("+(block_idx+1)+")@ VR(0)");
            if(!ste.is_array())
                semantic_error(this,"Variable "+name+" is not array type.");
            if(as.index.tn != TypeName.INT)
                semantic_error(as.index,"Index of array variable should have int type.");
        }else{
            if(ste.is_array()){
                semantic_error(this,"Variable "+name+" should have array format in assign statement.");
            }
            ste.get_T(writer);
        }
        block_idx--;

        if(ste.typ.typ != as.expr.tn){
            String t;
            if(ste.typ.typ == TypeName.INT) t = "int";
            else t = "float";
            switch(as.expr.tn){
                case INT:
                    semantic_warning(as.expr,"Expression should have "+t+" type.");
                    as.expr = new IntToFloat(as.expr);
                    writer.println("    I2F   VR(0)@ VR(0)");
                    break;
                case FLOAT:
                    semantic_warning(as.expr,"Expression should have "+t+" type.");
                    as.expr = new FloatToInt(as.expr);
                    writer.println("    F2I   VR(0)@ VR(0)");
                    break;
                default:
                    semantic_error(as.expr,"Expression should have "+t+" type.");
            }
        }

        writer.println("    MOVE  VR("+(block_idx+1)+")@ MEM(VR(0)@)");

        return as;
    }
}
