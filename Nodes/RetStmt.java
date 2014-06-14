// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class RetStmt extends Stmt {
    Expr expr;

    public RetStmt(Expr ex, Pos s, Pos e) {
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("return ");
        if(expr!=null)expr.show_ast_c_ver();
        writer.println(";");
    }

    public RetStmt semantic_analysis(){
        RetStmt rs = new RetStmt(null, start, end);
        if(expr==null) semantic_error(rs,"Return of function should have expression.");
        rs.expr = expr.semantic_analysis();

        writer.println("    MOVE  FP@ SP");
        writer.println("    MOVE  MEM(FP@)@ FP");
        writer.println("    SUB   SP@ 1 SP");
        writer.println("    JMP   MEM(SP@)@");

        TypeName fun_typ = cur_fun_table.typ.typ;
        TypeName expr_typ = rs.expr.tn;
        if(fun_typ != expr_typ){
            String t;
            if(fun_typ == TypeName.INT) t = "int";
            else t = "float";
            switch(expr_typ){
                case INT:
                    semantic_warning(rs.expr,"Return of function "+cur_fun_table.name+" should have "+t+" type.");
                    rs.expr = new IntToFloat(rs.expr);
                    break;
                case FLOAT:
                    semantic_warning(rs.expr,"Return of function "+cur_fun_table.name+" should have "+t+" type.");
                    rs.expr = new FloatToInt(rs.expr);
                    break;
                default:
                    semantic_error(rs.expr,"Return of function "+cur_fun_table.name+" should have "+t+" type.");
            }
        }
        return rs;
    }
}
