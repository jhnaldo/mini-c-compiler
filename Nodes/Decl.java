// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Decl extends Absyn {
    Type typ;
    IdentList idents;

    public Decl(Type t, IdentList idl, Pos s, Pos e) {
        typ = t;
        idents = idl;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("");
        typ.show_ast_c_ver();
        writer.print(" ");
        idents.show_ast_c_ver();
        writer.println(";");
    }

    public void show_sym_table(){
        for(Ident id : idents.arr){
            sym_count++;
            if(id.is_array()){
                ArrayIdent aid = (ArrayIdent)id;
                show_symbol(typ, aid.name, aid.size, SymbolRole.VAR);
            }else{
                SingleIdent sid = (SingleIdent)id;
                show_symbol(typ, sid.name, SymbolRole.VAR);
            }
        }
    }
}
