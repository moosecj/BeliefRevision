public class Not implements LogicalExpression {
    LogicalExpression LE;
    int rank;

    public Not(LogicalExpression LE){
        this.rank = LE.getRank() + 1;
        this.LE = LE;
    }

    public boolean contains(LogicalExpression containsLE){
        if(LE.contains(containsLE)){
            return true;
        }
        return false;
    }
}
