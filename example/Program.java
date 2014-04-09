// Copyright (c) 2010 Gavin Harrison
// See LICENSE for GPLv3 Terms

import java.util.*;

interface Expr { int run(HashMap<String,Integer> hm); }
interface Condition { boolean test(HashMap<String,Integer> hm); }
interface Statement { void run(HashMap<String,Integer> hm); }

class Num implements Expr
{
	Integer val;
	
	public Num(Integer i)
	{
		val = i;
	}
	
	public int run(HashMap<String,Integer> hm)
	{
		return val;
	}
}

class ID implements Expr
{
	String name;
	
	public ID(String s)
	{
		name = s;
	}
	
	public int run(HashMap<String,Integer> hm)
	{
		return (hm.get(name));
	}
}

class DivExpr implements Expr
{
	Expr left,right;
	
	public DivExpr(Expr l,Expr r)
	{
		left = l;
		right = r;
	}
	
	public int run(HashMap<String,Integer> hm)
	{
		int rval = right.run(hm);
		
		if (rval == 0)
		{
			System.out.println("Error: division by zero");
			System.exit(1);
		}
		
		return (left.run(hm) / rval);
	}
}

class MultExpr implements Expr
{
	Expr left,right;
	
	public MultExpr(Expr l,Expr r)
	{
		left = l;
		right = r;
	}
	
	public int run(HashMap<String,Integer> hm)
	{
		return (left.run(hm) * right.run(hm));
	}
}

class MinusExpr implements Expr
{
	Expr left,right;
	
	public MinusExpr(Expr l,Expr r)
	{
		left = l;
		right = r;
	}
	
	public int run(HashMap<String,Integer> hm)
	{
		return (left.run(hm) - right.run(hm));
	}
}

class PlusExpr implements Expr
{
	Expr left,right;
	
	public PlusExpr(Expr l,Expr r)
	{
		left = l;
		right = r;
	}
	
	public int run(HashMap<String,Integer> hm)
	{
		return (left.run(hm) + right.run(hm));
	}
}

class InvExpr implements Expr
{
	Expr expr;
	
	public InvExpr(Expr e)
	{
		expr = e;
	}
	
	public int run(HashMap<String,Integer> hm)
	{
		return (-1 * expr.run(hm));
	}
}

class GteCond implements Condition
{
	Expr left,right;
	
	public GteCond(Expr l,Expr r)
	{
		left = l;
		right = r;
	}
	
	public boolean test(HashMap<String,Integer> hm)
	{
		return (left.run(hm) >= right.run(hm));
	}
}

class GtCond implements Condition
{
	Expr left,right;
	
	public GtCond(Expr l,Expr r)
	{
		left = l;
		right = r;
	}
	
	public boolean test(HashMap<String,Integer> hm)
	{
		return (left.run(hm) > right.run(hm));
	}
}

class LteCond implements Condition
{
	Expr left,right;
	
	public LteCond(Expr l,Expr r)
	{
		left = l;
		right = r;
	}
	
	public boolean test(HashMap<String,Integer> hm)
	{
		return (left.run(hm) <= right.run(hm));
	}
}

class LtCond implements Condition
{
	Expr left,right;
	
	public LtCond(Expr l,Expr r)
	{
		left = l;
		right = r;
	}
	
	public boolean test(HashMap<String,Integer> hm)
	{
		return (left.run(hm) < right.run(hm));
	}
}

class NeqCond implements Condition
{
	Expr left,right;
	
	public NeqCond(Expr l,Expr r)
	{
		left = l;
		right = r;
	}
	
	public boolean test(HashMap<String,Integer> hm)
	{
		return (left.run(hm) != right.run(hm));
	}
}

class EqCond implements Condition
{
	Expr left,right;
	
	public EqCond(Expr l,Expr r)
	{
		left = l;
		right = r;
	}
	
	public boolean test(HashMap<String,Integer> hm)
	{
		return (left.run(hm) == right.run(hm));
	}
}

class OddCond implements Condition
{
	Expr expr;
	
	public OddCond(Expr e)
	{
		expr = e;
	}
	
	public boolean test(HashMap<String,Integer> hm)
	{
		return (expr.run(hm) % 2 == 1);
	}
}

class PrintStmt implements Statement
{
	Expr expr;
	
	public PrintStmt(Expr e)
	{
		expr = e;
	}
	
	public void run(HashMap<String,Integer> hm)
	{
		System.out.println(expr.run(hm));
	}
}

class WhileStmt implements Statement
{
	Condition cond;
	Statement stmt;
	
	public WhileStmt(Condition c, Statement s)
	{
		cond = c;
		stmt = s;
	}
	
	public void run(HashMap<String,Integer> hm)
	{
		while (cond.test(hm))
			stmt.run(hm);
	}
}

class IfStmt implements Statement
{
	Condition cond;
	Statement stmt;
	
	public IfStmt(Condition c, Statement s)
	{
		cond = c;
		stmt = s;
	}
	
	public void run(HashMap<String,Integer> hm)
	{
		if (cond.test(hm))
			stmt.run(hm);
	}
}

class BeginStmt implements Statement
{
	StmtLst stmts;
	
	public BeginStmt(StmtLst sl)
	{
		stmts = sl;
	}
	
	public void run(HashMap<String,Integer> hm)
	{
		stmts.run(hm);
	}
}

class AssignStmt implements Statement
{
	String name;
	Expr val;
	
	public AssignStmt(String i, Expr e)
	{
		name = i;
		val = e;
	}
	
	public void run(HashMap<String,Integer> hm)
	{
		if (hm.containsKey(name))
			hm.put(name,val.run(hm));
		else
		{
			System.out.println("Error: variable "+name+" undeclared");
			System.exit(1);
		}
	}
}

class StmtLst
{
	ArrayList<Statement> stmts;
	
	public StmtLst(Statement s)
	{
		stmts = new ArrayList<Statement>();
		stmts.add(s);
	}
	
	public void add(Statement s)
	{
		stmts.add(s);
	}
	
	public void run(HashMap<String,Integer> hm)
	{
		for(int i=0;i<stmts.size();i++)
			stmts.get(i).run(hm);
	}
}

class VarLst
{
	HashMap<String,Integer> lst;
	
	public VarLst(String id)
	{
		lst = new HashMap<String,Integer>();
		lst.put(id,0);
	}
	
	public void add(String id)
	{
		lst.put(id,0);
	}
	
	public HashMap<String,Integer> hm() { return lst; }
}

class Block
{
	HashMap<String,Integer> vars;
	Statement stmt;
	
	public Block(VarLst vl, Statement s)
	{
		vars = vl.hm();
		stmt = s;
	}
	
	public void run()
	{
		stmt.run(vars);
	}
}

class Program
{
	Block body;
	
	public Program(Block b)
	{
		body = b;
	}
	
	public void exec()
	{
		body.run();
	}
}
