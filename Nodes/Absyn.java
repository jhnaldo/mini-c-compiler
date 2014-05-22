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

    // semantic check
    static private ArrayList<SymbolTable> reverse(ArrayList<SymbolTable> arr){
        ArrayList<SymbolTable> _arr = new ArrayList<SymbolTable>(arr);
        Collections.reverse(_arr);
        return _arr;
    }
    static public STElem get_sym_table_elem(String name){
        for(SymbolTable st : reverse(sym_table_arr)){
            if(st.hash.containsKey(name)) return st.hash.get(name);
        }
        return null;
    }
    static public FuncTable get_fun_table(String name){
        for(FuncTable st : fun_table_arr){
            if(st.name.equals(name)) return st;
        }
        return null;
    }
    static public boolean is_func = false;
    static public void semantic_error(Absyn node, String message){
        System.err.println("[SemanticError]:<"+node.start.str()+"-"+node.end.str()+">:"+message);
        System.exit(0);
    }
    static public void semantic_warning(Absyn node, String message){
        System.err.println("[Warning]:<"+node.start.str()+"-"+node.end.str()+">:"+message);
    }

    // Symbol table
    static public ArrayList<SymbolTable> sym_table_arr = new ArrayList<SymbolTable>();
    static public ArrayList<FuncTable> fun_table_arr = new ArrayList<FuncTable>();
    static public SymbolTable cur_sym_table = null;
    static public FuncTable cur_fun_table = null;
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
    static public void add_symbol(Type t, String n, SymbolRole s){
        switch(s){
            case PARAM:
                cur_fun_table.add(t, -1, s);
            case VAR:
                cur_sym_table.add(n, t, -1, s);
        }
    }
    static public void add_symbol(Type t, String n, Integer k, SymbolRole s){
        switch(s){
            case PARAM:
                cur_fun_table.add(t, k, s);
            case VAR:
                cur_sym_table.add(n, t, k, s);
        }
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
