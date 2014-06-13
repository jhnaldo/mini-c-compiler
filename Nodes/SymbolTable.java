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

    public void add(String name, Type t, int len, SymbolRole s, int pos) {
        STElem ste = new STElem(t,len,s,pos);
        hash.put(name,ste);
    }
}
