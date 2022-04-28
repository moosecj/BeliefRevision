public class Symbol implements LogicalExpression {
    String name;
    int rank;

    public Symbol(String name){
        this.rank = 1;
        this.name = name;
    }

    public boolean contains(LogicalExpression containsLE){
        if(this.name == containsLE){
            return true;
        }
        return false;
    }
}
