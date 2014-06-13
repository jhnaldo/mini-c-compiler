// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Decl extends Absyn {
    Type typ;
    IdentList idents;

    public Decl(){
        typ=null;
        idents=null;
    }

    public Decl(Type t, IdentList idl, Pos s, Pos e){
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

    public Decl semantic_analysis(){
        for(Ident id : idents.arr){

            if(is_func){
                int size = sym_table_arr.size();
                SymbolTable st = sym_table_arr.get(size-2);
                if(st.hash.containsKey(id.name)){
                    System.err.println("[SemanticError]:"+id.start.str()+":Variable "+id.name+" is already defiend");
                    System.exit(0);
                }
            }
            if(cur_sym_table.hash.containsKey(id.name)){
                System.err.println("[SemanticError]:"+id.start.str()+":Parameter "+id.name+" is already defiend");
                System.exit(0);
            }

            if(id.is_array()){
                ArrayIdent aid = (ArrayIdent)id;
                writer.println("    ADD   SP@ 1 VR("+(block_idx+1)+")");
                writer.println("    ADD   SP@ "+aid.size+" SP");
                push("VR("+(block_idx+1)+")@");
                rel_pos+=aid.size;
                add_symbol(typ, aid.name, aid.size, SymbolRole.VAR, rel_pos-1);
            }else{
                SingleIdent sid = (SingleIdent)id;
                push(0);
                add_symbol(typ, sid.name, SymbolRole.VAR, rel_pos-1);
            }
        }

        Decl d = new Decl();
        d.typ = typ;
        d.idents = idents;
        d.start = start;
        d.end = end;
        return d;
    }
}
