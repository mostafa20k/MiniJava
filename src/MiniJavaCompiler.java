import gen.MiniJavaListener;
import gen.MiniJavaParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

public class MiniJavaCompiler implements MiniJavaListener {
    ArrayList<Scope> scopes = new ArrayList<>();
    private int indent = 0;
    private String output = "";
    private ArrayList<String> varsList = new ArrayList<>();

    Scope findScope(String name){
        for (var scope : scopes){
            if(scope.getName().equals(name))
                return scope;
        }
        return null;
    }
    Scope findScope(Type type){
        for (var scope : scopes){
            if(scope.getType() == type)
                return scope;
        }
        return null;
    }

    Scope findScope(int line){
        for (var scope : scopes){
            if(scope.getLine() == line)
                return scope;
        }
        return null;
    }
    ParserRuleContext getParentScopeRule(ParserRuleContext ctx){
        var parent = ctx.getParent();
        var scopeList = new ArrayList<Class>(Arrays.asList(
                MiniJavaParser.ProgramContext.class,
                MiniJavaParser.MainClassContext.class,
                MiniJavaParser.MainMethodContext.class,
                MiniJavaParser.ClassDeclarationContext.class,
                MiniJavaParser.InterfaceDeclarationContext.class,
                MiniJavaParser.MethodDeclarationContext.class,
                MiniJavaParser.WhileStatementContext.class,
                MiniJavaParser.NestedStatementContext.class,
                MiniJavaParser.ElseBlockContext.class,
                MiniJavaParser.IfBlockContext.class
                ));

        while (!scopeList.contains(parent.getClass())){
            var _temp = parent.getParent();

            if(parent.getParent() != null)
                parent = parent.getParent();
            else
                return null;
        }

        return parent;
    }
    Scope getParentScope(ParserRuleContext ctx){
        var parent_scope_rule = getParentScopeRule(ctx);

        return findScope(parent_scope_rule.start.getLine());
    }


