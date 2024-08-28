import java.util.LinkedHashMap;
import java.util.Map;

public class Scope {
    private LinkedHashMap<String, SymbolTableItem> hashtable = new LinkedHashMap<>();
    private String name;
    private int ruleIndex;
    private int line;
    private int column;
    private Type type;
    private ReturnType returnType;

    public Scope getParent() {
        return parent;
    }

    private Scope parent;

    public Scope(String name, int line , int column , int ruleIndex , Type type , Scope parent , ReturnType returnType) {
        this.name = name;
        this.ruleIndex = ruleIndex;
        this.type = type;
        this.line = line;
        this.parent = parent;
        this.returnType = returnType;
        this.column = column;
    }

    public LinkedHashMap<String, SymbolTableItem> getHashtable() {
        return hashtable;
    }
    public String getName() {
        return name;
    }
    public Type getType() {
        return type;
    }
    public ReturnType getReutnType() {
        return returnType;
    }

    public int getRuleIndex() {
        return ruleIndex;
    }
    public int getLine() {
        return line;
    }
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "------------- " + name + " : " + line + " -------------\n" + printItems() + "-----------------------------------------\n";
    }

    public String printItems() {
        String itemsStr = "";
        for (Map.Entry<String, SymbolTableItem> entry : hashtable.entrySet()) {
            itemsStr += "Key = " + entry.getKey() + " | Value = " + entry.getValue() + "\n";
        }
        return itemsStr;

    }
}
