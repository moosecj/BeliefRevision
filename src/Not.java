public class Not extends LogicalExpression {
    LogicalExpression LE;

    public Not(LogicalExpression LE){
        super.rank = LE.rank + 1;
        this.LE = LE;
    }
}
