// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class GTEExpr extends BinaryExpr {
    public GTEExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = ">=";
    }
}
