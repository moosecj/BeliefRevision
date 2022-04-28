public class Parenthesis extends LogicalExpression {
    LogicalExpression LE;

    public Parenthesis(LogicalExpression L){
        super.rank = L.rank ;
        this.LE = LE;
    }
}
