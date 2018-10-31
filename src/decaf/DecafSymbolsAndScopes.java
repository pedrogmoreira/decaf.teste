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
import org.antlr.v4.runtime.tree.TerminalNode;

import decaf.DecafParser.LiteralContext;

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
            decaf.DecafSymbol.Type parameterType = this.getType(ctx.argument_list().TYPE(index).getText());
            ParameterSymbol parameter = new ParameterSymbol(parameterName);
            // System.out.println("Dec_" + ctx.argument_list().TYPE(index);
            parameter.setType(parameterType);

            function.define(parameter);
        }

        ctx.block().statement().forEach(stmt -> 
            {
                if(stmt.return_statement() != null && ctx.method_return().VOID() != null) 
                    this.error(stmt.return_statement().RETURN().getSymbol(), "should not return value");
                
                else if(stmt.return_statement() != null
                    && (this.getType(ctx.method_return().TYPE().getText()) == DecafSymbol.Type.tINT && stmt.return_statement().expression().literal().INT() == null
                    || this.getType(ctx.method_return().TYPE().getText()) == DecafSymbol.Type.tBOOLEAN && stmt.return_statement().expression().literal().BOOLEAN() == null))
                        this.error(stmt.return_statement().RETURN().getSymbol(), "return value has wrong type");
            });

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
        else
        {
            for (int index = 0; index < method.getNumberOfParameters(); index++)
            {
                ParameterSymbol parameter = (ParameterSymbol) method.getSymbols().get(index);
                DecafSymbol.Type parameterType = (DecafSymbol.Type) parameter.getType();
                
                LiteralContext literalContext = ctx.method_call_arguments().expression(index).literal();

                if (parameterType == DecafSymbol.Type.tBOOLEAN && literalContext.BOOLEAN() == null
                    || parameterType == DecafSymbol.Type.tINT && literalContext.INT() == null)
                {
                    if (literalContext.BOOLEAN() != null) 
                    {
                        this.error(literalContext.BOOLEAN().getSymbol(), "type don't match signature"); 
                    } 
                    else if (literalContext.INT() != null) 
                    {
                        this.error(literalContext.INT().getSymbol(), "type don't match signature"); 
                    }
                }
            }
        }
    }

    @Override public void enterField_declaration(DecafParser.Field_declarationContext ctx) { 
        if (ctx.INT() != null && Integer.parseInt(ctx.INT().getSymbol().getText()) <= 0)
        {
            this.error(ctx.INT().getSymbol(), "bad array size: " + ctx.ID().getSymbol().getText());
        }
        else
        {
            if (ctx.INT() != null)
            {
                defineVar(this.getType(ctx.TYPE().getText() + "_array"), ctx.ID().getSymbol());
            }
            else
            {
                defineVar(this.getType(ctx.TYPE().getText()), ctx.ID().getSymbol());
            }
        }
    }

    @Override
    public void enterVar_declaration(DecafParser.Var_declarationContext ctx) {
        for (int index = 0; index < ctx.ID().size(); index++) 
        {
            defineVar(this.getType(ctx.TYPE().get(index).getText()), ctx.ID().get(index).getSymbol());
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

        if (ctx.LSQUARE() != null) {
            if (ctx.expression().location() != null) 
            {
                String locationName = ctx.expression().location().ID().getText();
                System.out.println(((VariableSymbol) currentScope.resolve(locationName)).getType());
                DecafSymbol.Type type = (DecafSymbol.Type) ((VariableSymbol) currentScope.resolve(locationName)).getType();
    
                if (type != DecafSymbol.Type.tINT) {
                    this.error(ctx.ID().getSymbol(), "array index has wrong type");
                }                
            } 
            else if (ctx.expression().literal().BOOLEAN() != null) 
            {
                this.error(ctx.ID().getSymbol(), "array index has wrong type");               
            }
        }

        if ( var==null ) {
            this.error(ctx.ID().getSymbol(), "no such variable: "+name);
        }
        if ( var instanceof FunctionSymbol ) {
            this.error(ctx.ID().getSymbol(), name+" is not a variable");
        }

    }

    @Override 
    public void enterStatement(DecafParser.StatementContext ctx) 
    { 
        // verificar atribuição de variavel
        if (ctx.location() != null) 
        {
            VariableSymbol symbol = (VariableSymbol) currentScope.resolve(ctx.location().ID().getSymbol().getText());  
            Type type = symbol.getType();
            
            // verificar tipo de atribuicao com variavel
            if (ctx.expression().location() != null) 
            {
                VariableSymbol symbol2 = (VariableSymbol) currentScope.resolve(ctx.expression().location().ID().getSymbol().getText());  
                Type type2 = symbol2.getType();

                if ((type == DecafSymbol.Type.tINT_ARRAY && type2 != DecafSymbol.Type.tINT) ||
                    (type == DecafSymbol.Type.tBOOLEAN_ARRAY && type2 != DecafSymbol.Type.tBOOLEAN) ||
                    (type == DecafSymbol.Type.tCHAR_ARRAY && type2 != DecafSymbol.Type.tCHAR)) {
                    this.error(ctx.location().ID().getSymbol(), "tipo incorreto em atribuição");
                }
            }
            // verificar tipo de atribuicao com literal
            else if (ctx.expression().literal() != null)
            {
                if ((ctx.expression().literal().CHAR() != null && type != DecafSymbol.Type.tCHAR_ARRAY) ||
                    (ctx.expression().literal().INT() != null && type != DecafSymbol.Type.tINT_ARRAY) ||
                    (ctx.expression().literal().BOOLEAN() != null && type != DecafSymbol.Type.tBOOLEAN_ARRAY)) {
                    this.error(ctx.location().ID().getSymbol(), "tipo incorreto em atribuição");
                }
            }
        }
    }

    @Override 
    public void enterIf_block(DecafParser.If_blockContext ctx) 
    { 
        //verificar se variavel é BOOLEAN
        if (ctx.expression().location() != null) {
            VariableSymbol symbol = (VariableSymbol) currentScope.resolve(ctx.expression().location().ID().getSymbol().getText());  
            Type type = symbol.getType();

            if (type != DecafSymbol.Type.tBOOLEAN || type != DecafSymbol.Type.tBOOLEAN_ARRAY) {
                this.error(ctx.LPARENT().getSymbol(), "condição deve ser do tipo BOOLEAN");
            }
        }
        //verificar se literal é BOOLEAN
        else if (ctx.expression().literal().BOOLEAN() == null) {
            this.error(ctx.LPARENT().getSymbol(), "condição deve ser do tipo BOOLEAN");
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
    public static DecafSymbol.Type getType(String tokenType) {
        switch ( tokenType ) {
            case "void" :  return DecafSymbol.Type.tVOID;
            case "int":   return DecafSymbol.Type.tINT;
            case "char":   return DecafSymbol.Type.tCHAR;
            case "boolean":   return DecafSymbol.Type.tBOOLEAN;
            case "int_array":   return DecafSymbol.Type.tINT_ARRAY;
            case "char_array":   return DecafSymbol.Type.tCHAR_ARRAY;
            case "boolean_array":   return DecafSymbol.Type.tBOOLEAN_ARRAY;
        }
        return DecafSymbol.Type.tINVALID;
    }
}