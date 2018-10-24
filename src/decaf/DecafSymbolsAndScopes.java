package decaf;

import java.util.HashMap;
import java.util.List;
import org.antlr.symtab.FunctionSymbol;
import org.antlr.symtab.ParameterSymbol;
import org.antlr.symtab.GlobalScope;
import org.antlr.symtab.LocalScope;
import org.antlr.symtab.Scope;
import org.antlr.symtab.VariableSymbol;
import org.antlr.symtab.Symbol;
import org.antlr.symtab.Type;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeProperty;


/**
 * This class defines basic symbols and scopes for Decaf language
 */
public class DecafSymbolsAndScopes extends DecafParserBaseListener {
    ParseTreeProperty<Scope> scopes = new ParseTreeProperty<Scope>();
    GlobalScope globals;
    Scope currentScope; // define symbols in this scope

    @Override
    public void enterProgram(DecafParser.ProgramContext ctx) {
        globals = new GlobalScope(null);
        pushScope(globals);
    }

    @Override
    public void exitProgram(DecafParser.ProgramContext ctx) {
        System.out.println(globals);

        if (globals.resolve("main") == null)
        {
            this.error(ctx.RCURLY().getSymbol(), "Método \'main\' não encontrado.");
        }
    }

    @Override
    public void enterMethod_declaration(DecafParser.Method_declarationContext ctx)  {
        String name = ctx.method_name().ID().getSymbol().getText();
        //int typeTokenType = ctx.TYPE();
        //DecafSymbol.Type type = this.getType(typeTokenType);

        // push new scope by making new one that points to enclosing scope
        FunctionSymbol function = new FunctionSymbol(name);
        // function.get
        // function.setType(type); // Set symbol type

        for (int index = 0; index < ctx.argument_list().TYPE().size(); index ++)
        {
            String parameterName = ctx.argument_list().ID(index).getSymbol().getText();
            Type parameterType = this.getType(ctx.argument_list().TYPE(index).getSymbol().getType());
            ParameterSymbol parameter = new ParameterSymbol(parameterName);
            parameter.setType(parameterType);

            function.define(parameter);
        }

        currentScope.define(function); // Define function in current scope
        saveScope(ctx, function);
        pushScope(function);
    }

    @Override
    public void exitMethod_declaration(DecafParser.Method_declarationContext ctx) {
        popScope();
    }

    @Override
    public void enterBlock(DecafParser.BlockContext ctx) {
        LocalScope l = new LocalScope(currentScope);
        saveScope(ctx, currentScope);
        pushScope(l);
    }

    @Override
    public void exitBlock(DecafParser.BlockContext ctx) {
        popScope();
    }

    @Override public void enterMethod_call(DecafParser.Method_callContext ctx) 
    {
        String methodName = ctx.method_name().ID().getSymbol().getText();
        FunctionSymbol method = (FunctionSymbol) currentScope.resolve(methodName);
        
        if (method.getNumberOfParameters() != ctx.method_call_arguments().expression().size())
        {
            this.error(ctx.method_name().ID().getSymbol(), "argument mismatch: " + methodName);
        }
    }

    @Override public void enterField_declaration(DecafParser.Field_declarationContext ctx) { 
        if (Integer.parseInt(ctx.INT().getSymbol().getText()) <= 0)
        {
            this.error(ctx.INT().getSymbol(), "bad array size: " + ctx.ID().getSymbol().getText());
        }
        else
        {
            defineVar(this.getType(ctx.TYPE().getSymbol().getType()), ctx.ID().getSymbol());
        }
    }

    @Override
    public void enterVar_declaration(DecafParser.Var_declarationContext ctx) {
        for (int index = 0; index < ctx.ID().size(); index++) 
        {
            defineVar(this.getType(ctx.TYPE().get(index).getSymbol().getType()), ctx.ID().get(index).getSymbol());
        }

    }

    @Override
    public void exitVar_declaration(DecafParser.Var_declarationContext ctx) {
        for (TerminalNode node : ctx.ID()) {   
            String name = node.getSymbol().getText();
            Symbol var = currentScope.resolve(name);
            if ( var==null ) {
                this.error(node.getSymbol(), "no such variable: "+name);
            }
            if ( var instanceof FunctionSymbol ) {
                this.error(node.getSymbol(), name+" is not a variable");
            }
        }
    }

    @Override
    public void enterLocation(DecafParser.LocationContext ctx)
    {
        String name = ctx.ID().getSymbol().getText();
        Symbol var = currentScope.resolve(name);

        if ( var==null ) {
            this.error(ctx.ID().getSymbol(), "no such variable: "+name);
        }
        if ( var instanceof FunctionSymbol ) {
            this.error(ctx.ID().getSymbol(), name+" is not a variable");
        }

    }

    void defineVar(DecafSymbol.Type typeCtx, Token nameToken) {
        //int typeTokenType = typeCtx.getTypeIndex();
        VariableSymbol var = new VariableSymbol(nameToken.getText());

        // DecafSymbol.Type type = this.getType(typeTokenType);
        var.setType(typeCtx);

        List symbols = currentScope.getSymbols();

        if (!symbols.contains(var)) 
        {
            currentScope.define(var); // Define symbol in current scope
        } 
        else
        {
            this.error(nameToken, "Variável já declarada");
        }
    }

    /**
     * Método que atuliza o escopo para o atual e imprime o valor
     *
     * @param s
     */
    private void pushScope(Scope s) {
        currentScope = s;
        System.out.println("entering: "+currentScope.getName()+":"+s);
    }

    /**
     * Método que cria um novo escopo no contexto fornecido
     *
     * @param ctx
     * @param s
     */
    void saveScope(ParserRuleContext ctx, Scope s) {
        scopes.put(ctx, s);
    }

    /**
     * Muda para o contexto superior e atualia o escopo
     */
    private void popScope() {
        System.out.println("leaving: "+currentScope.getName()+":"+currentScope);
        currentScope = currentScope.getEnclosingScope();
    }

    public static void error(Token t, String msg) {
        System.err.printf("line %d:%d %s\n", t.getLine(), t.getCharPositionInLine(),
                msg);
    }

    /**
     * Valida tipos encontrados na linguagem para tipos reais
     *
     * @param tokenType
     * @return
     */
    public static DecafSymbol.Type getType(int tokenType) {
        switch ( tokenType ) {
            case DecafParser.VOID :  return DecafSymbol.Type.tVOID;
            case DecafParser.INT :   return DecafSymbol.Type.tINT;
        }
        return DecafSymbol.Type.tINVALID;
    }


}
