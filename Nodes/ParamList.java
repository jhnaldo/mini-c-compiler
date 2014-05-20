// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class ParamList extends Absyn {
    ArrayList<Param> arr;

    public ParamList(){
        arr = new ArrayList<Param>();
    }

    public ParamList(Type t, Ident id, Pos s, Pos e) {
        arr = new ArrayList<Param>();
        arr.add(new Param(t, id));
        start = s;
        end = e;
    }

    public void add(Type t, Ident id) {
        arr.add(new Param(t, id));
        end = id.end;
    }

    public void show_ast_c_ver(){
        arr.get(0).show_ast_c_ver();
        for(Param p : arr.subList(1,arr.size())){
            writer.print(", ");
            p.show_ast_c_ver();
        }
    }

    public void show_sym_table(){
        for(Param p : arr){
            sym_count++;
            Type typ = p.typ;
            Ident id = p.ident;
            if(id.is_array()){
                ArrayIdent aid = (ArrayIdent)id;
                show_symbol(typ, aid.name, aid.size, SymbolRole.PARAM);
            }else{
                SingleIdent sid = (SingleIdent)id;
                show_symbol(typ, sid.name, SymbolRole.PARAM);
            }
        }
    }

    public ParamList semantic_analysis(){
        ParamList pl = new ParamList();
        for(Param p : arr){
            Type typ = p.typ;
            Ident id = p.ident;
            if(id.is_array()){
                ArrayIdent aid = (ArrayIdent)id;
                add_symbol(typ, aid.name, aid.size, SymbolRole.PARAM);
            }else{
                SingleIdent sid = (SingleIdent)id;
                add_symbol(typ, sid.name, SymbolRole.PARAM);
            }
        }
        pl.arr = arr;
        pl.start = start;
        pl.end = end;
        return pl;
    }
}
