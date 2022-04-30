public class Or implements LogicalExpression {
    LogicalExpression LE1;
    LogicalExpression LE2;
    int rank;

    public Or(LogicalExpression LE1, LogicalExpression LE2){
        this.rank = LE1.getRank() + LE2.getRank() + 1;
        this.LE1 = LE1;
        this.LE2 = LE2;
    }

    @Override
    public boolean contains(LogicalExpression containsLE){
        if(LE1.contains(containsLE) || LE2.contains(containsLE)){
            return true;
        }
        return false;
    }

    public LogicalExpression getLogicalExpression1() {
        return this.LE1;
    }
    public LogicalExpression getLogicalExpression2() {
        return this.LE2;
    }


    @Override
    public int getRank() {
        // TODO Auto-generated method stub
        return this.rank;
    }

    @Override
    public String toString(){
        return LE1.toString() + " OR " + LE2.toString();
    }
}
