public interface LogicalExpression {
    
    public boolean contains(LogicalExpression containsLE);

    public String toString();

    public int getRank();
    
}
