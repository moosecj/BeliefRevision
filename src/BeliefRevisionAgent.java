import java.util.*;

public class BeliefRevisionAgent {

    ArrayList<LogicalExpression> base;

    public BeliefRevisionAgent(ArrayList<LogicalExpression> base) {
        this.base = base;
    }


    public ArrayList<LogicalExpression> revise(ArrayList<LogicalExpression> Beliefbase, LogicalExpression le) {
        if (le == null) {
            // do ting
        }
        return null;
    }

    public ArrayList<LogicalExpression> contract(ArrayList<LogicalExpression> Beliefbase, LogicalExpression le) {
        // if(BeliefBase.contains(le)) BeliefBase.remove
        return null;
    }

    public ArrayList<LogicalExpression> expand(ArrayList<LogicalExpression> Beliefbase, LogicalExpression le) {
        if (!Beliefbase.contains(le)) Beliefbase.add(le);
        return Beliefbase;
    }

    public ArrayList<LogicalExpression> getBase() {
        return this.base;
    }
}