// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class IdentList extends Absyn {
    ArrayList<Ident> arr;

    public IdentList(Ident id, Pos s, Pos e) {
        arr = new ArrayList<Ident>();
        arr.add(id);
        start = s;
        end = e;
    }

    public void add(Ident id) {
        arr.add(id);
        end = id.end;
    }

    public void show_ast_c_ver(){
        arr.get(0).show_ast_c_ver();
        for(Ident i : arr.subList(1,arr.size())){
            writer.print(", ");
            i.show_ast_c_ver();
        }
    }
}
