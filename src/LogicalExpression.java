public interface LogicalExpression {
    
    public boolean contains(LogicalExpression containsLE);

    public String toString();

    public int getRank();

    public void setRank(int rank);

    //public LogicalExpression getLogicalExpression();
    
}
