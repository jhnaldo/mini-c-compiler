// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

import java.util.*;
import java.io.PrintWriter;

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

    // Get current position
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

    // Output option
    static public boolean prod_rule = true;     // Production Rule  : "prod.txt"
    static public boolean ast_c_ver = true;     // C-like AST       : "tree.txt"
    static public boolean sym_table = true;     // Symbol Table     : "table.txt"

    // File Output
    static public PrintWriter writer = null;

    // Display functions
    static public void show_prod_rule(String msg){
        if (prod_rule) {
            writer.println(msg);
        }
    }
    public void show_ast_c_ver(){ }
    public void show_sym_table(){ }

    // Indentation
    static public int tab = 4;
    static public int level = 0;
    protected void print(String msg){
        String space="";
        for(int i=0;i<tab*level;i++) space+=" ";
        writer.print(space+msg);
    }
    protected void println(String msg){
        String space="";
        for(int i=0;i<tab*level;i++) space+=" ";
        writer.println(space+msg);
    }

    // Symbol table
    static public ArrayList<String> cur_func_name = new ArrayList<String>();
    static public Integer sym_count = 0;
    static public Integer comp_count = 0;
    static public void show_func_name(){
        if(cur_func_name.size()==0){
            writer.println("Function Name : GLOBAL");
        }else{
            writer.println();
            writer.print("Function Name : "+cur_func_name.get(0));
            for(String name : cur_func_name.subList(1,cur_func_name.size())){
                writer.print(" - "+name);
            }
            writer.println();
        }
        writer.println("     count      type                          name     array      role");
    }
    static public void show_symbol(Type t, String n, SymbolRole s){
        String tn = null;
        switch(t.typ){
            case INT:
                tn="int";
                break;
            case FLOAT:
                tn="float";
                break;
        }
        String sn = null;
        switch(s){
            case VAR:
                sn="variable";
                break;
            case PARAM:
                sn="parameter";
                break;
        }
        writer.printf("%10d%10s%30s%10s%10s\n",sym_count,tn,n,"",sn);
    }
    static public void show_symbol(Type t, String n, Integer k, SymbolRole s){
        String tn = null;
        switch(t.typ){
            case INT:
                tn="int";
                break;
            case FLOAT:
                tn="float";
                break;
        }
        String sn = null;
        switch(s){
            case VAR:
                sn="variable";
                break;
            case PARAM:
                sn="parameter";
                break;
        }
        writer.printf("%10d%10s%30s%10d%10s\n",sym_count,tn,n,k,sn);
    }
}
class Ident extends Absyn {
    String name;
    public boolean is_array(){ return false; }
}
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

    public void show_sym_table(){
        show_func_name();
        if(decls!=null) decls.show_sym_table();
        if(funcs!=null) funcs.show_sym_table();
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

    public void show_sym_table(){
        for(Decl d : arr){
            d.show_sym_table();
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

    public void show_sym_table(){
        for(Func f : arr){
            f.show_sym_table();
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
            writer.print(", ");
            i.show_ast_c_ver();
        }
    }
}

class SingleIdent extends Ident {
    Number val;

    public SingleIdent(String n, Pos s, Pos e) {
        name = n;
        val = 0;
        start = s;
        end = e;
    }

    public boolean is_array(){ return false; }

    public void show_ast_c_ver(){
        writer.print(name);
    }
}

class ArrayIdent extends Ident {
    Integer size;

    public ArrayIdent(String n, Integer si, Pos s, Pos e) {
        name=n;
        size=si;
        start = s;
        end = e;
    }

    public boolean is_array(){ return true; }

    public void show_ast_c_ver(){
        writer.print(name+"["+size+"]");
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
        writer.print(" "+name+" (");
        if(params!=null){
            params.show_ast_c_ver();
        }
        writer.println(")");
        comp_stmt.show_ast_c_ver();
    }

    public void show_sym_table(){
        sym_count=0;
        comp_count=0;
        cur_func_name.add(name);
        show_func_name();
        if(params!=null) params.show_sym_table();
        comp_stmt.show_sym_table();
        cur_func_name.remove(cur_func_name.size()-1);
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
            writer.print(", ");
            p.show_ast_c_ver();
        }
    }

    public void show_sym_table(){
        for(Param p : arr){
            sym_count++;
            Type typ = p.typ;
            Ident id = p.ident;
            if(id.is_array()){
                ArrayIdent aid = (ArrayIdent)id;
                show_symbol(typ, aid.name, aid.size, SymbolRole.PARAM);
            }else{
                SingleIdent sid = (SingleIdent)id;
                show_symbol(typ, sid.name, SymbolRole.PARAM);
            }
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
        writer.print(" ");
        ident.show_ast_c_ver();
    }
}

enum TypeName {
    INT, FLOAT;
}

enum SymbolRole{
    VAR, PARAM;
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
                writer.print("int");
                break;
            case FLOAT:
                writer.print("float");
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

    public void show_sym_table(){
        for(Stmt s : arr){
            if(s instanceof CompStmt){
                Integer temp=comp_count+1;
                comp_count=0;
                cur_func_name.add("compound("+temp+")");
                show_func_name();
                s.show_sym_table();
                comp_count=temp;
                cur_func_name.remove(cur_func_name.size()-1);
            }
            s.show_sym_table();
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

    public void show_sym_table(){
        if(decls!=null)decls.show_sym_table();
        stmts.show_sym_table();
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
        writer.println(";");
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
        writer.print(name);
        if(index!=null){
            writer.print("[");
            index.show_ast_c_ver();
            writer.print("]");
        }
        writer.print(" = ");
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
        writer.println(";");
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
        writer.print(name+"(");
        if(args!=null)args.show_ast_c_ver();
        writer.print(")");
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
        writer.println(";");
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
            writer.println(");");
        }else{
            print("while(");
            expr.show_ast_c_ver();
            writer.println(");");
            if(!CompStmt.class.isInstance(stmt)) level++;
            stmt.show_ast_c_ver();
            if(!CompStmt.class.isInstance(stmt)) level--;
        }
    }

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        if(is_do){
            cur_func_name.add("do_while("+temp+")");
        }else{
            cur_func_name.add("while("+temp+")");
        }
        show_func_name();
        stmt.show_sym_table();
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);
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
        writer.print("; ");
        condition.show_ast_c_ver();
        writer.print("; ");
        incl.show_ast_c_ver();
        writer.println(")");
        if(!CompStmt.class.isInstance(stmt)) level++;
        stmt.show_ast_c_ver();
        if(!CompStmt.class.isInstance(stmt)) level--;
    }

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        cur_func_name.add("for("+temp+")");
        show_func_name();
        stmt.show_sym_table();
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);
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
        writer.println(")");
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

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        cur_func_name.add("if("+temp+")");
        show_func_name();
        then_stmt.show_sym_table();
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);

        if(else_stmt!=null){
            temp=comp_count+1;
            comp_count=0;
            cur_func_name.add("else("+temp+")");
            show_func_name();
            else_stmt.show_sym_table();
            comp_count=temp;
            cur_func_name.remove(cur_func_name.size()-1);
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
        writer.println(")");
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

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        cur_func_name.add("switch("+temp+")");
        show_func_name();
        cases.show_sym_table();
        if(default_stmt!=null){
            temp=comp_count+1;
            comp_count=0;
            cur_func_name.add("default("+temp+")");
            show_func_name();
            default_stmt.show_sym_table();
            comp_count=temp;
            cur_func_name.remove(cur_func_name.size()-1);
        }
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);
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

    public void show_sym_table(){
        for(CaseStmt c : arr){
            c.show_sym_table();
        }
    }
}

class CaseStmt extends Stmt {
    Integer num;
    StmtList stmts;
    Boolean has_break;

    public CaseStmt(Integer k, StmtList sl, Boolean hb){
        num = k;
        stmts = sl;
        has_break = hb;
    }

    public void show_ast_c_ver(){
        println("case "+num+":");
        level++;
        stmts.show_ast_c_ver();
        if(has_break) println("break;");
        level--;
    }

    public void show_sym_table(){
        Integer temp=comp_count+1;
        comp_count=0;
        cur_func_name.add("case("+temp+")");
        show_func_name();
        stmts.show_sym_table();
        comp_count=temp;
        cur_func_name.remove(cur_func_name.size()-1);
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
        writer.print(symbol);
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
        writer.print(symbol);
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
        writer.print(num);
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
        writer.print(num);
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
        writer.print(name);
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
        writer.print(name+"[");
        expr.show_ast_c_ver();
        writer.print("]");
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
        writer.print("(");
        expr.show_ast_c_ver();
        writer.print(")");
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
            writer.print(", ");
            e.show_ast_c_ver();
        }
    }
}
