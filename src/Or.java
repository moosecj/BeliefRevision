public class Or implements LogicalExpression {
    LogicalExpression LE1;
    LogicalExpression LE2;
    int rank;

    public Or(LogicalExpression LE1, LogicalExpression LE2, int rank){
        this.rank = LE1.getRank() + LE2.getRank() + 1;
        this.LE1 = LE1;
        this.LE2 = LE2;
    }

    public boolean contains(LogicalExpression containsLE){
        if(LE1.contains(containsLE) || LE2.contains(containsLE)){
            return true;
        }
        return false;
    }
}
