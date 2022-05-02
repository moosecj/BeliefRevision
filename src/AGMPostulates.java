import java.util.ArrayList;

import javafx.scene.shape.QuadCurve;

public class AGMPostulates {
    /*
    Ones we need to implement:
    1 Closure: B * p = Cn(B * p) Closed in consequence?
    2 Success: p in B * p meaning p should be contained in the revised beliefbase
    3 Inclusion: B * p should be a subset of B + p
    4 Vacuity: If there was nothing to be done, dont do anything
    5 Consistency: B * p is consistent if p is consistent (No contradictions)
    6 Extensionality: If p and q have the same meaning, then B % p = B % q
    7 Superexpansion: B * (p AND q) subset of (B * p) + q
    8 Subexpansion: if !q not in B * p, then (B * p) + q subset in B * (p AND q)


    p->q, p
    !p, 0

    */

    public AGMPostulates() {
        initTestCases();
        System.out.println("========== Closure Check Begin ========== ");
        checkClosure();
        System.out.println("========== Closure Check End ========== ");

        System.out.println("========== Success Check Begin ========== ");
        checkSuccess();
        System.out.println("========== Success Check End ========== ");

        System.out.println("========== Inclusion Check Begin ========== ");
        checkInclusion();
        System.out.println("========== Inclusion Check End ========== ");

        System.out.println("========== Vacuity Check Begin ========== ");
        checkVacuity();
        System.out.println("========== Vacuity Check End ========== ");

        System.out.println("========== Consistency Check Begin ========== ");
        checkConsistency();
        System.out.println("========== Consistency Check End ========== ");

        System.out.println("========== Extensionality Check Begin ========== ");
        checkExtensionality();
        System.out.println("========== Extensionality Check End ========== ");

        System.out.println("========== Superexpansion Check Begin ========== ");
        checkSuperexpansion();
        System.out.println("========== Superexpansion Check End ========== ");

        System.out.println("========== Subexpansion Check Begin ========== ");
        checkSubexpansion();
        System.out.println("========== Subexpansion Check End ========== ");

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

    // Clsure: B * p = Cn(B * p) Closed in consequence?
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

    // Success: p in B * p meaning p should be contained in the revised beliefbase
    public void checkSuccess() {
        Symbol q = new Symbol("q");

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

    // Inclusion: B * p should be a subset of B + p
    public void checkInclusion() {
        Symbol q = new Symbol("q");

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

    // Vacuity: If there was nothing to be done, dont do anything
    public void checkVacuity() {
        Symbol w = new Symbol("w");
        Not notw = new Not(w);

        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        System.out.println("Initial Belief Base");
        beliefRevisionAgent.print();
        ArrayList<LogicalExpression> revisedBase = new ArrayList<>(beliefRevisionAgent.revise(notw));
        System.out.println("Revised Belief Base");
        beliefRevisionAgent.print();

        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        ArrayList<LogicalExpression> expandedBase = new ArrayList<>(beliefRevisionAgent.expand(notw));

        for(int i = 0; i < revisedBase.size(); i++) {
            if(!(revisedBase.get(i).toString().equals(expandedBase.get(i).toString()))) {
                System.out.println("Vacuity check failed. " + revisedBase.get(i).toString() + " , " + expandedBase.get(i).toString());
                return;
            }
        }
        System.out.println("Vacuity check succeded.");
    }

    // Consistency: B * p is consistent if p is consistent (No contradictions)
    public void checkConsistency() {
        Symbol q = new Symbol("q");
        Symbol p = new Symbol("p");
        Not notp = new Not(p);



        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        System.out.println("Initial Belief Base");
        beliefRevisionAgent.print();
        ArrayList<LogicalExpression> consistantRevisedBase = new ArrayList<>(beliefRevisionAgent.revise(notp));
        System.out.println("Consistant Revised Belief Base");
        beliefRevisionAgent.print();

        // CHECK CONSISTENCY
        ArrayList<LogicalExpression> LEsetCNF = CNFConverter.cNFConverter(consistantRevisedBase);
        BaseChecker bc = new BaseChecker();
        ArrayList<String> symb = bc.getSymbols(LEsetCNF);
        bc.fillTruthTable(symb, LEsetCNF);
        boolean consistent1 = bc.checkBase(LEsetCNF, symb);

        if(consistent1) {
            System.out.println("Consistency check succeded.");
        } else {
            System.out.println("Consistency check failed.");
        }
    }

    // Extensionality: If p and q have the same meaning, then B % p = B % q
    public void checkExtensionality() {
        Symbol p = new Symbol("p");
        Symbol r = new Symbol("r");
        Not notr = new Not(r);

        Biimplication biimp1 = new Biimplication(p, notr);
        Biimplication biimp2 = new Biimplication(notr, p);

        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        System.out.println("Initial Belief Base");
        beliefRevisionAgent.print();
        ArrayList<LogicalExpression> revisedBase1 = new ArrayList<>(beliefRevisionAgent.revise(biimp1));
        System.out.println("First Revised Belief Base");
        beliefRevisionAgent.print();

        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        ArrayList<LogicalExpression> revisedBase2 = new ArrayList<>(beliefRevisionAgent.revise(biimp2));
        System.out.println("Second Revised Belief Base");
        beliefRevisionAgent.print();

        for(int i = 0; i < revisedBase1.size(); i++) {
            if(!(revisedBase1.get(i).toString().equals(revisedBase2.get(i).toString()))) {
                System.out.println("Extensionality check failed. " + revisedBase1.get(i).toString() + " , " + revisedBase2.get(i).toString());
                return;
            }
        }
        System.out.println("Extensionality check succeded.");
    }

    // Superexpansion: B * (p AND q) subset of (B * p) + q
    public void checkSuperexpansion() {
        Symbol p = new Symbol("p");
        Symbol q = new Symbol("q");
        Not notp = new Not(p);
        Not notq = new Not(q);
        And pandq = new And(notp, notq);
        Parenthesis parenthesis_pandq = new Parenthesis(pandq);


        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        System.out.println("Initial Belief Base");
        beliefRevisionAgent.print();
        ArrayList<LogicalExpression> revisedBase = new ArrayList<>(beliefRevisionAgent.revise(parenthesis_pandq));
        System.out.println("Case: B * " + parenthesis_pandq.toString());
        beliefRevisionAgent.print();
        
        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        ArrayList<LogicalExpression> revisedBase2 = new ArrayList<>(beliefRevisionAgent.revise(notp));
        revisedBase2 = beliefRevisionAgent.expand(notq);
        System.out.println("Case: (B * "+ notp.toString() +") + " + notq.toString());
        beliefRevisionAgent.print();

        for(LogicalExpression l : revisedBase) {
            boolean check = false;
            for(LogicalExpression le : revisedBase2) {
                if(l.toString().equals(le.toString())) {
                    check = true;
                }
            }
            if(!check) {
                System.out.println("Superexpansion check failed.");
                return;
            }
        }
        System.out.println("Superexpansion check succeded.");
    }

    // Subexpansion: if !q not in B * p, then (B * p) + q subset in B * (p AND q)
    public void checkSubexpansion() {
        Symbol p = new Symbol("p");
        Symbol q = new Symbol("q");
        And pandq = new And(p, q);
        Parenthesis parenthesis_pandq = new Parenthesis(pandq);


        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        System.out.println("Initial Belief Base");
        beliefRevisionAgent.print();
        ArrayList<LogicalExpression> revisedBase = new ArrayList<>(beliefRevisionAgent.revise(parenthesis_pandq));
        System.out.println("Case: B * " + parenthesis_pandq.toString());
        beliefRevisionAgent.print();
        
        beliefRevisionAgent.setBeliefBase(testBeliefBase);
        ArrayList<LogicalExpression> revisedBase2 = new ArrayList<>(beliefRevisionAgent.revise(p));
        revisedBase2 = beliefRevisionAgent.expand(q);
        System.out.println("Case: (B * "+ p.toString() +") + " + q.toString());
        beliefRevisionAgent.print();

        for(LogicalExpression l : revisedBase2) {
            boolean check = false;
            for(LogicalExpression le : revisedBase) {
                if(l.toString().equals(le.toString())) {
                    check = true;
                }
            }
            if(!check) {
                System.out.println("Subexpansion check failed.");
                return;
            }
        }
        System.out.println("Subexpansion check succeded.");
    }
}