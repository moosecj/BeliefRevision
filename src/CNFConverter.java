import java.lang.reflect.AnnotatedTypeVariable;
import java.util.*;
import javax.sound.midi.Synthesizer;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class CNFConverter {
    //https://en.wikipedia.org/wiki/Conjunctive_normal_form
    //https://en.wikipedia.org/wiki/De_Morgan%27s_laws
    
    public static ArrayList<LogicalExpression> cNFConverter(ArrayList<LogicalExpression> LEset){
        ArrayList<LogicalExpression> convertedBeleifBase = new ArrayList<LogicalExpression>();

        for (LogicalExpression logicalExpression : LEset) {
            LogicalExpression letester = CNFConverter.removeImplicationAndBi(logicalExpression);
            letester = CNFConverter.moveNotInvards(letester, 0);
            letester = CNFConverter.distributeOrInwards(letester);
            convertedBeleifBase.add(letester);
        }

        ArrayList<LogicalExpression> convertedBeleifBaseAndCleaned = new ArrayList<LogicalExpression>();

        for (LogicalExpression logicalExpression : convertedBeleifBase) {
            if(logicalExpression instanceof Parenthesis){
                convertedBeleifBaseAndCleaned.add(((Parenthesis)logicalExpression).getLogicalExpression());

            }else if(logicalExpression instanceof And){
                convertedBeleifBaseAndCleaned.add(((And)logicalExpression).getLogicalExpression1());
                convertedBeleifBaseAndCleaned.add(((And)logicalExpression).getLogicalExpression2());
            }else{
                convertedBeleifBaseAndCleaned.add(logicalExpression);
            }
        }
        convertedBeleifBaseAndCleaned = removeDuplicates(convertedBeleifBaseAndCleaned);

        return convertedBeleifBaseAndCleaned;

    }


    public static LogicalExpression removeImplicationAndBi(LogicalExpression le){
        if(le instanceof Symbol){
            return le;
        }
        if(le instanceof And){
            LogicalExpression le1 = removeImplicationAndBi(((And )le).getLogicalExpression1());
            LogicalExpression le2 = removeImplicationAndBi(((And )le).getLogicalExpression2());
            return new And(le1,le2);
        }
        if(le instanceof Or){
            LogicalExpression le1 = removeImplicationAndBi(((Or )le).getLogicalExpression1());
            LogicalExpression le2 = removeImplicationAndBi(((Or )le).getLogicalExpression2());
            return new Or(le1,le2);
        }
        if(le instanceof Parenthesis){
            LogicalExpression tempLe = ((Parenthesis)le).getLogicalExpression();
            if(tempLe instanceof Parenthesis){
                return removeImplicationAndBi(((Parenthesis )le).getLogicalExpression());
            }
            return new Parenthesis( removeImplicationAndBi(((Parenthesis )le).getLogicalExpression()));
        }
        if(le instanceof Not){
            return new Not( removeImplicationAndBi(((Not )le).getLogicalExpression()));
        }
        if(le instanceof Implication){
            LogicalExpression le1 = removeImplicationAndBi(((Implication )le).getLogicalExpression1());
            LogicalExpression le2 = removeImplicationAndBi(((Implication )le).getLogicalExpression2());
            return new Or(new Not(le1),le2);
        }
        if(le instanceof Biimplication){
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

            if(notFlag == 1){
                return new Not(le);
            }
            return le;
        }
        if(le instanceof And){
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
            return new Parenthesis( moveNotInvards(((Parenthesis )le).getLogicalExpression(),notFlag));
        }
        if(le instanceof Not){
            if(notFlag == 1){
                return  moveNotInvards(((Not )le).getLogicalExpression(),0);
            }
            return  moveNotInvards(((Not )le).getLogicalExpression(),1);
        }
        return null;
    }

    public static LogicalExpression distributeOrInwards(LogicalExpression le){
        if(le instanceof Symbol){
            return le;
        }
        if(le instanceof And){
            LogicalExpression le1 = distributeOrInwards(((And )le).getLogicalExpression1());
            LogicalExpression le2 = distributeOrInwards(((And )le).getLogicalExpression2());
            return new And(le1,le2);
        }
        if(le instanceof Or){
            LogicalExpression le1 = distributeOrInwards(((Or )le).getLogicalExpression1());
            LogicalExpression le2 = distributeOrInwards(((Or )le).getLogicalExpression2());
            if(le1 instanceof Parenthesis){
                if(le2 instanceof Parenthesis){
                    return new Or(le1,le2);
                }
                LogicalExpression tempLe = ((Parenthesis)le1).getLogicalExpression();
                if(tempLe instanceof And){
                    LogicalExpression ret1 = new Or(le2,((And)tempLe).getLogicalExpression1());
                    LogicalExpression ret2 = new Or(le2,((And)tempLe).getLogicalExpression2());
                    return new And(new Parenthesis(ret1),new Parenthesis(ret2));
                } 
                return new Or(le1,le2);


                
            }
            if(le2 instanceof Parenthesis){
                if(le1 instanceof Parenthesis){
                    return new Or(le1,le2);
                }
                // P ∨ ( Q ∧ R )
                // ( P ∨ Q ) ∧ ( P ∨ R )
                LogicalExpression tempLe = ((Parenthesis)le2).getLogicalExpression();
                if( tempLe instanceof And){
                    LogicalExpression ret1 = new Or(le1,((And)tempLe).getLogicalExpression1());
                    LogicalExpression ret2 = new Or(le1,((And)tempLe).getLogicalExpression2());
                    return new And(new Parenthesis(ret1),new Parenthesis(ret2));
                } 
                return new Or(le1,le2);
            }




            return new Or(le1,le2);
        }
        if(le instanceof Parenthesis){
            return new Parenthesis( distributeOrInwards(((Parenthesis )le).getLogicalExpression()));
        }
        if(le instanceof Not){
            return new Not( distributeOrInwards(((Not )le).getLogicalExpression()));
        }
        return null;
    }


    //Implemnetation based on https://www.geeksforgeeks.org/how-to-remove-duplicates-from-arraylist-in-java/
    public static ArrayList<LogicalExpression> removeDuplicates(ArrayList<LogicalExpression> LEset)
    {
        ArrayList<LogicalExpression> newLEset = new ArrayList<LogicalExpression>();
        for (LogicalExpression element : LEset) {

            if (!newLEset.contains(element)) {
                newLEset.add(element);
            }
        }
        return newLEset;
    }

}
