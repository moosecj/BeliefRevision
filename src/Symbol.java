public class Symbol implements LogicalExpression {
    String name;
    int rank;

    public Symbol(String name){
        this.rank = 1;
        this.name = name;
    }

    @Override
    public boolean contains(LogicalExpression containsLE){
        if(this.name == containsLE.toString()){
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
        return name;
    }
}
