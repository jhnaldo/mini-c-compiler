// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Pos {
    public int linenum=0;
    public int colnum=0;
    public Pos(int l, int c){
        linenum = l;
        colnum = c;
    }

    public String str(){
        return String.valueOf(linenum)+":"+String.valueOf(colnum);
    }
}
