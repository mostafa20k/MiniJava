import java.util.Locale;

public class SymbolTableItem {
    int line;
    int column;
    String name;
    String dec_type;
    String parameters;
    Type type;
    String inherits;
    String implement;
    String access;

    public SymbolTableItem(int line , int column, String name , String dec_type , Type type,
                           String inherits, String implement, String access, String params) {
        this.column = column;
        this.line = line;
        this.name = name;
        this.type = type;
        this.dec_type = dec_type;
        this.inherits=inherits;
        this.implement=implement;
        this.access=access;
        this.parameters=params;
    }

    @Override
    public String toString() {
        switch (type){
            case METHOD:
                if(this.access!=null && this.parameters!=null)
                    return String.format("%s (name: %s) (return type: %s) (accessModifier: ACCESS_MODIFIER_%s) " +
                                    "(Parameters: %s)"
                            , type , name , dec_type, access.toUpperCase(), parameters);
                else if(this.access!=null)
                    return String.format("%s (name: %s) (return type: %s) (accessModifier: ACCESS_MODIFIER_%s)"
                            , type , name , dec_type, access.toUpperCase());
                else if(this.parameters!=null)
                    return String.format("%s (name: %s) (return type: %s) "+
                                    "(Parameters: %s)"
                            , type , name , dec_type, parameters);

                return String.format("%s (name: %s) (return type: %s)" , type , name , dec_type);
            case Class:
                if(this.inherits!=null && this.implement!=null)
                    return String.format("%s (name: %s) (extends : %s | implements : %s)",
                            type,name,inherits,implement);
                else if(this.inherits!=null)
                    return String.format("%s (name: %s) (extends : %s)",
                            type, name, inherits);
                else if(this.implement!=null)
                    return String.format("%s (name: %s) (implements : %s)",
                            type,name,implement);
                break;
            case LocalVar:
                return String.format("%s (name: %s) (type: %s)" , type , name , dec_type);

        }

        return String.format("%s (name: %s)" , type , name);
    }
}
