// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Type extends Absyn {
    TypeName typ;

    public Type(TypeName t, Pos s, Pos e) {
        typ = t;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        switch(typ){
            case INT:
                writer.print("int");
                break;
            case FLOAT:
                writer.print("float");
                break;
        }
    }
}
