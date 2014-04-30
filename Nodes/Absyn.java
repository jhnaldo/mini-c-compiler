// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Absyn {
    public Pos start = null;
    public Pos end = null;

    // Get current position
    public String get_pos(){
        String sstr, estr;

        if (start == null){
            sstr = "?:?";
        }else{
            sstr = start.str();
        }

        if (end == null){
            estr = "?:?";
        }else{
            estr = end.str();
        }
        return "["+sstr+"-"+estr+"]";
    }

    // Output option
    static public boolean prod_rule = true;     // Production Rule  : "prod.txt"
    static public boolean ast_c_ver = true;     // C-like AST       : "tree.txt"
    static public boolean sym_table = true;     // Symbol Table     : "table.txt"

    // File Output
    static public PrintWriter writer = null;

    // Display functions
    static public void show_prod_rule(String msg){
        if (prod_rule) {
            writer.println(msg);
        }
    }
    public void show_ast_c_ver(){ }
    public void show_sym_table(){ }

    // Indentation
    static public int tab = 4;
    static public int level = 0;
    protected void print(String msg){
        String space="";
        for(int i=0;i<tab*level;i++) space+=" ";
        writer.print(space+msg);
    }
    protected void println(String msg){
        String space="";
        for(int i=0;i<tab*level;i++) space+=" ";
        writer.println(space+msg);
    }

    // Symbol table
    static public ArrayList<String> cur_func_name = new ArrayList<String>();
    static public Integer sym_count = 0;
    static public Integer comp_count = 0;
    static public void show_func_name(){
        if(cur_func_name.size()==0){
            writer.println("Function Name : GLOBAL");
        }else{
            writer.println();
            writer.print("Function Name : "+cur_func_name.get(0));
            for(String name : cur_func_name.subList(1,cur_func_name.size())){
                writer.print(" - "+name);
            }
            writer.println();
        }
        writer.println("     count      type                          name     array      role");
    }
    static public void show_symbol(Type t, String n, SymbolRole s){
        String tn = null;
        switch(t.typ){
            case INT:
                tn="int";
                break;
            case FLOAT:
                tn="float";
                break;
        }
        String sn = null;
        switch(s){
            case VAR:
                sn="variable";
                break;
            case PARAM:
                sn="parameter";
                break;
        }
        writer.printf("%10d%10s%30s%10s%10s\n",sym_count,tn,n,"",sn);
    }
    static public void show_symbol(Type t, String n, Integer k, SymbolRole s){
        String tn = null;
        switch(t.typ){
            case INT:
                tn="int";
                break;
            case FLOAT:
                tn="float";
                break;
        }
        String sn = null;
        switch(s){
            case VAR:
                sn="variable";
                break;
            case PARAM:
                sn="parameter";
                break;
        }
        writer.printf("%10d%10s%30s%10d%10s\n",sym_count,tn,n,k,sn);
    }
}