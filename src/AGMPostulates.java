import java.util.ArrayList;

public class AGMPostulates {
    /*
    1 Closure:    B % p = Cn(B % p)
    2 Success:    p should not be in Cn(B % p)
    3 Inclusion:  Minimum changes - B % p should be a subset of B
    4 Vacuity:    If p isn't in B do nothing p not in B meaning B % p = B
    5 Extensionality: If p and q have the same meaning, then B % p = B % q
    6 Recovery:   Contraction leads to as little loss as possible, 




    Ones we need to implement:
    1 Closure:    B * p = Cn(B * p) Closed in consequence?
    2 Success:    p in B * p meaning p should be contained in the revised beliefbase
    3 Inclusion:  B * p should be a subset of B + p
    4 Vacuity:    If there was nothing to be done, dont do anything
    5 Consistency: B * p is consistent if p is consistent (No contradictions)
    6 Extensionality: If p and q have the same meaning, then B % p = B % q


    q
    p->!q

    p, q, p->!q
    p, q, !q, p->!q

    p, !p AND q

    (p AND q) -> !q

    */

    public AGMPostulates() {
        initTestCases();
        checkClosure();
        checkSuccess();
        checkInclusion();
    }

    BeliefRevisionAgent beliefRevisionAgent = new BeliefRevisionAgent(null);
    
    // test belief base
    ArrayList<LogicalExpression> testBeliefBase = new ArrayList<>();
    private void initTestCases() {
        // expressions
        Symbol p = new Symbol("p");
        Symbol q = new Symbol("q");
        Symbol r = new Symbol("r");
        Or porq = new Or(p, q);
        Parenthesis parenthesis_porq = new Parenthesis(porq);
        Implication porqthenr = new Implication(parenthesis_porq, r);
        // (p OR q) -> r

        testBeliefBase.add(p);
        testBeliefBase.add(r);
        testBeliefBase.add(porqthenr);
    }


    public void checkClosure() {
        Symbol q = new Symbol("q");
        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        beliefRevisionAgent.revise(q);

        if(false /*Check for contradictions*/) {
            System.out.println("Closure check succeded.");
        } else {
            System.out.println("Closure check failed.");
        }
    }

    public void checkSuccess() {
        Symbol q = new Symbol("q");

        System.out.println("=== Success Check ===");

        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        System.out.println("Initial Belief Base");
        beliefRevisionAgent.print();
        beliefRevisionAgent.revise(q);
        System.out.println("Revised Belief Base");
        beliefRevisionAgent.print();

        ArrayList<LogicalExpression> base = beliefRevisionAgent.getBase();
        boolean check = false;
        for(LogicalExpression l : base) {
            if(l.toString().equals(q.toString())) {
                check = true;
            }
        }
        if(check) {
            System.out.println("Symbol q is contained in the belief base after revision.");
            System.out.println("Success check succeded.");
        } else {
            System.out.println("Success check failed.");
        }
    }

    public void checkInclusion() {
        Symbol q = new Symbol("q");

        System.out.println("=== Inclusion Check ===");

        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        System.out.println("Initial Belief Base");
        beliefRevisionAgent.print();
        ArrayList<LogicalExpression> revisedBase = new ArrayList<>(beliefRevisionAgent.revise(q));
        System.out.println("Revised Belief Base");
        beliefRevisionAgent.print();

        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        ArrayList<LogicalExpression> expandedBase = new ArrayList<>(beliefRevisionAgent.expand(q));

        for(LogicalExpression l : revisedBase) {
            boolean check = false;
            for(LogicalExpression le : expandedBase) {
                if(l.toString().equals(le.toString())) {
                    check = true;
                }
            }
            if(!check) {
                System.out.println("Inclusion check failed.");
                return;
            }
        }
        System.out.println("Inclusion check succeded.");
    }

    public void checkVacuity() {
        
    }
}
