// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

import java.util.*;

interface Ident { }
interface Stmt { }

class Program {
    DeclList decls;
    FuncList funcs;

    public Program(DeclList dl, FuncList fl) {
        decls = dl;
        funcs = fl;
    }
}

class DeclList {
    ArrayList<Decl> arr;

    public DeclList(Decl decl) {
        arr = new ArrayList<Decl>();
        arr.add(decl);
    }

    public void add(Decl decl) {
        arr.add(decl);
    }
}

class FuncList {
    ArrayList<Func> arr;

    public FuncList(Func func) {
        arr = new ArrayList<Func>();
        arr.add(func);
    }

    public void add(Func func) {
        arr.add(func);
    }
}

class Decl {
    Type typ;
    IdentList idents;

    public Decl(Type t, IdentList idl) {
        typ = t;
        idents = idl;
    }
}

class IdentList {
    ArrayList<Ident> arr;

    public IdentList(Ident id) {
        arr = new ArrayList<Ident>();
        arr.add(id);
    }

    public void add(Ident id) {
        arr.add(id);
    }
}

class SingleIdent implements Ident {
    String name;
    Number val;

    public SingleIdent(String n) {
        name = n;
        val = 0;
    }
}

class ArrayIdent implements Ident {
    String name;
    ArrayList<Number> val;
    Integer size;

    public ArrayIdent(String n, Integer s) {
        name=n;
        size=s;
        val = new ArrayList<Number>();
        for (int i=0;i<this.size;i++) {
            val.add(0);
        }
    }
}

class Func {
    Type typ;
    String name;
    ParamList params;
    CompStmt comp_stmt;

    public Func(Type t, String fn, ParamList pl, CompStmt cs) {
        typ = t;
        name = fn;
        params = pl;
        comp_stmt = cs;
    }
}

class ParamList {
    ArrayList<Type> tarr;
    ArrayList<Ident> iarr;

    public ParamList(Type t, Ident id) {
        tarr = new ArrayList<Type>();
        tarr.add(t);
        iarr = new ArrayList<Ident>();
        iarr.add(id);
    }

    public void add(Type t, Ident id) {
        tarr.add(t);
        iarr.add(id);
    }
}

class Type {
    Integer typ;

    public Type(Integer t) {
        typ = t;
    }
}

class CompStmt implements Stmt{
    DeclList decls;
    StmtList stmts;

    public CompStmt(DeclList dl, StmtList sl) {
        decls = dl;
        stmts = sl;
    }
}

class StmtList {
    ArrayList<Stmt> arr;

    public StmtList() {
        arr = new ArrayList<Stmt>();
    }

    public void add(Stmt s) {
        arr.add(s);
    }
}

class AssignStmt implements Stmt {
    Assign assign;

    public AssignStmt(Assign as) {
        assign = as;
    }
}

class Assign {
    String name;
    Expr index, expr;

    public Assign(String n, Expr idx, Expr e) {
        name = n;
        index = idx;
        expr = e;
    }
}

class CallStmt implements Stmt {
    Call call;

    public CallStmt(Call c){
        call = c;
    }
}

class Call {
    String name;
    ArgList args;

    public Call(String n, ArgList al){
        name = n;
        args = al;
    }
}

class RetStmt implements Stmt {
    Expr expr;

    public RetStmt(Expr e) {
        expr = e;
    }
}

class WhileStmt implements Stmt {
    Expr expr;
    Stmt stmt;
    Boolean is_do;

    public WhileStmt(Expr e, Stmt s, Boolean d) {
        expr = e;
        stmt = s;
        is_do = d;
    }
}

class ForStmt implements Stmt {
    Assign initial;
    Expr condition;
    Assign incl;
    Stmt stmt;

    public ForStmt(Assign init, Expr cond, Assign as, Stmt st) {
        initial = init;
        condition = cond;
        incl = as;
        stmt = st;
    }
}

class IfStmt implements Stmt {
    Expr condition;
    Stmt then_stmt, else_stmt;

    public IfStmt(Expr cond, Stmt th, Stmt el) {
        condition = cond;
        then_stmt = th;
        else_stmt = el;
    }
}

class SwitchStmt implements Stmt {
    Ident ident;
    CaseList cases;
    StmtList default_stmt;
    Boolean default_has_break;

    public SwitchStmt(Ident id, CaseList cl, StmtList ds, Boolean dhb) {
        ident = id;
        cases = cl;
        default_stmt = ds;
        default_has_break = dhb;
    }
}

class CaseList {
    ArrayList<Integer> iarr;
    ArrayList<StmtList> sarr;
    ArrayList<Boolean> barr;

    public CaseList() {
        iarr = new ArrayList<Integer>();
        sarr = new ArrayList<StmtList>();
        barr = new ArrayList<Boolean>();
    }

    public void add(Integer k, StmtList sl, Boolean hb){
        iarr.add(k);
        sarr.add(sl);
        barr.add(hb);
    }
}

class Expr {
}

class ArgList {
    ArrayList<Expr> arr;

    public ArgList(Expr e) {
        arr = new ArrayList<Expr>();
        arr.add(e);
    }

    public void add(Expr e) {
        arr.add(e);
    }
}
