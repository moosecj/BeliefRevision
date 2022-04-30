public class Parenthesis implements LogicalExpression {
    LogicalExpression LE;
    int rank;

    public Parenthesis(LogicalExpression LE){
        this.rank = LE.getRank() ;
        this.LE = LE;
    }

    @Override
    public boolean contains(LogicalExpression containsLE){
        if(LE.contains(containsLE)){
            return true;
        }
        return false;
    }

    @Override
    public int getRank() {
        // TODO Auto-generated method stub
        return this.rank;
    }

    public LogicalExpression getLogicalExpression() {
        return this.LE;
    }

    @Override
    public String toString(){
        return "(" + LE.toString() + ")";
    }
}
