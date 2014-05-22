// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class SymbolTable {
    String name;
    HashMap<String, STElem> hash;

    public SymbolTable(String _name) {
        name = _name;
        hash = new HashMap<String, STElem>();
    }

    public void add(String name, Type t, int len, SymbolRole s) {
        STElem ste = new STElem(t,len,s);
        hash.put(name,ste);
    }
}

class FuncTable {
    String name;
    Type typ;
    ArrayList<STElem> arr;

    public FuncTable(String _name, Type _typ) {
        name = _name;
        typ = _typ;
        arr = new ArrayList<STElem>();
    }

    public void add(Type t, int len, SymbolRole s) {
        STElem ste = new STElem(t,len,s);
        arr.add(ste);
    }
}

class STElem {
    Type typ;
    int size;
    SymbolRole sr;

    public STElem(Type t, int len, SymbolRole s) {
        typ = t;
        size = len;
        sr = s;
    }

    public void display(PrintWriter writer){
        String tn = null;
        switch(typ.typ){
            case INT:
                tn="int";
                break;
            case FLOAT:
                tn="float";
                break;
        }
        String sn = null;
        switch(sr){
            case VAR:
                sn="variable";
                break;
            case PARAM:
                sn="parameter";
                break;
        }
        writer.printf("%10s%10d%10s\n",tn,size,sn);
    }

    public boolean is_array(){
        return size>=0;
    }
}
