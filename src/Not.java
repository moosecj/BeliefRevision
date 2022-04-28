public class Not implements LogicalExpression {
    LogicalExpression LE;
    int rank;

    public Not(LogicalExpression LE){
        this.rank = LE.getRank() + 1;
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

    @Override
    public String toString(){
        return "!" + LE.toString();
    }
}
