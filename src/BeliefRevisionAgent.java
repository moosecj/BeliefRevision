import java.util.*;

public class BeliefRevisionAgent {

    ArrayList<LogicalExpression> base;

    public BeliefRevisionAgent(ArrayList<LogicalExpression> base) {
        this.base = base;
    }


    public ArrayList<LogicalExpression> revise(ArrayList<LogicalExpression> beliefBase, LogicalExpression le) {
        if (le == null) {
            // do ting
        }
        return null;
    }

    public ArrayList<LogicalExpression> contract(ArrayList<LogicalExpression> beliefBase, LogicalExpression le) {
        System.out.println("CONTRACT WITH: " + le.toString());
        ArrayList<LogicalExpression> valuesToRemove = new ArrayList<>();
        for (LogicalExpression logicalExpression : beliefBase) {
            if(logicalExpression.contains(le)){
                valuesToRemove.add(logicalExpression);
                //beliefBase.remove(logicalExpression);
            }
        }
        beliefBase.removeAll(valuesToRemove);
        return beliefBase;
    }

    public ArrayList<LogicalExpression> expand(ArrayList<LogicalExpression> beliefBase, LogicalExpression le) {
        boolean check = false;
        for (LogicalExpression logicalExpression : beliefBase) {
            if(logicalExpression.toString().equals(le.toString())){
                check = true;
            }
        }
        if (!check) { beliefBase.add(le); }
        return beliefBase;
    }

    public ArrayList<LogicalExpression> getBase() {
        return this.base;
    }
}