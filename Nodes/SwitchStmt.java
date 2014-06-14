// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class SwitchStmt extends Stmt {
    Ident ident;
    CaseList cases;
    StmtList default_stmt;
    Boolean default_has_break;

    public SwitchStmt(Ident id, CaseList cl, StmtList ds, Boolean dhb, Pos s, Pos e) {
        ident = id;
        cases = cl;
        default_stmt = ds;
        default_has_break = dhb;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("switch(");
        ident.show_ast_c_ver();
        writer.println(")");
        println("{");
        level++;
        cases.show_ast_c_ver();
        if(default_stmt!=null){
            println("default:");
            level++;
            default_stmt.show_ast_c_ver();
            if(default_has_break){
                println("break;");
            }
            level--;
        }
        level--;
        println("}");
    }

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        cur_func_name.add("switch("+temp+")");
        show_func_name();
        cases.show_sym_table();
        if(default_stmt!=null){
            temp=comp_count+1;
            comp_count=0;
            cur_func_name.add("default("+temp+")");
            show_func_name();
            default_stmt.show_sym_table();
            comp_count=temp;
            cur_func_name.remove(cur_func_name.size()-1);
        }
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);
    }

    public SwitchStmt semantic_analysis(){
        SwitchStmt ss = new SwitchStmt(null, null, null, default_has_break, start, end);
        String name = ident.name;
        STElem ste = get_sym_table_elem(name);
        if(ste == null){
            System.err.println("[SemanticError]:"+ident.start.str()+":Variable "+name+" is not defined");
            System.exit(0);
        }
        ste.get_T(writer);
        writer.println("    MOVE  MEM(VR(0)@)@ VR(0)");

        int cur_label_num = label_num;
        ss.ident = ident.semantic_analysis();
        if(default_stmt!=null){
            block_idx++;
            int default_label_num = cur_label_num++;
            int case_label_num = cur_label_num;
            label_num++;
            for(CaseStmt cs : cases.arr){
                case_label_num++;
                writer.println("    SUB   VR(0)@ "+cs.num+" VR("+block_idx+")");
                writer.println("    JMPZ  VR("+block_idx+")@ _L"+case_label_num);
            }
            block_idx--;
            writer.println("    JMP   _L"+default_label_num);
            ss.cases = cases.semantic_analysis();
            writer.println("LAB _L"+default_label_num);
            ss.default_stmt = default_stmt.semantic_analysis();
            writer.println("LAB _L"+cur_label_num);
        }else{
            block_idx++;
            int case_label_num = cur_label_num;
            for(CaseStmt cs : cases.arr){
                case_label_num++;
                writer.println("    SUB   VR(0)@ "+cs.num+" VR("+block_idx+")");
                writer.println("    JMPZ  VR("+block_idx+")@ _L"+case_label_num);
            }
            block_idx--;
            writer.println("    JMP   _L"+cur_label_num);
            ss.cases = cases.semantic_analysis();
            ss.default_stmt = null;
            writer.println("LAB _L"+cur_label_num);
        }
        label_num++;
        return ss;
    }
}
