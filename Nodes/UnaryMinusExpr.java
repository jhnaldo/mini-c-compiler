// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class UnaryMinusExpr extends UnaryExpr {
    public UnaryMinusExpr(Expr ex, Pos s, Pos e){
        super(ex,s,e);
        symbol = "-";
    }
}
