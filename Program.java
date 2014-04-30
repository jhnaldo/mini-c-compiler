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
class Absyn {
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
    static public boolean prod_rule = false;
    static public boolean ast_c_ver = true;
    static public boolean sym_table = false;
    static public int tab = 4;
    static public int level = 0;
    static public void show_prod_rule(String msg){
        if (prod_rule)
            System.out.println(msg);
    }
    public void show_ast_c_ver(){ }
    protected void print(String msg){
        String space="";
        for(int i=0;i<tab*level;i++) space+=" ";
        System.out.print(space+msg);
    }
    protected void println(String msg){
        String space="";
        for(int i=0;i<tab*level;i++) space+=" ";
        System.out.println(space+msg);
    }
}
class Ident extends Absyn { }
class Stmt extends Absyn { }

class Program extends Absyn {
    DeclList decls;
    FuncList funcs;

    public Program(DeclList dl, FuncList fl, Pos s, Pos e) {
        decls = dl;
        funcs = fl;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        if(decls!=null) decls.show_ast_c_ver();
        if(funcs!=null) funcs.show_ast_c_ver();
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

    public void show_ast_c_ver(){
        for(Decl d : arr){
            d.show_ast_c_ver();
        }
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

    public void show_ast_c_ver(){
        for(Func f : arr){
            f.show_ast_c_ver();
        }
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

    public void show_ast_c_ver(){
        print("");
        typ.show_ast_c_ver();
        System.out.print(" ");
        idents.show_ast_c_ver();
        System.out.println(";");
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

    public void show_ast_c_ver(){
        arr.get(0).show_ast_c_ver();
        for(Ident i : arr.subList(1,arr.size())){
            System.out.print(", ");
            i.show_ast_c_ver();
        }
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

    public void show_ast_c_ver(){
        System.out.print(name);
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

    public void show_ast_c_ver(){
        System.out.print(name+"["+size+"]");
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

    public void show_ast_c_ver(){
        typ.show_ast_c_ver();
        System.out.print(" "+name+" (");
        if(params!=null){
            params.show_ast_c_ver();
        }
        System.out.println(")");
        comp_stmt.show_ast_c_ver();
    }
}

class ParamList extends Absyn {
    ArrayList<Param> arr;

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
            System.out.print(", ");
            p.show_ast_c_ver();
        }
    }
}

class Param extends Absyn {
    Type typ;
    Ident ident;

    public Param(Type t, Ident id){
        typ = t;
        ident = id;
    }

    public void show_ast_c_ver(){
        typ.show_ast_c_ver();
        System.out.print(" ");
        ident.show_ast_c_ver();
    }
}

enum TypeName {
    INT, FLOAT;
}

class Type extends Absyn {
    TypeName typ;

    public Type(TypeName t, Pos s, Pos e) {
        typ = t;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        switch(typ){
            case INT:
                System.out.print("int");
                break;
            case FLOAT:
                System.out.print("float");
                break;
        }
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

    public void show_ast_c_ver(){
        for(Stmt s : arr){
            s.show_ast_c_ver();
        }
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

    public void show_ast_c_ver(){
        println("{");
        level++;
        if(decls!=null)decls.show_ast_c_ver();
        stmts.show_ast_c_ver();
        level--;
        println("}");
    }
}

class NullStmt extends Stmt{
    public NullStmt(Pos s, Pos e) {
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        println(";");
    }
}

class AssignStmt extends Stmt {
    Assign assign;

    public AssignStmt(Assign as, Pos s, Pos e) {
        assign = as;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("");
        assign.show_ast_c_ver();
        System.out.println(";");
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

    public void show_ast_c_ver(){
        System.out.print(name);
        if(index!=null){
            System.out.print("[");
            index.show_ast_c_ver();
            System.out.print("]");
        }
        System.out.print(" = ");
        expr.show_ast_c_ver();
    }
}

class CallStmt extends Stmt {
    Call call;

    public CallStmt(Call c, Pos s, Pos e){
        call = c;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("");
        call.show_ast_c_ver();
        System.out.println(";");
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

    public void show_ast_c_ver(){
        System.out.print(name+"(");
        if(args!=null)args.show_ast_c_ver();
        System.out.print(")");
    }
}

class RetStmt extends Stmt {
    Expr expr;

    public RetStmt(Expr ex, Pos s, Pos e) {
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        print("return ");
        if(expr!=null)expr.show_ast_c_ver();
        System.out.println(";");
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

    public void show_ast_c_ver(){
        if(is_do){
            println("do");
            if(!CompStmt.class.isInstance(stmt)) level++;
            stmt.show_ast_c_ver();
            if(!CompStmt.class.isInstance(stmt)) level--;
            print("while(");
            expr.show_ast_c_ver();
            System.out.println(");");
        }else{
            print("while(");
            expr.show_ast_c_ver();
            System.out.println(");");
            if(!CompStmt.class.isInstance(stmt)) level++;
            stmt.show_ast_c_ver();
            if(!CompStmt.class.isInstance(stmt)) level--;
        }
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

    public void show_ast_c_ver(){
        print("for(");
        initial.show_ast_c_ver();
        System.out.print("; ");
        condition.show_ast_c_ver();
        System.out.print("; ");
        incl.show_ast_c_ver();
        System.out.println(")");
        if(!CompStmt.class.isInstance(stmt)) level++;
        stmt.show_ast_c_ver();
        if(!CompStmt.class.isInstance(stmt)) level--;
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

    public void show_ast_c_ver(){
        print("if(");
        condition.show_ast_c_ver();
        System.out.println(")");
        if(!CompStmt.class.isInstance(then_stmt)) level++;
        then_stmt.show_ast_c_ver();
        if(!CompStmt.class.isInstance(then_stmt)) level--;

        if(else_stmt!=null){
            println("else");
            if(!CompStmt.class.isInstance(else_stmt)) level++;
            else_stmt.show_ast_c_ver();
            if(!CompStmt.class.isInstance(else_stmt)) level--;
        }
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

    public void show_ast_c_ver(){
        print("switch(");
        ident.show_ast_c_ver();
        System.out.println(")");
        println("{");
        level++;
        cases.show_ast_c_ver();
        if(default_stmt!=null){
            println("default:");
            level++;
            default_stmt.show_ast_c_ver();
            if(default_has_break){
                println("break;");
            }
            level--;
        }
        level--;
        println("}");
    }
}

class CaseList extends Absyn {
    ArrayList<CaseStmt> arr;

    public CaseList(Pos s, Pos e) {
        arr = new ArrayList<CaseStmt>();
        start = s;
        end = e;
    }

    public void add(Integer k, StmtList sl, Boolean hb, Pos s, Pos e){
        arr.add(new CaseStmt(k, sl, hb));
        if (arr.size() == 1)
            start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        for(CaseStmt c : arr){
            c.show_ast_c_ver();
        }
    }
}

class CaseStmt extends Stmt {
    Integer num;
    StmtList stmt_list;
    Boolean has_break;

    public CaseStmt(Integer k, StmtList sl, Boolean hb){
        num = k;
        stmt_list = sl;
        has_break = hb;
    }

    public void show_ast_c_ver(){
        println("case "+num+":");
        level++;
        stmt_list.show_ast_c_ver();
        if(has_break) println("break;");
        level--;
    }
}

class Expr extends Absyn {
}

class UnaryExpr extends Expr{
    Expr expr;
    String symbol;

    public UnaryExpr(Expr ex, Pos s, Pos e){
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        System.out.print(symbol);
        expr.show_ast_c_ver();
    }
}
class UnaryMinusExpr extends UnaryExpr {
    public UnaryMinusExpr(Expr ex, Pos s, Pos e){
        super(ex,s,e);
        symbol = "-";
    }
}

class BinaryExpr extends Expr{
    Expr left_expr;
    Expr right_expr;
    String symbol;

    public BinaryExpr(Expr le, Expr re, Pos s, Pos e){
        left_expr = le;
        right_expr = re;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        left_expr.show_ast_c_ver();
        System.out.print(symbol);
        right_expr.show_ast_c_ver();
    }
}
class MultExpr extends BinaryExpr {
    public MultExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "*";
    }
}
class DivExpr extends BinaryExpr {
    public DivExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "/";
    }
}
class PlusExpr extends BinaryExpr {
    public PlusExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "+";
    }
}
class MinusExpr extends BinaryExpr {
    public MinusExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "-";
    }
}
class GTExpr extends BinaryExpr {
    public GTExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = ">";
    }
}
class LTExpr extends BinaryExpr {
    public LTExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "<";
    }
}
class GTEExpr extends BinaryExpr {
    public GTEExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = ">=";
    }
}
class LTEExpr extends BinaryExpr {
    public LTEExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "<=";
    }
}
class EqualExpr extends BinaryExpr {
    public EqualExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "==";
    }
}
class NotEqualExpr extends BinaryExpr {
    public NotEqualExpr(Expr le, Expr re, Pos s, Pos e){
        super(le,re,s,e);
        symbol = "!=";
    }
}

class CallExpr extends Expr {
    Call call;

    public CallExpr(Call c, Pos s, Pos e){
        call = c;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        call.show_ast_c_ver();
    }
}

class IntExpr extends Expr {
    Integer num;

    public IntExpr(Integer k, Pos s, Pos e){
        num = k;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        System.out.print(num);
    }
}

class FloatExpr extends Expr {
    Float num;

    public FloatExpr(Float k, Pos s, Pos e){
        num = k;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        System.out.print(num);
    }
}

class SingleIdExpr extends Expr {
    String name;

    public SingleIdExpr(String n, Pos s, Pos e){
        name = n;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        System.out.print(name);
    }
}

class ArrayIdExpr extends Expr {
    String name;
    Expr expr;

    public ArrayIdExpr(String n, Expr ex, Pos s, Pos e){
        name = n;
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        System.out.print(name+"[");
        expr.show_ast_c_ver();
        System.out.print("]");
    }
}

class ParenExpr extends Expr {
    Expr expr;
    public ParenExpr(Expr ex, Pos s, Pos e){
        expr = ex;
        start = s;
        end = e;
    }

    public void show_ast_c_ver(){
        System.out.print("(");
        expr.show_ast_c_ver();
        System.out.print(")");
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

    public void show_ast_c_ver(){
        arr.get(0).show_ast_c_ver();
        for(Expr e : arr.subList(1,arr.size())){
            System.out.print(", ");
            e.show_ast_c_ver();
        }
    }
}
