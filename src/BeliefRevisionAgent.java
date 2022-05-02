import java.util.*;

public class BeliefRevisionAgent {

    ArrayList<LogicalExpression> beliefBase;

    public BeliefRevisionAgent(ArrayList<LogicalExpression> base) {
        this.beliefBase = base;
    }


    public ArrayList<LogicalExpression> revise(LogicalExpression le) {
        //ArrayList<LogicalExpression> returnBase = new ArrayList<LogicalExpression>();
        //returnBase = contract(beliefBase, (le instanceof Not) ? ((Not) le).getLogicalExpression() : new Not(le));
        //returnBase = expand(returnBase, le);
        this.beliefBase = contract((le instanceof Not) ? ((Not) le).getLogicalExpression() : new Not(le));
        this.beliefBase = expand(le);
        return this.beliefBase;
    }

    public ArrayList<LogicalExpression> contract(LogicalExpression le) {
        System.out.println("CONTRACT WITH: " + le.toString());
        ArrayList<LogicalExpression> valuesToRemove = new ArrayList<>();
        for (LogicalExpression logicalExpression : this.beliefBase) {
            if(logicalExpression.contains(le)){
                valuesToRemove.add(logicalExpression);
                //beliefBase.remove(logicalExpression);
            }
        }
        this.beliefBase.removeAll(valuesToRemove);
        return this.beliefBase;
    }

    public ArrayList<LogicalExpression> expand(LogicalExpression le) {
        boolean check = false;
        for (LogicalExpression logicalExpression : this.beliefBase) {
            if(logicalExpression.toString().equals(le.toString())){
                check = true;
            }
        }
        if (!check) { this.beliefBase.add(le); }
        return this.beliefBase;
    }

    public ArrayList<LogicalExpression> getBase() {
        return this.beliefBase;
    }

    public void setBeliefBase(ArrayList<LogicalExpression> base) {
        this.beliefBase = new ArrayList<>(base);
    }

    public void print(){

        //for(int i = 0; i < this.beliefBase.size(); i++);
        System.out.print("{");
        int i = 0;
        for (LogicalExpression item : this.beliefBase) {
            
            if(i == this.beliefBase.size()-1) {
                System.out.print(item.toString());
            } else {
                System.out.print(item.toString() + ", ");
            }
            i++;
        }
        System.out.print("}");
        System.out.println();
    }
}