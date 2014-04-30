// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class MinusExpr extends BinaryExpr {
    public MinusExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "-";
    }
}
