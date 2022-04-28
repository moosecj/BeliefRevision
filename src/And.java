public class And extends LogicalExpression {
    LogicalExpression LE1;
    LogicalExpression LE2;

    public And(LogicalExpression LE1, LogicalExpression LE2){
        super.rank = LE1.rank + LE2.rank + 1;
        this.LE1 = LE1;
        this.LE2 = LE2;
    }
    
}
