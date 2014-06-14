// Copyright (c) 2014 Jihyeok Park
// KAIST CS420 Project

package Nodes;

import java.util.*;
import java.io.PrintWriter;

public class Call extends Expr {
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

    public Call semantic_analysis(){
        Call c = new Call(name, null, start, end);

        if(name.equals("scanf")){
            Expr arg = args.arr.get(0);
            if(arg instanceof SingleIdExpr){
                SingleIdExpr sarg = (SingleIdExpr)arg;
                STElem ste = get_sym_table_elem(sarg.name);
                ste.get_T(writer);
                writer.println("    READ  MEM(VR(0)@)");
            }else if(arg instanceof ArrayIdExpr){
                ArrayIdExpr aarg = (ArrayIdExpr)arg;
                STElem ste = get_sym_table_elem(aarg.name);

                aarg.expr.semantic_analysis();
                block_idx++;
                writer.println("    MOVE  VR(0)@ VR("+block_idx+")");
                ste.get_T(writer);
                writer.println("    ADD   MEM(VR(0)@)@ VR("+block_idx+")@ VR(0)");
                writer.println("    READ  MEM(VR(0)@)");
                block_idx--;
            }else{
                semantic_error(this,"<scanf> should have an argument with Ident form.");
            }
            this.tn = TypeName.INT;
            return this;
        }else if(name.equals("printf")){
            Expr _arg = args.arr.get(0).semantic_analysis();
            writer.println("    WRITE VR(0)@");

            args.arr.remove(0);
            args.add(_arg);
            this.tn = TypeName.INT;
            return this;
        }else{
            // function existance check
            FuncTable ft = get_fun_table(name);
            int param_size = 0;
            int i;
            if(ft==null) semantic_error(c,"Function "+name+" is not defined.");

            for(i=block_idx;i>=1;i--){
                push("VR("+i+")@");
            }

            if(args!=null){
                c.args = new ArgList();
                c.args.start = args.start;
                c.args.end = args.end;

                // argument size check
                int arg_size = args.arr.size();
                param_size = ft.arr.size();
                if(arg_size != param_size)
                    semantic_error(c,"The number of arguments of function "+name+" should be "+param_size);

                // argument type check
                for(i=0; i<param_size; i++){
                    Expr arg = args.arr.get(i);
                    STElem param = ft.arr.get(i);

                    Expr _arg = arg.semantic_analysis();
                    if(param.is_array()){
                        // array type check
                        if(param.typ.typ == TypeName.INT && _arg.tn != TypeName.INT_ARR)
                            semantic_error(_arg,"Argument"+(i+1)+" of the function "+name+" should have int array type.");
                        if(param.typ.typ == TypeName.FLOAT && _arg.tn != TypeName.FLOAT_ARR)
                            semantic_error(_arg,"Argument"+(i+1)+" of the function "+name+" should have float array type.");
                        push("VR(0)@");
                    }else{
                        if(param.typ.typ == TypeName.INT && _arg.tn != TypeName.INT){
                            if(_arg.tn == TypeName.FLOAT)
                                semantic_warning(_arg,"This expression should have int type.");
                            else
                                semantic_error(_arg,"This expression should have int type.");
                            _arg = new FloatToInt(_arg);
                            writer.println("    F2I   VR(0)@ VR(0)");
                        }
                        if(param.typ.typ == TypeName.FLOAT && _arg.tn != TypeName.FLOAT){
                            if(_arg.tn == TypeName.INT)
                                semantic_warning(_arg,"This expression should have int type.");
                            else
                                semantic_error(_arg,"This expression should have int type.");
                            _arg = new IntToFloat(_arg);
                            writer.println("    I2F   VR(0)@ VR(0)");
                        }
                        push("VR(0)@");
                    }
                    c.args.add(_arg);
                }
            }
            int ret_label_num = label_num++;
            push("_L"+ret_label_num);
            push("FP@");
            writer.println("    MOVE  SP@ FP");
            writer.println("    JMP   "+name);
            writer.println("LAB _L"+ret_label_num);
            writer.println("    SUB   SP@ "+(param_size+1)+" SP");
            for(i=1;i<=block_idx;i++){
                pop("VR("+i+")");
            }
            c.tn = ft.typ.typ;
        }

        return c;
    }
}
