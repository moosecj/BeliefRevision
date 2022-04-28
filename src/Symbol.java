public class Symbol extends LogicalExpression {
    String name;

    public Symbol(String name){
        super.rank = 1;
        this.name = name;
    }
}
