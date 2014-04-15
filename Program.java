// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

import java.util.*;

class Pos {
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
abstract class Absyn {
    public Pos start = null;
    public Pos end = null;
    public String get_pos(){
        String sstr, estr;

        if (start == null){
            sstr = "?:?";
        }else{
            sstr = start.str();
        }

        if (end == null){
            estr = "?:?";
        }else{
            estr = end.str();
        }
        return "["+sstr+"-"+estr+"]";
    }
}
abstract class Ident extends Absyn { }
abstract class Stmt extends Absyn { }

class Program extends Absyn {
    DeclList decls;
    FuncList funcs;

    public Program(DeclList dl, FuncList fl, Pos s, Pos e) {
        decls = dl;
        funcs = fl;
        start = s;
        end = e;
    }
}

class DeclList extends Absyn {
    ArrayList<Decl> arr;

    public DeclList(Decl decl, Pos s, Pos e) {
        arr = new ArrayList<Decl>();
        arr.add(decl);
        start = s;
        end = e;
    }

    public void add(Decl decl) {
        arr.add(decl);
        end = decl.end;
    }
}

class FuncList extends Absyn {
    ArrayList<Func> arr;

    public FuncList(Func func, Pos s, Pos e) {
        arr = new ArrayList<Func>();
        arr.add(func);
        start = s;
        end = e;
    }

    public void add(Func func) {
        arr.add(func);
        end = func.end;
    }
}

class Decl extends Absyn {
    Type typ;
    IdentList idents;

    public Decl(Type t, IdentList idl, Pos s, Pos e) {
        typ = t;
        idents = idl;
        start = s;
        end = e;
    }
}

class IdentList extends Absyn {
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
}

class SingleIdent extends Ident {
    String name;
    Number val;

    public SingleIdent(String n, Pos s, Pos e) {
        name = n;
        val = 0;
        start = s;
        end = e;
    }
}

class ArrayIdent extends Ident {
    String name;
    Integer size;

    public ArrayIdent(String n, Integer si, Pos s, Pos e) {
        name=n;
        size=si;
        start = s;
        end = e;
    }
}

class Func extends Absyn {
    Type typ;
    String name;
    ParamList params;
    CompStmt comp_stmt;

    public Func(Type t, String fn, ParamList pl, CompStmt cs, Pos s, Pos e) {
        typ = t;
        name = fn;
        params = pl;
        comp_stmt = cs;
        start = s;
        end = e;
    }
}

class ParamList extends Absyn {
    ArrayList<Type> tarr;
    ArrayList<Ident> iarr;

    public ParamList(Type t, Ident id, Pos s, Pos e) {
        tarr = new ArrayList<Type>();
        tarr.add(t);
        iarr = new ArrayList<Ident>();
        iarr.add(id);
        start = s;
        end = e;
    }

    public void add(Type t, Ident id) {
        tarr.add(t);
        iarr.add(id);
        end = id.end;
    }
}

class Type extends Absyn {
    Integer typ;

    public Type(Integer t, Pos s, Pos e) {
        typ = t;
        start = s;
        end = e;
    }
}

class CompStmt extends Stmt{
    DeclList decls;
    StmtList stmts;

    public CompStmt(DeclList dl, StmtList sl, Pos s, Pos e) {
        decls = dl;
        stmts = sl;
        start = s;
        end = e;
    }
}

class NullStmt extends Stmt{
    public NullStmt(Pos s, Pos e) {
        start = s;
        end = e;
    }
}

class StmtList extends Absyn {
    ArrayList<Stmt> arr;

    public StmtList(Pos s, Pos e) {
        arr = new ArrayList<Stmt>();
        start = s;
        end = e;
    }

    public void add(Stmt s) {
        arr.add(s);
        if (arr.size() == 1)
            start = s.start;
        end = s.end;
    }
}

class AssignStmt extends Stmt {
    Assign assign;

    public AssignStmt(Assign as, Pos s, Pos e) {
        assign = as;
        start = s;
        end = e;
    }
}

class Assign extends Absyn {
    String name;
    Expr index, expr;

    public Assign(String n, Expr idx, Expr ex, Pos s, Pos e) {
        name = n;
        index = idx;
        expr = ex;
        start = s;
        end = e;
    }
}

class CallStmt extends Stmt {
    Call call;

    public CallStmt(Call c, Pos s, Pos e){
        call = c;
        start = s;
        end = e;
    }
}

class Call extends Absyn {
    String name;
    ArgList args;

    public Call(String n, ArgList al, Pos s, Pos e){
        name = n;
        args = al;
        start = s;
        end = e;
    }
}

class RetStmt extends Stmt {
    Expr expr;

    public RetStmt(Expr ex, Pos s, Pos e) {
        expr = ex;
        start = s;
        end = e;
    }
}

class WhileStmt extends Stmt {
    Expr expr;
    Stmt stmt;
    Boolean is_do;

    public WhileStmt(Expr ex, Stmt st, Boolean d, Pos s, Pos e) {
        expr = ex;
        stmt = st;
        is_do = d;
        start = s;
        end = e;
    }
}

class ForStmt extends Stmt {
    Assign initial;
    Expr condition;
    Assign incl;
    Stmt stmt;

    public ForStmt(Assign init, Expr cond, Assign as, Stmt st, Pos s, Pos e) {
        initial = init;
        condition = cond;
        incl = as;
        stmt = st;
        start = s;
        end = e;
    }
}

class IfStmt extends Stmt {
    Expr condition;
    Stmt then_stmt, else_stmt;

    public IfStmt(Expr cond, Stmt th, Stmt el, Pos s, Pos e) {
        condition = cond;
        then_stmt = th;
        else_stmt = el;
        start = s;
        end = e;
    }
}

class SwitchStmt extends Stmt {
    Ident ident;
    CaseList cases;
    StmtList default_stmt;
    Boolean default_has_break;

    public SwitchStmt(Ident id, CaseList cl, StmtList ds, Boolean dhb, Pos s, Pos e) {
        ident = id;
        cases = cl;
        default_stmt = ds;
        default_has_break = dhb;
        start = s;
        end = e;
    }
}

class CaseList extends Absyn {
    ArrayList<Integer> iarr;
    ArrayList<StmtList> sarr;
    ArrayList<Boolean> barr;

    public CaseList(Pos s, Pos e) {
        iarr = new ArrayList<Integer>();
        sarr = new ArrayList<StmtList>();
        barr = new ArrayList<Boolean>();
        start = s;
        end = e;
    }

    public void add(Integer k, StmtList sl, Boolean hb, Pos s, Pos e){
        iarr.add(k);
        sarr.add(sl);
        barr.add(hb);
        if (iarr.size() == 1)
            start = s;
        end = e;
    }
}

class Expr extends Absyn {
    public Expr(Pos s, Pos e){
        start = s;
        end = e;
    }
}

class ArgList extends Absyn {
    ArrayList<Expr> arr;

    public ArgList(Expr ex, Pos s, Pos e) {
        arr = new ArrayList<Expr>();
        arr.add(ex);
        start = s;
        end = e;
    }

    public void add(Expr ex) {
        arr.add(ex);
        end = ex.end;
    }
}
