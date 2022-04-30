import java.lang.reflect.AnnotatedTypeVariable;

import javax.sound.midi.Synthesizer;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class CNFConverter {
    //https://en.wikipedia.org/wiki/Conjunctive_normal_form
    //https://en.wikipedia.org/wiki/De_Morgan%27s_laws
    
    public static LogicalExpression removeImplicationAndBi(LogicalExpression le){
        if(le instanceof Symbol){
            System.out.println("symbol");
            return le;
        }
        if(le instanceof And){
            System.out.println("And");
            LogicalExpression le1 = removeImplicationAndBi(((And )le).getLogicalExpression1());
            LogicalExpression le2 = removeImplicationAndBi(((And )le).getLogicalExpression2());
            return new And(le1,le2);
        }
        if(le instanceof Or){
            System.out.println("OR");
            LogicalExpression le1 = removeImplicationAndBi(((Or )le).getLogicalExpression1());
            LogicalExpression le2 = removeImplicationAndBi(((Or )le).getLogicalExpression2());
            return new Or(le1,le2);
        }
        if(le instanceof Parenthesis){
            System.out.println("Parenth");
            return new Parenthesis( removeImplicationAndBi(((Parenthesis )le).getLogicalExpression()));
        }
        if(le instanceof Not){
            System.out.println("not");
            return new Not( removeImplicationAndBi(((Not )le).getLogicalExpression()));
        }
        if(le instanceof Implication){
            System.out.println("Implication");
            LogicalExpression le1 = removeImplicationAndBi(((Implication )le).getLogicalExpression1());
            LogicalExpression le2 = removeImplicationAndBi(((Implication )le).getLogicalExpression2());
            return new Or(new Not(le1),le2);
        }
        if(le instanceof Biimplication){
            System.out.println("biimplication");
            LogicalExpression le1 = removeImplicationAndBi(((Biimplication )le).getLogicalExpression1());
            LogicalExpression le2 = removeImplicationAndBi(((Biimplication )le).getLogicalExpression2());
            LogicalExpression ret1 = new Or(le1,new Not(le2));
            LogicalExpression ret2 = new Or(new Not(le1),le2);

            return new And(new Parenthesis(ret1),new Parenthesis(ret2));
        }
        return null;
    }

    public void removeBiImplication(LogicalExpression le){
        
    }

    public static LogicalExpression moveNotInvards(LogicalExpression le, int notFlag){
        if(le instanceof Symbol){
            System.out.println("symbol");

            if(notFlag == 1){
                return new Not(le);
            }
            return le;
        }
        if(le instanceof And){
            System.out.println("And");
            if(notFlag == 1){
                LogicalExpression le1 = moveNotInvards(((And )le).getLogicalExpression1(),1);
                LogicalExpression le2 = moveNotInvards(((And )le).getLogicalExpression2(),1);
                return new Or(le1,le2);
            } else{
                LogicalExpression le1 = moveNotInvards(((And )le).getLogicalExpression1(),notFlag);
                LogicalExpression le2 = moveNotInvards(((And )le).getLogicalExpression2(),notFlag);
                return new And(le1,le2);
            }


        }
        if(le instanceof Or){
            System.out.println("OR");
            if(notFlag == 1){
                LogicalExpression le1 = moveNotInvards(((Or )le).getLogicalExpression1(),1);
                LogicalExpression le2 = moveNotInvards(((Or )le).getLogicalExpression2(),1);
                return new And(le1, le2);
            } else{
                LogicalExpression le1 = moveNotInvards(((Or )le).getLogicalExpression1(),notFlag);
                LogicalExpression le2 = moveNotInvards(((Or )le).getLogicalExpression2(),notFlag);
                return new Or(le1,le2);
            }
        }
        if(le instanceof Parenthesis){
            System.out.println("Parenth");
            return new Parenthesis( moveNotInvards(((Parenthesis )le).getLogicalExpression(),notFlag));
        }
        if(le instanceof Not){
            System.out.println("not");
            if(notFlag == 1){
                return  moveNotInvards(((Not )le).getLogicalExpression(),0);
            }
            return  moveNotInvards(((Not )le).getLogicalExpression(),1);
        }
        return null;
    }

    public static LogicalExpression distributeOrInwards(LogicalExpression le){
        if(le instanceof Symbol){
            System.out.println("symbol");
            return le;
        }
        if(le instanceof And){
            System.out.println("And");
            LogicalExpression le1 = distributeOrInwards(((And )le).getLogicalExpression1());
            LogicalExpression le2 = distributeOrInwards(((And )le).getLogicalExpression2());
            return new And(le1,le2);
        }
        if(le instanceof Or){
            System.out.println("OR");
            LogicalExpression le1 = distributeOrInwards(((Or )le).getLogicalExpression1());
            LogicalExpression le2 = distributeOrInwards(((Or )le).getLogicalExpression2());
            return new Or(le1,le2);
        }
        if(le instanceof Parenthesis){
            System.out.println("Parenth");
            return new Parenthesis( distributeOrInwards(((Parenthesis )le).getLogicalExpression()));
        }
        if(le instanceof Not){
            System.out.println("not");
            return new Not( distributeOrInwards(((Not )le).getLogicalExpression()));
        }
        return null;
    }

}
