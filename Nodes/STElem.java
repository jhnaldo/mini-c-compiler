// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class STElem {
    Type typ;
    int size;
    SymbolRole sr;
    int position;

    public STElem(Type t, int len, SymbolRole s, int pos) {
        typ = t;
        size = len;
        sr = s;
        position = pos;
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

    public void get_T(PrintWriter writer){
        switch(sr){
            case VAR:
            case PARAM:
                writer.println("    ADD   FP@ "+position+" VR(0)");
                break;
            case GLOBAL:
                writer.println("    ADD   1 "+position+" VR(0)");
                break;
        }
    }
}
