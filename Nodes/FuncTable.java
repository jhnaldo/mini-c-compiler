// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class FuncTable {
    String name;
    Type typ;
    ArrayList<STElem> arr;

    public FuncTable(String _name, Type _typ) {
        name = _name;
        typ = _typ;
        arr = new ArrayList<STElem>();
    }

    public void add(Type t, int len, SymbolRole s, int pos) {
        STElem ste = new STElem(t,len,s,pos);
        arr.add(ste);
    }
}
