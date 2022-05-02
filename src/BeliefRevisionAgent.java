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
        this.beliefBase = expand(le);
        this.beliefBase = contract((le instanceof Not) ? ((Not) le).getLogicalExpression() : new Not(new Parenthesis(le)));
        return this.beliefBase;
    }

    public ArrayList<LogicalExpression> contract(LogicalExpression le) {
        //System.out.println("CONTRACT WITH: " + le.toString());
        /*ArrayList<LogicalExpression> valuesToRemove = new ArrayList<>();
        for (LogicalExpression logicalExpression : this.beliefBase) {
            if(logicalExpression.contains(le)){
                valuesToRemove.add(logicalExpression);
                //beliefBase.remove(logicalExpression);
            }
        }
        this.beliefBase.removeAll(valuesToRemove);*/

        ArrayList<LogicalExpression> LEsetCNF = CNFConverter.cNFConverter(this.beliefBase);
        BaseChecker bc = new BaseChecker();
        ArrayList<String> symb = bc.getSymbols(LEsetCNF);
        bc.fillTruthTable(symb, LEsetCNF);
        boolean consistent = bc.checkBase(LEsetCNF, symb);
        if(!consistent){
            ArrayList<Integer> toRet = bc.cleanBase(this.beliefBase, symb);
            for (int i = 0; i < toRet.size(); i++) {
                this.beliefBase.remove(toRet.get(i)-i);
            }
        }
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


    public ArrayList<LogicalExpression> calculateConsequences(ArrayList<LogicalExpression> beliefBase){
        BeliefRevisionAgent brAgent = new BeliefRevisionAgent(beliefBase);
        ArrayList<LogicalExpression> consequences = new ArrayList<LogicalExpression>();
        for (LogicalExpression logicalExpression : beliefBase) {
            if(logicalExpression instanceof Implication){
                if(this.contains(beliefBase, (((Implication) logicalExpression).LE1))){

                    consequences.add((((Implication) logicalExpression).LE2));

                }else if(this.contains(beliefBase, ((Implication) logicalExpression).LE1)){
                    consequences.add(((Implication) logicalExpression).LE2);
                }
            }else if(logicalExpression instanceof Biimplication){
                if(this.contains(beliefBase, new Not(((Biimplication) logicalExpression).LE1))){
                    consequences.add(new Not(((Biimplication) logicalExpression).LE2));
                }else if(brAgent.contains(beliefBase, ((Biimplication) logicalExpression).LE1)){
                    consequences.add(((Biimplication) logicalExpression).LE2);
                }

                if(this.contains(beliefBase, new Not(((Biimplication) logicalExpression).LE2))){
                    consequences.add(new Not(((Biimplication) logicalExpression).LE1));
                }else if(this.contains(beliefBase, ((Biimplication) logicalExpression).LE2)){
                    consequences.add(((Biimplication) logicalExpression).LE1);
                }
            }
            consequences.add(logicalExpression);
        }
        return consequences;
    }

    public boolean contains(ArrayList<LogicalExpression> beliefBase, LogicalExpression le){
        for (LogicalExpression logicalExpression : beliefBase) {
            if(logicalExpression.toString().equals(le.toString())){
                return true;
            }

        }
        return true;
    }
}