public class Parenthesis implements LogicalExpression {
    LogicalExpression LE;
    int rank;

    public Parenthesis(LogicalExpression LE){
        this.rank = LE.getRank() ;
        this.LE = LE;
    }

    public boolean contains(LogicalExpression containsLE){
        if(LE.contains(containsLE)){
            return true;
        }
        return false;
    }
}