    @Override
    public void enterProgram(MiniJavaParser.ProgramContext ctx) {
        Scope program = new Scope("program" , ctx.start.getLine(),ctx.start.getCharPositionInLine(),
                ctx.getRuleIndex() , Type.PROGRAM , null , ReturnType.VOID);
        scopes.add(program);


    }
    public void cycleHandling(){

        Scope scope = scopes.get(0);
        Map<String, List<String>> inheritanceGraph = new HashMap<>();
        LinkedHashMap<String, SymbolTableItem> hashtable = scope.getHashtable();
        for(Map.Entry<String, SymbolTableItem> entry : hashtable.entrySet()) {
            String key = entry.getKey();
            SymbolTableItem value = entry.getValue();
            if (value.type == Type.Class){
                if(value.inherits!= null){

                    inheritanceGraph.put(key.substring(6), Arrays.asList(value.inherits));
                }
            }
        }
        List<List<String>> cyclicPaths = detectAllCycles(inheritanceGraph);
        if (!cyclicPaths.isEmpty()) {
            System.out.println("Error410 : Invalid inheritance ");
            for (List<String> cycle : cyclicPaths) {
                System.out.print("[");
                for (String node : cycle) {
                    System.out.print(node);
                    if (node != cycle.get(cycle.size()-1))
                        System.out.print(" -> ");
                }
                System.out.print("]");
                System.out.println();
            }
        }
    }
    public List<List<String>> detectAllCycles(Map<String, List<String>> inheritanceGraph) {
        Set<String> visited = new HashSet<>();
        Set<String> currentlyVisiting = new HashSet<>();
        List<List<String>> cyclicPaths = new ArrayList<>();

        for (String node : inheritanceGraph.keySet()) {
            if (!visited.contains(node)) {
                List<String> path = new ArrayList<>();
                if (detectAllCyclesUtil(node, inheritanceGraph, visited, currentlyVisiting, path, cyclicPaths)) {
                    return cyclicPaths;
                }
            }
        }

        return cyclicPaths;
    }
    private boolean detectAllCyclesUtil(String currentClass, Map<String, List<String>> inheritanceGraph,
                                               Set<String> visited, Set<String> currentlyVisiting, List<String> path,
                                               List<List<String>> cyclicPaths) {

        currentlyVisiting.add(currentClass);
        path.add(currentClass);

        for (String child : inheritanceGraph.getOrDefault(currentClass, new ArrayList<>())) {
            if (currentlyVisiting.contains(child)) {
                // Cycle detected, add the current path to cyclicPaths
                path.add(child);
                int startIndex = path.indexOf(child);
                List<String> cyclicPath = new ArrayList<>(path.subList(startIndex, path.size()));
                cyclicPaths.add(cyclicPath);
                return true;
            }

            if (!visited.contains(child) && detectAllCyclesUtil(child, inheritanceGraph, visited, currentlyVisiting, path, cyclicPaths)) {
                return true;
            }
        }

        currentlyVisiting.remove(currentClass);
        visited.add(currentClass);
        path.remove(path.size() - 1);

        return false;
    }
    public void implementHandling(){
        Scope programScope = scopes.get(0);
        LinkedHashMap<String, SymbolTableItem> hashtable = programScope.getHashtable();
        for(Map.Entry<String, SymbolTableItem> entry : hashtable.entrySet()) {
            String key = entry.getKey();
            SymbolTableItem value = entry.getValue();
            List<String>implementList = new ArrayList<>();
            if (value.type == Type.Class){
//                int start = value.dec_type.lastIndexOf("implements");
                if(value.implement != null){
                    implementList = convertStringToList(value.implement);
                    Scope target = findScope(value.name);
                    for (String implement: implementList){
                        Scope temp = findScope(implement);
                        if (temp == null){
                            System.out.println("Error421: class " + value.name + " must implement valid class/interface");
                        }else {
                            LinkedHashMap<String, SymbolTableItem> tempHashtable =temp.getHashtable();
                            for(Map.Entry<String, SymbolTableItem> tempEntry : tempHashtable.entrySet()){
                                if (tempEntry.getValue().type == Type.METHOD){
                                    boolean same = false;
                                    LinkedHashMap<String, SymbolTableItem> targetHashtable =target.getHashtable();
                                    for(Map.Entry<String, SymbolTableItem> targetEntry : tempHashtable.entrySet()){
                                        if (tempEntry.getValue().name.equals(targetEntry.getValue().name) && tempEntry.getValue().dec_type.equals(targetEntry.getValue().dec_type)){
                                            if (!tempEntry.getValue().access.equals(targetEntry.getValue().access)){
                                                System.out.println("Error320: in Line ["+ target.getLine() +":"+target.getColumn() + "]  the access level cannot be more restrictive than the overridden method's access level");

                                            }
                                            same = true;
                                        }
                                    }
                                    if (!same){
                                        System.out.println("Error420: class ["+ value.name + "] must implement all abstract methods");
                                        break;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static List<String> convertStringToList(String input) {
        List<String> stringList = new ArrayList<>();

        if (input.contains(",")) {
            String[] tokens = input.trim().split("\\s*,\\s*");

            for (String token : tokens) {
                stringList.add(token.trim());
            }
        } else {
            stringList.add(input.trim());
        }

        return stringList;
    }

    @Override
    public void exitProgram(MiniJavaParser.ProgramContext ctx) {
        System.out.println("Unused Vars: " + varsList);
        printText();
        cycleHandling();
        implementHandling();
        for (var scope : scopes){
            var parent = scope.getParent();
            if(parent != null && parent.getType() != Type.PROGRAM) {
                System.out.println("nested " + parent.getName() + " " + parent.getLine() + " : ");
            }
            else {
                System.out.println("=============================================");
            }

            System.out.println(scope.toString());
        }
    }

    @Override
    public void enterMainClass(MiniJavaParser.MainClassContext ctx) {
        Scope scope = new Scope(ctx.className.getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ctx.getRuleIndex(), Type.MainClass, getParentScope(ctx), ReturnType.VOID);
        scopes.add(scope);

        var programScope = findScope(Type.PROGRAM);
        programScope.getHashtable().put("mainclass_"+ ctx.className.getText(),
                new SymbolTableItem(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.className.getText(),
                        null, Type.MainClass, null, null, null, null));

        output += "class " + ctx.className.getText() + " {\n";
        indent++;
    }

    @Override
    public void exitMainClass(MiniJavaParser.MainClassContext ctx) {
        output += "}\n";
        indent--;
    }

    @Override
    public void enterMainMethod(MiniJavaParser.MainMethodContext ctx) {
        Scope scope = new Scope("method_main", ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ctx.getRuleIndex(), Type.MainMethod, getParentScope(ctx), ReturnType.VOID);

        var mainClassScope = findScope(Type.MainClass);
        mainClassScope.getHashtable().put("method_main",
                new SymbolTableItem(ctx.start.getLine(), ctx.start.getCharPositionInLine(), "main",
                    "void", Type.MainMethod,null, null, null, ctx.type().getText()));

        String pramType = convertType(ctx.type().getText());

        String temp="";
        temp += "(type: " + pramType;
        scope.getHashtable().put("var_args",
                new SymbolTableItem(ctx.start.getLine(),ctx.start.getCharPositionInLine(),
                        "args", temp, Type.MethodParam,null, null, null, null));

        scopes.add(scope);

        output += printIndent(indent) + "public static void main (" + pramType + " "
                + ctx.Identifier().getText() + ") {\n";
        indent++;
    }

    @Override
    public void exitMainMethod(MiniJavaParser.MainMethodContext ctx) {
        indent--;
        output += printIndent(indent) + "}\n";
    }


    @Override
    public void enterClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        Scope scope = new Scope(ctx.className.getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ctx.getRuleIndex(), Type.Class, getParentScope(ctx), null);
        scopes.add(scope);
        String impl = "";
        int num_id = 1;
        int flag = 0;

        var programScope = findScope(Type.PROGRAM);

        if (!ctx.getText().contains("inherits")) {
            output += "class " + ctx.className.getText();

        } else {
            output += "class " + ctx.className.getText() + " extends " + ctx.Identifier().get(1);
            num_id++;
            flag++;
        }

        if (ctx.getText().contains("implements")) {
            output += " implements ";
            int size = ctx.Identifier().size();
            for (int i = num_id; i < size; i++) {
                output += ctx.Identifier(i);
                impl += ctx.Identifier(i);
                if (i != size - 1) {
                    output += ", ";
                    impl += ", ";
                }
            }
            flag++;
        }

        if(flag==0){
            programScope.getHashtable().put("class_"+ ctx.className.getText(),
                    new SymbolTableItem(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.className.getText(),
                            null, Type.Class, null, null, null, null));
        }else if(flag==1){
            programScope.getHashtable().put("class_"+ ctx.className.getText(),
                    new SymbolTableItem(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.className.getText(),
                            null, Type.Class, ctx.Identifier(1).getText(), null, null, null));
        }else if(flag==2){
            programScope.getHashtable().put("class_"+ ctx.className.getText(),
                    new SymbolTableItem(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.className.getText(),
                            null, Type.Class, ctx.Identifier(1).getText(), impl, null, null));
        }

        output += "{\n";
        indent++;
    }

    @Override
    public void exitClassDeclaration(MiniJavaParser.ClassDeclarationContext ctx) {
        indent--;
        output += printIndent(indent) + "}\n";
    }

    @Override
    public void enterInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx) {
        Scope scope = new Scope(ctx.Identifier().getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ctx.getRuleIndex(), Type.Interface, getParentScope(ctx), null);
        scopes.add(scope);

        var programScope = findScope(Type.PROGRAM);
        programScope.getHashtable().put("interface_"+ ctx.Identifier().getText(),
                new SymbolTableItem(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.Identifier().getText(),
                        null, Type.Interface,null,null, null,null));

        output += "interface " + ctx.Identifier() + "{\n";
        indent++;

    }

    @Override
    public void exitInterfaceDeclaration(MiniJavaParser.InterfaceDeclarationContext ctx) {
        indent--;
        output += "}";

    }

    @Override
    public void enterInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx) {
        String returnType = convertType(ctx.returnType().getText());

        ReturnType enum_returnType = ReturnType.VOID;
        switch (returnType){
            case "number":
                enum_returnType = ReturnType.INT;
                break;
            case "boolean":
                enum_returnType = ReturnType.BOOLEAN;
                break;
            case "void":
                enum_returnType = ReturnType.VOID;
                break;
        }

        String parameters = "";
        int size = ctx.parameterList().parameter().size();
        for (int i = 0; i < size; i++) {
            String type = convertType(ctx.parameterList().parameter(i).type().getText());
            parameters += type + " " + ctx.parameterList().parameter(i).Identifier();

            if (i != size - 1) {
//                output += ", ";
                parameters += ", ";
            }

        }
        var programScope = getParentScope(ctx);
        programScope.getHashtable().put("method_"+ ctx.Identifier().getText(),
                new SymbolTableItem(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.Identifier().getText(),
                        returnType, Type.METHOD, null,null,
                        ctx.accessModifier()!=null? ctx.accessModifier().getText(): null,
                        parameters));

        String access = new String();
        if (ctx.accessModifier() != null) {
            access = ctx.accessModifier().getText() + " ";
        }

        output += printIndent(indent) + access + returnType + " " + ctx.Identifier().getText() +
                '(';
    }

    @Override
    public void exitInterfaceMethodDeclaration(MiniJavaParser.InterfaceMethodDeclarationContext ctx) {
        output += ");\n";
    }

    @Override
    public void enterFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx) {
        //printIndent(indent);
        String total_field;
        String access = new String();
        String Final = new String();
        if (ctx.accessModifier() != null) {
            access = ctx.accessModifier().getText() + " ";
        }
        if (ctx.Final() != null) {
            Final = ctx.Final().getText();
        }
        String type = convertType(ctx.type().getText());
        String identifier = ctx.Identifier().getText();
        String EQ = new String();
        String expression = new String();
        if (ctx.EQ() != null) {
            EQ = ctx.EQ().getText()+" ";
            expression = replaceExp(ctx.expression().getText());
        }
        total_field = printIndent(indent) +
                access.concat(Final).concat(" " + type + " ").concat(identifier + " ")
                        .concat(EQ).concat(expression);
        output += total_field;


        Scope parent = getParentScope(ctx);

        parent.getHashtable().put("var_"+ ctx.Identifier().getText(),
                new SymbolTableItem(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.Identifier().getText(),
                        null, Type.Field, null,
                        null, ctx.accessModifier()!=null? ctx.accessModifier().getText(): null, null));
    }
    @Override
    public void exitFieldDeclaration(MiniJavaParser.FieldDeclarationContext ctx) {
        output += ";\n";
    }

    @Override
    public void enterLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx) {
        String type = convertType(ctx.type().getText()) + " ";
        output += printIndent(indent) + type + ctx.Identifier().getText();
        Scope parent_scope = getParentScope(ctx);
        parent_scope.getHashtable().put("var_" + ctx.Identifier() , new SymbolTableItem(ctx.start.getLine(),
                ctx.start.getCharPositionInLine(),ctx.Identifier().getText(),type,Type.LocalVar, null, null, null, null));
        varsList.add(ctx.Identifier().getText());

    }

    @Override
    public void exitLocalDeclaration(MiniJavaParser.LocalDeclarationContext ctx) {
        output += ";\n";
    }

    @Override
    public void enterMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        String access = new String();
        String override = new String();
        if (ctx.accessModifier() != null) {
            access = ctx.accessModifier().getText() + " ";
        }
        if (ctx.Override() != null) {
            override = ctx.Override().getText() + "\n";
        }
        output += printIndent(indent) + override + printIndent(indent) +access +
                ctx.returnType().getText() + " " + ctx.Identifier() + " (";

        String returnType = convertType(ctx.returnType().getText());

        ReturnType enum_returnType = ReturnType.VOID;
        switch (returnType){
            case "number":
                enum_returnType = ReturnType.INT;
                break;
            case "boolean":
                enum_returnType = ReturnType.BOOLEAN;
                break;
            case "void":
                enum_returnType = ReturnType.VOID;
                break;
        }

        Scope scope = new Scope(ctx.Identifier().getText(), ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ctx.getRuleIndex(), Type.METHOD, getParentScope(ctx), enum_returnType);
        scopes.add(scope);

        String parameters = "";
        int size = ctx.parameterList().parameter().size();
        for (int i = 0; i < size; i++) {
            String type = convertType(ctx.parameterList().parameter(i).type().getText());
            parameters += type + " " + ctx.parameterList().parameter(i).Identifier();

            if (i != size - 1) {
//                output += ", ";
                parameters += ", ";
            }

        }

        var programScope = findScope(Type.Class);
        programScope.getHashtable().put("method_"+ ctx.Identifier().getText(),
                new SymbolTableItem(ctx.start.getLine(), ctx.start.getCharPositionInLine(), ctx.Identifier().getText(),
                        returnType, Type.METHOD, null, null,
                        ctx.accessModifier()!=null? ctx.accessModifier().getText(): null,
                        parameters));
    }
    @Override
    public void exitMethodDeclaration(MiniJavaParser.MethodDeclarationContext ctx) {
        output += "\n";
    }

    @Override
    public void enterParameterList(MiniJavaParser.ParameterListContext ctx) {
        Scope parent_scope = getParentScope(ctx);
        String parameters = "";
        int size = ctx.parameter().size();
        for (int i = 0; i < size; i++) {
            String type = convertType(ctx.parameter(i).type().getText());
            output += type + " " + ctx.parameter(i).Identifier();
            parameters += type + " " + ctx.parameter(i).Identifier();

            if (i != size - 1) {
                output += ", ";
                parameters += ", ";
            }

        }

    }

    @Override
    public void exitParameterList(MiniJavaParser.ParameterListContext ctx) {

    }

    @Override
    public void enterParameter(MiniJavaParser.ParameterContext ctx) {

    }

    @Override
    public void exitParameter(MiniJavaParser.ParameterContext ctx) {

    }

    @Override
    public void enterMethodBody(MiniJavaParser.MethodBodyContext ctx) {
        output += "){\n";
        indent++;
    }

    @Override
    public void exitMethodBody(MiniJavaParser.MethodBodyContext ctx) {
        if(ctx.RETURN()!=null){
            output += ctx.RETURN().getText() + replaceExp(ctx.expression().getText()) + ";";
        }
        indent--;
        output += printIndent(indent) + "}";


    }

    @Override
    public void enterType(MiniJavaParser.TypeContext ctx) {

    }

    @Override
    public void exitType(MiniJavaParser.TypeContext ctx) {

    }

    @Override
    public void enterBooleanType(MiniJavaParser.BooleanTypeContext ctx) {

    }

    @Override
    public void exitBooleanType(MiniJavaParser.BooleanTypeContext ctx) {

    }

    @Override
    public void enterReturnType(MiniJavaParser.ReturnTypeContext ctx) {

    }

    @Override
    public void exitReturnType(MiniJavaParser.ReturnTypeContext ctx) {

    }

    @Override
    public void enterAccessModifier(MiniJavaParser.AccessModifierContext ctx) {

    }

    @Override
    public void exitAccessModifier(MiniJavaParser.AccessModifierContext ctx) {

    }

    @Override
    public void enterNestedStatement(MiniJavaParser.NestedStatementContext ctx) {
        output += "{\n";
        indent++;
        Scope scope = new Scope("Nested", ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ctx.getRuleIndex(), Type.NESTED, getParentScope(ctx), null);
        scopes.add(scope);
    }

    @Override
    public void exitNestedStatement(MiniJavaParser.NestedStatementContext ctx) {
        indent--;
        output += printIndent(indent) + "}\n";
    }

    @Override
    public void enterIfElseStatement(MiniJavaParser.IfElseStatementContext ctx) {
        output += printIndent(indent) + "if" + ctx.LP() +
                replaceExp(ctx.expression().getText()) + ctx.RP();

    }

    @Override
    public void exitIfElseStatement(MiniJavaParser.IfElseStatementContext ctx) {

    }

    @Override
    public void enterWhileStatement(MiniJavaParser.WhileStatementContext ctx) {
        output += printIndent(indent) + "while" + ctx.LP()
                + replaceExp(ctx.expression().getText()) + ctx.RP();
        Scope scope = new Scope("While", ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                ctx.getRuleIndex(), Type.While, getParentScope(ctx), null);
        scopes.add(scope);
    }

    @Override
    public void exitWhileStatement(MiniJavaParser.WhileStatementContext ctx) {

    }

    @Override
    public void enterPrintStatement(MiniJavaParser.PrintStatementContext ctx) {
        output += printIndent(indent) + "System.out.print" + ctx.LP() +
                replaceExp(ctx.expression().getText()) + ctx.RP();
    }

    @Override
    public void exitPrintStatement(MiniJavaParser.PrintStatementContext ctx) {
        output += ";\n";
    }

    @Override
    public void enterVariableAssignmentStatement(MiniJavaParser.VariableAssignmentStatementContext ctx) {
        output += printIndent(indent) + replaceExp(ctx.expression().get(0).getText()) + ctx.EQ()
                + replaceExp(ctx.expression().get(1).getText());
        int sizeVarList = varsList.size();
        List<String> itemsToRemove = new ArrayList<>();

        for (int i = 0; i < sizeVarList; i++) {
            String var = varsList.get(i);
            if (ctx.expression(0).getText().contains(var) || ctx.expression(1).getText().contains(var)) {
                itemsToRemove.add(var);
            }
        }

        varsList.removeAll(itemsToRemove);
    }

    @Override
    public void exitVariableAssignmentStatement(MiniJavaParser.VariableAssignmentStatementContext ctx) {
        output += ";\n";
    }

    @Override
    public void enterArrayAssignmentStatement(MiniJavaParser.ArrayAssignmentStatementContext ctx) {
        output += printIndent(indent) + ctx.Identifier().getText() + ctx.LSB() +
                replaceExp(ctx.expression().get(0).getText()) + ctx.RSB() + ctx.EQ() +
                replaceExp(ctx.expression().get(0).getText());
    }

    @Override
    public void exitArrayAssignmentStatement(MiniJavaParser.ArrayAssignmentStatementContext ctx) {
        output += ";\n";
    }

    @Override
    public void enterLocalVarDeclaration(MiniJavaParser.LocalVarDeclarationContext ctx) {


    }

    @Override
    public void exitLocalVarDeclaration(MiniJavaParser.LocalVarDeclarationContext ctx) {

    }

    @Override
    public void enterExpressioncall(MiniJavaParser.ExpressioncallContext ctx) {
        output += printIndent(indent) +
                replaceExp(ctx.expression().getText().substring(0, ctx.getText().length() - 1));
    }

    @Override
    public void exitExpressioncall(MiniJavaParser.ExpressioncallContext ctx) {
        output += ";\n";
    }

    @Override
    public void enterIfBlock(MiniJavaParser.IfBlockContext ctx) {
        if (ctx.start.getText().equals("{")) {
            output += "";
        } else {
            Scope scope = new Scope("IF", ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                    ctx.getRuleIndex(), Type.IF, getParentScope(ctx), null);
            scopes.add(scope);
            output += "\n";
            indent++;
        }
    }

    @Override
    public void exitIfBlock(MiniJavaParser.IfBlockContext ctx) {
        if (ctx.stop.getText().equals("}")) {
            output += "";
        } else {
            output += "";
            indent--;
        }
    }

    @Override
    public void enterElseBlock(MiniJavaParser.ElseBlockContext ctx) {
        if (ctx.start.getText().equals("{")) {
            output += printIndent(indent) + "else";
        } else {
            Scope scope = new Scope("Else", ctx.start.getLine(), ctx.start.getCharPositionInLine(),
                    ctx.getRuleIndex(), Type.Else, getParentScope(ctx), null);
            scopes.add(scope);
            output += printIndent(indent) + "else" + "\n";
            indent++;
        }
    }

    @Override
    public void exitElseBlock(MiniJavaParser.ElseBlockContext ctx) {
        if (ctx.stop.getText().equals("}")) {
            output += "";
        } else {
            output += "";
            indent--;
        }
    }

    @Override
    public void enterWhileBlock(MiniJavaParser.WhileBlockContext ctx) {
        if (ctx.start.getText().equals("{")) {
            output += "";
        } else {
            output += "\n";
            indent++;
        }
    }

    @Override
    public void exitWhileBlock(MiniJavaParser.WhileBlockContext ctx) {

        if (ctx.stop.getText().equals("}")) {
            output += "";
        } else {
            output += "";
            indent--;
        }
    }

    @Override
    public void enterLtExpression(MiniJavaParser.LtExpressionContext ctx) {

    }

    @Override
    public void exitLtExpression(MiniJavaParser.LtExpressionContext ctx) {

    }

    @Override
    public void enterObjectInstantiationExpression(MiniJavaParser.ObjectInstantiationExpressionContext ctx) {

    }

    @Override
    public void exitObjectInstantiationExpression(MiniJavaParser.ObjectInstantiationExpressionContext ctx) {

    }

    @Override
    public void enterArrayInstantiationExpression(MiniJavaParser.ArrayInstantiationExpressionContext ctx) {

    }

    @Override
    public void exitArrayInstantiationExpression(MiniJavaParser.ArrayInstantiationExpressionContext ctx) {

    }

    @Override
    public void enterPowExpression(MiniJavaParser.PowExpressionContext ctx) {

    }

    @Override
    public void exitPowExpression(MiniJavaParser.PowExpressionContext ctx) {

    }

    @Override
    public void enterIdentifierExpression(MiniJavaParser.IdentifierExpressionContext ctx) {

    }

    @Override
    public void exitIdentifierExpression(MiniJavaParser.IdentifierExpressionContext ctx) {

    }

    @Override
    public void enterMethodCallExpression(MiniJavaParser.MethodCallExpressionContext ctx) {

    }

    @Override
    public void exitMethodCallExpression(MiniJavaParser.MethodCallExpressionContext ctx) {

    }

    @Override
    public void enterNotExpression(MiniJavaParser.NotExpressionContext ctx) {

    }

    @Override
    public void exitNotExpression(MiniJavaParser.NotExpressionContext ctx) {

    }

    @Override
    public void enterBooleanLitExpression(MiniJavaParser.BooleanLitExpressionContext ctx) {

    }

    @Override
    public void exitBooleanLitExpression(MiniJavaParser.BooleanLitExpressionContext ctx) {

    }

    @Override
    public void enterParenExpression(MiniJavaParser.ParenExpressionContext ctx) {

    }

    @Override
    public void exitParenExpression(MiniJavaParser.ParenExpressionContext ctx) {

    }

    @Override
    public void enterIntLitExpression(MiniJavaParser.IntLitExpressionContext ctx) {

    }

    @Override
    public void exitIntLitExpression(MiniJavaParser.IntLitExpressionContext ctx) {

    }

    @Override
    public void enterStringLitExpression(MiniJavaParser.StringLitExpressionContext ctx) {

    }

    @Override
    public void exitStringLitExpression(MiniJavaParser.StringLitExpressionContext ctx) {

    }

    @Override
    public void enterNullLitExpression(MiniJavaParser.NullLitExpressionContext ctx) {

    }

    @Override
    public void exitNullLitExpression(MiniJavaParser.NullLitExpressionContext ctx) {

    }

    @Override
    public void enterAndExpression(MiniJavaParser.AndExpressionContext ctx) {

    }

    @Override
    public void exitAndExpression(MiniJavaParser.AndExpressionContext ctx) {

    }

    @Override
    public void enterArrayAccessExpression(MiniJavaParser.ArrayAccessExpressionContext ctx) {

    }

    @Override
    public void exitArrayAccessExpression(MiniJavaParser.ArrayAccessExpressionContext ctx) {

    }

    @Override
    public void enterAddExpression(MiniJavaParser.AddExpressionContext ctx) {

    }

    @Override
    public void exitAddExpression(MiniJavaParser.AddExpressionContext ctx) {

    }

    @Override
    public void enterThisExpression(MiniJavaParser.ThisExpressionContext ctx) {

    }

    @Override
    public void exitThisExpression(MiniJavaParser.ThisExpressionContext ctx) {

    }

    @Override
    public void enterFieldCallExpression(MiniJavaParser.FieldCallExpressionContext ctx) {

    }

    @Override
    public void exitFieldCallExpression(MiniJavaParser.FieldCallExpressionContext ctx) {

    }

    @Override
    public void enterArrayLengthExpression(MiniJavaParser.ArrayLengthExpressionContext ctx) {

    }

    @Override
    public void exitArrayLengthExpression(MiniJavaParser.ArrayLengthExpressionContext ctx) {

    }

    @Override
    public void enterIntarrayInstantiationExpression(MiniJavaParser.IntarrayInstantiationExpressionContext ctx) {

    }

    @Override
    public void exitIntarrayInstantiationExpression(MiniJavaParser.IntarrayInstantiationExpressionContext ctx) {

    }

    @Override
    public void enterSubExpression(MiniJavaParser.SubExpressionContext ctx) {

    }

    @Override
    public void exitSubExpression(MiniJavaParser.SubExpressionContext ctx) {

    }

    @Override
    public void enterMulExpression(MiniJavaParser.MulExpressionContext ctx) {

    }

    @Override
    public void exitMulExpression(MiniJavaParser.MulExpressionContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }

    private String replaceExp(String expression) {
        if (expression.contains("**"))
            return replacePow(expression);
        else if (expression.contains("<>"))
            return replaceNot(expression);
        else if (expression.contains("*")&&!expression.contains("**")){
            return replaceMultiply(expression);
        }else if (expression.contains("/")){
            return replaceDivision(expression);
        }else if (expression.contains("+")){
            return replaceAdd(expression);
        }else if (expression.contains("-")){
            return replaceMinus(expression);
        }
        return expression;
    }
    private String replaceDivision(String expression){
        String[] parts = expression.split("\\/");
        int first = 0;
        int second = 0;
        int result = 0;
        //decide ==0 -> contain operand if decide ==1 -> isNumber
        if (decide(parts[0])==0){
            replaceExp(parts[0]);
        }else if(decide(parts[0])==1&&decide(parts[1])==1){
            first = Integer.parseInt(parts[0].trim());
            second = Integer.parseInt(parts[1].trim());
            result = first / second;
            return String.valueOf(result);
        }if (decide(parts[1])==0){
            replaceExp(parts[1]);
        }
        return expression;
    }
    private String replaceMinus(String expression){
        String[] parts = expression.split("\\-");
        int first = 0;
        int second = 0;
        int result = 0;
        //decide ==0 -> contain operand if decide ==1 -> isNumber
        if (decide(parts[0])==0){
            replaceExp(parts[0]);
        }else if(decide(parts[0])==1&&decide(parts[1])==1){
            first = Integer.parseInt(parts[0].trim());
            second = Integer.parseInt(parts[1].trim());
            result = first - second;
            return String.valueOf(result);
        }if (decide(parts[1])==0){
            replaceExp(parts[1]);
        }
        return expression;
    }
    private String replaceAdd(String expression){
        String[] parts = expression.split("\\+");
        int first = 0;
        int second = 0;
        int result = 0;
        //decide ==0 -> contain operand if decide ==1 -> isNumber
        if (decide(parts[0])==0){
            replaceExp(parts[0]);
        }else if(decide(parts[0])==1&&decide(parts[1])==1){
            first = Integer.parseInt(parts[0].trim());
            second = Integer.parseInt(parts[1].trim());
            result = first + second;
            return String.valueOf(result);
        }if (decide(parts[1])==0){
            replaceExp(parts[1]);
        }
        return expression;
    }
    private String replaceMultiply(String expression){
        String[] parts = expression.split("\\*");
        int first = 0;
        int second = 0;
        int result = 0;
        //decide ==0 -> contain operand if decide ==1 -> isNumber
        if (decide(parts[0])==0){
            replaceExp(parts[0]);
        }else if(decide(parts[0])==1&&decide(parts[1])==1){
            first = Integer.parseInt(parts[0].trim());
            second = Integer.parseInt(parts[1].trim());
            result = first * second;
            return String.valueOf(result);
        }if (decide(parts[1])==0){
            replaceExp(parts[1]);
        }
        return expression;
    }

    private String replaceNot(String expression) {
        String[] parts = expression.split("<>");
        if (parts.length >= 2) {
            String args = parts[1].trim().substring(0, parts[1].length() - 1);

            // Replace <> with !
            return "!" + args;
        } else {
            return expression;
        }
    }

    private String replacePow(String expression) {
        //A**B
        String[] parts = expression.split("\\*\\*");
        if (parts.length >= 2) {
            String base = parts[0].trim();
            String exponent = parts[1].trim();
            if (isNumber(base)){
                if (isNumber(exponent)){
                    return String.valueOf(Math.pow(Integer.parseInt(base),Integer.parseInt(exponent)));
                }
            }
            if (containOperand(base)){
                replaceExp(base);
            }
            if (containOperand(exponent)){
                replaceExp(exponent);
            }

            // Replace ** with Math.pow
            return "Math.pow(" + base + ", " + exponent + ")";
        } else {
            return expression;
        }
    }
    public boolean containOperand(String input){
        if (input.contains("*")||input.contains("+")||input.contains("**")||input.contains("/")){
            return true;
        }
        return false;
    }

    private String printIndent(int x) {
        String y = "";

        for (int i = 0; i < x; i++) {
            y = y.concat("\t");
        }
        return y;
    }

    private String convertType(String text) {
        if (text.contains("number")) {
            return text.replace("number", "int");
        } else if (text.equals("boolean")) {
            return "boolean";
        } else {
            return text;
        }
    }

    public void printText() {
        System.out.print(output);
    }
    public boolean isNumber(String base) {
        try {
            Integer.parseInt(base);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private int decide(String input){
        if (isNumber(input.trim())){
            return 1;
        }
        else if (containOperand(input)){
            return 0;
        }
        else {
            return -1;
        }
    }
}
